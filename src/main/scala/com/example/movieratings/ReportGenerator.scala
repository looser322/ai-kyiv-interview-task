package com.example.movieratings

import com.example.movieratings.service.MovieReportGeneratorService

import scala.language.postfixOps

object ReportGenerator {

  def main(args: Array[String]): Unit = {
    if (args.length != 3) {
      println("Application requires 3 arguments: input file, output file, and report type.")
      System.exit(1)
    }

    val moviesFilePath = args(0)
    val trainingSetDir = args(1)
    val outputReportPath = args(2)

    MovieReportGeneratorService.composeReport(movieTitlesPath = moviesFilePath, movieRatingsPath = trainingSetDir, outputPath = outputReportPath)
  }
}
