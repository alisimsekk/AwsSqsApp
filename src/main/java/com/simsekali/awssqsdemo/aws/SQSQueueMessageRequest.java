package com.simsekali.awssqsdemo.aws;

import com.simsekali.awssqsdemo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SQSQueueMessageRequest {
    private User body;
    private boolean isDeadLetterQueueTest;
}
