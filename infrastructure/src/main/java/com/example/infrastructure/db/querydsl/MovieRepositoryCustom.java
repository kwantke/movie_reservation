package com.example.infrastructure.db.querydsl;

import com.example.application.dto.request.MovieSearchCriteria;
import com.example.domain.model.entity.Movie;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface MovieRepositoryCustom {


  List<Movie> findByFilters(MovieSearchCriteria movieSearchCriteria, Sort sort);
}
