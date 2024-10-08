package com.example.movieratings

import com.example.movieratings.service.MovieReportGeneratorService

object ReportGenerator {

  def main(args: Array[String]): Unit = {
    if (args.length != 3) {
      println("Application requires 3 arguments: movie_titles file, training_set path, and output file path.")
      System.exit(1)
    }

    val moviesFilePath = args(0)
    val trainingSetDir = args(1)
    val outputReportPath = args(2)

    MovieReportGeneratorService.composeReport(movieTitlesPath = moviesFilePath, movieRatingsPath = trainingSetDir, outputPath = outputReportPath)
  }
}
