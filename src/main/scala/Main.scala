package org.twitterStreams

import org.twitterStreams.services.TwitterService
import org.apache.log4j.BasicConfigurator

import com.typesafe.scalalogging.LazyLogging

object Main extends LazyLogging {

  def main(args: Array[String]): Unit = {
    BasicConfigurator.configure();

    logger.info("++++: Starting program...")

    val twitterClient = new TwitterService()
    twitterClient.processTweets(args(0))

    logger.info("++++: Ending program...")

  }
}
