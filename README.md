# twitterStreams
A program to display recent tweets from any Twitter user and show the tweet sentiment from AWS Comprehend

## Setup
1. Request access to the [Twitter API](https://developer.twitter.com/en/docs/twitter-api/getting-started/getting-access-to-the-twitter-api)
2. Fill out Twitter Credentials in [application.conf](https://github.com/guangyusong/twitterStreams/blob/main/src/main/resources/application.conf)
3. Follow the steps here to setup the [AWS Comprehend CLI](https://docs.aws.amazon.com/comprehend/latest/dg/setting-up.html)


## Execution and Testing
To run the program, run the following in a terminal: `sbt "runMain org.twitterStreams.Main @USERNAME"`

Example: `sbt "runMain org.twitterStreams.Main @elonmusk"`

To run the tests, use `sbt test`

## Output
The output will be located in the `output` folder. Note that each time the program is run, it will overwrite the previous output

There will be a CSV with 3 columns, the timestamp, the message content, and an AWS Comprehend score between -100 and 100. The lower the number, the more negative the sentiment and vice versa. 0 stands for neutral sentiment.

### Example output

![Screen Shot 2022-05-12 at 7 42 17 PM](https://user-images.githubusercontent.com/15316444/168184605-b267c8e4-0f36-4e4a-8e92-9501e889ec88.png)

## Change the number of tweets

Search for `maxResults` and change the number from 100 to any number greater than 10. If you remove the line completely, the program will fetch the 10 most recent tweets.

https://github.com/guangyusong/twitterStreams/blob/main/src/main/scala/services/TwitterService.scala
