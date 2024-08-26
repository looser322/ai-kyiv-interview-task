package com.example.movieratings.service

import com.example.movieratings.datasource.{DataExtractor, FileDataExtractor}
import com.example.movieratings.domain.{MovieInfo, MovieRating, MovieReportRow}
import com.example.movieratings.utils.CsvUtils

import java.io.File
import scala.language.postfixOps


object MovieReportGeneratorService {

  private final val NUMBER_OF_REVIEWS_INCLUSION_INT = 1000

  private final val MOVIE_REPORT_PERIOD_RANGE = 1970 to 1990

  private val MOVIE_REPORT_HEADERS = List("MovieTitle", "YearOfRelease", "AverageRating", "NumberOfReviews")

  private val dateExtractor: DataExtractor = FileDataExtractor

  def composeReport(movieTitlesPath: String, movieRatingsPath: String, outputPath: String) = {
    val movieTitles = dateExtractor.getMovieTitles(movieTitlesPath)
    val movieRatings = dateExtractor.getMovieRatings(movieRatingsPath)

    val reportRows = createMovieStatsReportByPeriodAndConditions(movieTitles, movieRatings)

    CsvUtils.writeToFile(
      MOVIE_REPORT_HEADERS +:
      reportRows.map(row => List(row.title, row.yearOfRelease.getOrElse(None), row.avgRating, row.numOfReviews)),
      new File(outputPath)
    )
  }

  def createMovieStatsReportByPeriodAndConditions(movieInfo: Map[Int, MovieInfo], movieRating: List[MovieRating]): List[MovieReportRow] = {
    movieRating.groupBy(_.movieId).map { case (movieId, ratings) =>
        val movie = movieInfo(movieId)
        val avgRating = ratings.map(_.rating).sum.toDouble / ratings.size
        MovieReportRow(
          movieId,
          movie.title,
          movie.year,
          avgRating,
          ratings.size
        )
      }
      .filter(_.numOfReviews > NUMBER_OF_REVIEWS_INCLUSION_INT)
      .filter(row => row.yearOfRelease.exists(MOVIE_REPORT_PERIOD_RANGE contains))
      .toList
      .sortBy(row => (row.avgRating, row.title))(
        Ordering.Tuple2(Ordering.Double.reverse, Ordering.String)
      )
  }
}
