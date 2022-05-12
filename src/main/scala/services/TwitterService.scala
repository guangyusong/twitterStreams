package org.twitterStreams.services

import com.alexdupre.twitter.TwitterClient
import com.alexdupre.twitter.models.ExpandedTweet
import com.alexdupre.twitter.TwitterClient.{TweetExpansion, TweetField}
import com.typesafe.scalalogging.LazyLogging

import org.twitterStreams.credentials.TwitterCredentials
import org.twitterStreams.utils.SparkUtils
import org.twitterStreams.config.ConfigUtils
import org.apache.spark.sql.{DataFrame, Dataset, SaveMode, SparkSession}
import org.apache.spark.sql.functions.not

import sttp.client3.{HttpURLConnectionBackend, Identity}

import scala.collection.mutable.ListBuffer
import scala.concurrent.ExecutionContext.Implicits.global

class TwitterService extends App with TwitterCredentials with LazyLogging {

  def run(f: TwitterClient[Identity] => Unit) = {
    implicit val backend = HttpURLConnectionBackend()
    val client = new TwitterClient(consumerKey, bearerToken)
    try {
      f(client)
    } finally {
      backend.close()
    }
  }

  def runjob(
      timestamps: ListBuffer[String],
      content: ListBuffer[String],
      sentimentList: ListBuffer[String]
  ) = {
    val spark: SparkSession = SparkSession
      .builder()
      .master("local[1]")
      .appName("twitter-project")
      .getOrCreate()

    import spark.implicits._
    val df = (
      timestamps,
      content,
      sentimentList
    ).zipped.toList.toDF(
      "timestamps",
      "content",
      "sentiment"
    ) // TODO: zipped only works with 3 items, use transpose
    df.coalesce(1)
      .write
      .mode(SaveMode.Overwrite)
      .csv("output")
    spark.stop()
  }

  def processTweets(query: String) = {

    // Twitter Client
    // val query = "@elonmusk" // TODO: Move this to the config
    val tweetExpansions =
      Set(
        TweetExpansion.AuthorId,
        TweetExpansion.InReplyToUserId
      ) // Fields we can potentially use later
    val tweetFields = Set(TweetField.AuthorId, TweetField.CreatedAt)

    var timestamps = new ListBuffer[String]();
    var content = new ListBuffer[String]();
    var sentimentList = new ListBuffer[String]();

    run { client =>
      val resp = client.getRecentTweets(
        query,
        expansions = tweetExpansions,
        tweetFields = tweetFields,
        maxResults = 100
      )
      resp.expandedTweets.foreach { t =>
        val sentiment = // Get sentiment using AWS Comprehend
          AWSService
            .getSentiment(
              t.tweet.text
            )
        logger.info(sentiment.toString)

        timestamps += t.tweet.createdAt.toString
        content += t.tweet.text
        sentimentList += sentiment.toString
      }
    }

    // Run the spark job
    runjob(timestamps, content, sentimentList)

    logger.info("Function completed")
  }
}
