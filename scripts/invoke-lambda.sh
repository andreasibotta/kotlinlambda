
aws --endpoint-url http://localhost:4574 lambda invoke --function-name kotlin-hello --payload '{"message": "Hello there"}' output.txt
