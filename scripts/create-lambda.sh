
aws --endpoint-url http://localhost:4574 lambda delete-function  --function-name kotlin-hello

aws --endpoint-url http://localhost:4574 lambda create-function --region us-east-1 --function-name kotlin-hello \
--zip-file fileb://build/libs/kotlinlambda-all.jar \
--role arn:aws:iam:1234:role/lambda_basic_execution \
--handler com.kotlinlambda.App --runtime java8 \
--timeout 15 --memory-size 128
