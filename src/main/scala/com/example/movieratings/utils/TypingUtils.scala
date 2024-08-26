package com.example.movieratings.utils

object TypingUtils {

   def toIntSafe(s: String): Option[Int] = {
    try {
      Some(s.toInt)
    } catch {
      case _: NumberFormatException => None
    }
  }

}
