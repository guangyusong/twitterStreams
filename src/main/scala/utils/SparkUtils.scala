package org.twitterStreams.utils

import org.apache.spark.sql.SparkSession

object SparkUtils {

  def sparkSession(
      appName: String = "twitter-spark-app",
      masterURL: String = "local[*]"
  ): SparkSession = {
    lazy val spark = SparkSession
      .builder()
      .appName(appName)
      .master(masterURL)
      .getOrCreate()
    spark
  }

}
