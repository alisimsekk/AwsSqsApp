#!/bin/bash
set -x

echo "Starting SQS Queue Creation..."

docker-compose exec sqs-demo-localstack awslocal sqs create-queue \
  --queue-name UserSignUpDeadLetter.fifo \
  --attributes '{"FifoQueue":"true"}'

docker-compose exec sqs-demo-localstack awslocal sqs create-queue \
  --queue-name UserSignUp.fifo \
  --attributes '{
    "FifoQueue":"true",
    "RedrivePolicy":"{\"deadLetterTargetArn\":\"arn:aws:sqs:us-east-1:000000000000:UserSignUpDeadLetter.fifo\",\"maxReceiveCount\":\"3\"}"
  }'

echo "Listing Queues:"
docker-compose exec sqs-demo-localstack awslocal sqs list-queues

echo "SQS Queue Creation Completed."

set +x