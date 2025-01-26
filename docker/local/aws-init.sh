#!/bin/bash
set -x
awslocal sqs create-queue --queue-name SqsDemoLocalUserSignUpDeadLetter.fifo --attributes '{"FifoQueue": "true"}'
awslocal sqs create-queue --queue-name SqsDemoLocalUserSignUp.fifo --attributes '{"FifoQueue": "true", "RedrivePolicy": "{\"deadLetterTargetArn\":\"SqsDemoLocalUserSignUpDeadLetter.fifo\",\"maxReceiveCount\":\"3\"}"}'
set +x