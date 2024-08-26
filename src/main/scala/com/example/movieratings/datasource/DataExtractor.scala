package com.example.movieratings.datasource

import com.example.movieratings.domain.{MovieInfo, MovieRating}

trait DataExtractor {

  def getMovieTitles(filePath: String): Map[Int, MovieInfo]

  def getMovieRatings(dirPath: String): List[MovieRating]

}


