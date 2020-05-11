
Resources
---------

* https://gist.github.com/crypticmind/c75db15fd774fe8f53282c3ccbe3d7ad
* https://docs.aws.amazon.com/lambda/latest/dg/welcome.html
* https://aws.amazon.com/blogs/compute/kotlin-and-groovy-jvm-languages-with-aws-lambda/
* https://www.raywenderlich.com/5777183-write-an-aws-lambda-function-with-kotlin-and-micronaut
* https://github.com/localstack/localstack/issues/1400
* https://github.com/aws-samples/lambda-kotlin-groovy-example/blob/master/kotlin/build.gradle


How to run
----------

`./gradlew shadowjar && sax monolith ./scripts/create-lambda.sh &&
 sax monolith ./scripts/invoke-lambda.sh && cat output.txt`

