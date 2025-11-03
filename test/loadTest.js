import http from 'k6/http';
import {check, sleep} from 'k6';
// 👇 HTML 리포트용 (외부 번들)
import { htmlReport } from 'https://raw.githubusercontent.com/benc-uk/k6-reporter/main/dist/bundle.js';
import { textSummary } from 'https://jslib.k6.io/k6-summary/0.0.4/index.js';
import { Trend } from 'k6/metrics'; // 👈 추가
const slow = new Trend('slow_req'); // 👈 느린 요청 트렌드 기록용

const DAU = 1500; // 하루 활성 사용자 수 (고정)
const avgRequestsPerUser = 2; // 한 사용자가 하루에 2번 요청한다고 가정
const dailyTotalRequests = DAU * avgRequestsPerUser;
const avgRPS = dailyTotalRequests / 86400; // 1초당 평균 요청 수 계산
const peakRPS = avgRPS * 10; // 피크 트래픽 10배 증가 고려

const VU_COUNT = 1500

export let options = {
  stages: [
    { duration: '2m', target: VU_COUNT }, // 2분 동안 서서히 증가
    { duration: '2m', target: VU_COUNT }, // 5분 동안 유지
    { duration: '1m', target: 0 }, // 1분 동안 종료
  ],
  thresholds: {
    'http_req_duration': ['p(95)<200'], // 95% 요청이 200ms 이하
    'http_req_failed': ['rate<0.01'], // 실패율 1% 이하
    slow_req: ['p(95)<3000'], // 👈 3초 이상 느린 요청 비율 체크용
  },
};

export default function () {
  let res = http.get('http://host.docker.internal:8080/api/v1/movies?title=in&genre=SCI_FI'); // API 호출
  check(res, { 'is status 200': (r) => r.status === 200 });

  // 👇 3초이상 느린 요청 로깅
  if (res.timings.duration > 3000) {
    slow.add(res.timings.duration);
    console.log(`SLOW ${res.status} ${res.timings.duration.toFixed(2)}ms url=${res.url}`);
  }

  // 👇 요청별 세부 타이밍 로깅 (debug 목적)
  //console.log(JSON.stringify(res.timings)); // blocked, connecting, waiting 등 세부 타이밍

  sleep(1); // 부하를 고르게 분배하기 위해 1초 대기
}

// 👇 실행 후 자동으로 result.html 생성
export function handleSummary(data) {
  return {
    'result.html': htmlReport(data),
    stdout: textSummary(data, { enableColors: true }),
  };
}

// 실행 명령어 : docker run --rm -v "{PWD}:/scripts" -w /scripts grafana/k6 run loadTest.js