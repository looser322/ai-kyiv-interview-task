package com.example.movieratings.domain

import java.time.LocalDate


case class MovieInfo(id: Int, year: Option[Int], title: String)

case class MovieRating(movieId: Int, customerId: Int, rating: Int, date: LocalDate)

case class MovieReportRow(movieId: Int, title: String, yearOfRelease: Option[Int], avgRating: Double, numOfReviews: Int)