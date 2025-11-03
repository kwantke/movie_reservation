# 영화 예매 시스템 (Movie Reservation System)

## 프로젝트 개요
영화 예매 시스템은 사용자가 영화 좌석을 예매하고 관리할수 있는 REST API 기반 서비스 입니다.

## 기술 스택
- Spring Boot 3.4.11
- Spring Data JPA
- Java 17
- Gradle
- MySQL 8.0
- Docker
- Redis
- K6
- Junit5
- IntelliJ Http Request

## 아키텍쳐(Hexagonal Architecture)
`헥사고날 아키텍처`를 기반으로 한 멀티모듈 구성입니다.

#### domain
- `도메인` 모듈은 다른 모듈에 의존하지 않습니다.
- 도메인의 핵심 로직을 책임지는 엔티티, 예외, 변환기 등의 요소를 포함합니다.
- 비즈니스 로직의 중심으로, 외부 변화에 의존하지 않도록 설계하였습니다.

#### application
- `애플리케이션`모듈은 `도메인`모듈에 의존합니다.
- Inbound Port: 컨트롤러에서 DTO로 데이터를 주고 받을 때 호출할 서비스 포트를 제공합니다.
- Outbound Port: DB와 통신하기 위해 서비스 계층에서 호출할 리포지토리 포트를 정의합니다.

#### infrastructure
- `인프라스트럭쳐`모듈은 `애플리케이션`모듈과 `도메인`모듈에 의존합니다.
- 외부 시스템 및 DB와의 연결을 담당합니다.
- adapter: 저장소와 상호작용하기 위해 리포지토리 포트를 구현합니다.

## 테이블 구조
![erd image](docs/images/movie_reservation_ERD.png)
테이블 : 영화, 상영관, 상영정보, 좌석, 상영좌석, 예매, 예매좌석, 회원

>- 공통코드는 테이블을 별도로 생성하지 않고, ENUM으로 관리(영상물 등급, 장르)


