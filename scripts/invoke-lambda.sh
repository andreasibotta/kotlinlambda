
aws --endpoint-url http://localhost:4574 lambda invoke \
 --function-name kotlin-hello \
 --log-type Tail \
 --payload '{"message": "Hello there"}' output.txt
