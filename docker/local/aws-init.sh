#!/bin/bash
set -x
awslocal sqs create-queue --queue-name SqsDemoUserSignUpDeadLetter.fifo --attributes '{"FifoQueue": "true"}'
awslocal sqs create-queue --queue-name SqsDemoUserSignUp.fifo --attributes '{"FifoQueue": "true", "RedrivePolicy": "{\"deadLetterTargetArn\":\"SqsDemoUserSignUpDeadLetter.fifo\",\"maxReceiveCount\":\"3\"}"}'
set +x