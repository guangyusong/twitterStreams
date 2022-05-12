package org.twitterStreams

import org.twitterStreams.testutils.{StandardTest}
import org.twitterStreams.services.{AWSService}

import org.apache.log4j.BasicConfigurator

import scala.concurrent.ExecutionContext.Implicits.global

class AWSServiceTest extends StandardTest {
  BasicConfigurator.configure();

  "A tweet" when {
    "tested with AWS Comprehend" should {
      "be POSITIVE" in {
        val sentiment = // Get sentiment using AWS Comprehend
          AWSService
            .getSentiment(
              "I'm so happy! Hurray! I'm so happy! I'm so excited! I love this!"
            )
        println(sentiment)
        sentiment should (be > 0)
      }
      "be NEGATIVE" in {
        val sentiment = // Get sentiment using AWS Comprehend
          AWSService
            .getSentiment(
              "I'm terribly depressed and sad. There is no hope. Words cannot describe my anguish and disappointment at the state of things."
            )
        println(sentiment)
        sentiment should (be < 0)
      }
    }
  }
}
