package org.twitterStreams.services

import scala.concurrent.ExecutionContext.Implicits.global

import com.amazonaws.auth.{
  BasicAWSCredentials,
  AWSStaticCredentialsProvider,
  DefaultAWSCredentialsProviderChain
}
import com.amazonaws.services.comprehend.AmazonComprehendClient
import com.amazonaws.services.comprehend.model.{
  DetectSentimentRequest,
  DetectSentimentResult
}
object AWSService {

  def getSentimentScore(res: DetectSentimentResult): Int =
    res.getSentiment match {
      case "POSITIVE" => (res.getSentimentScore.getPositive * 100).toInt
      case "NEGATIVE" => (res.getSentimentScore.getNegative * -100).toInt
      case _          => 0 // Other cases
    }

  def getSentiment(s: String): Int = {
    // val credentials = new AWSStaticCredentialsProvider(  // Alternative way to hardcode credentials
    //   new BasicAWSCredentials(
    //     "", // Access key
    //     ""  // Secret key
    //   )
    // )
    val credentials = DefaultAWSCredentialsProviderChain.getInstance

    val comClient = AmazonComprehendClient.builder
      .withCredentials(
        credentials
      )
      .withRegion("us-east-2")
      .build
    println("Getting Sentiment...")
    val req = new DetectSentimentRequest()
      .withText(s)
      .withLanguageCode("en")
    val res = comClient.detectSentiment(req)
    getSentimentScore(res)
  }
}
