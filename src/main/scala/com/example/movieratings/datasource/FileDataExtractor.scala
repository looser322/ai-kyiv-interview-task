package com.example.movieratings.datasource

import com.example.movieratings.domain.{MovieInfo, MovieRating}
import com.example.movieratings.utils.CsvUtils
import com.example.movieratings.utils.TypingUtils.toIntSafe
import org.apache.commons.csv.CSVRecord

import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object FileDataExtractor extends DataExtractor {

  private val MOVIE_RATINGS_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd")

  def getMovieTitles(filePath: String): Map[Int, MovieInfo] = {
    val file = new File(filePath)
    val records = CsvUtils.readFromFileAsList(file)

    records.map { record =>
      MovieInfo(
        record.get(0).toInt,
        toIntSafe(record.get(1)),
        record.get(2)
      )
    }.map(movieTitle => movieTitle.id -> movieTitle).toMap
  }

  def getMovieRatings(dirPath: String): List[MovieRating] = {
    val dir = new File(dirPath)

    if (!dir.exists || !dir.isDirectory) {
      throw new IllegalArgumentException(s"Provided path $dirPath is not a directory or does not exist")
    }

    val files = dir.listFiles()

    files.iterator.flatMap { file =>
      val csvRecords: Iterator[CSVRecord] = CsvUtils.readFromFileAsList(file).iterator
      val movieId = csvRecords.next().get(0).split(":")(0).toInt

      csvRecords.drop(1).map { record =>
        val customerId = record.get(0).toInt
        val rating = record.get(1).toInt
        val date = LocalDate.parse(record.get(2), MOVIE_RATINGS_DATE_FORMATTER)

        MovieRating(
          movieId,
          customerId,
          rating,
          date
        )
      }
    }.toList
  }
}
