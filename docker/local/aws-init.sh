#!/bin/bash
set -x

awslocal sqs create-queue \
  --queue-name UserSignUpDeadLetter.fifo \
  --attributes '{"FifoQueue":"true"}'

awslocal sqs create-queue \
  --queue-name UserSignUp.fifo \
  --attributes '{
    "FifoQueue":"true",
    "RedrivePolicy":"{\"deadLetterTargetArn\":\"arn:aws:sqs:us-east-1:000000000000:UserSignUpDeadLetter.fifo\",\"maxReceiveCount\":\"3\"}"
  }'

set +x