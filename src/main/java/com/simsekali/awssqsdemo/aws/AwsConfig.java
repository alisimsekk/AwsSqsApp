package com.simsekali.awssqsdemo.aws;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.simsekali.awssqsdemo.exception.AwsCredentialProviderErrorException;
import com.simsekali.awssqsdemo.util.LocalDateSerializer;
import com.simsekali.awssqsdemo.util.LocalDateTimeSerializer;
import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory;
import io.awspring.cloud.sqs.listener.acknowledgement.handler.AcknowledgementMode;
import io.awspring.cloud.sqs.support.converter.MessagingMessageConverter;
import io.awspring.cloud.sqs.support.converter.SqsMessagingMessageConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.util.StringUtils;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.Message;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
@RequiredArgsConstructor
public class AwsConfig {

    @Value("${spring.cloud.aws.credentials.access-key}")
    private String accessKey;
    @Value("${spring.cloud.aws.credentials.secret-key}")
    private String secretKey;
    @Value("${spring.cloud.aws.region.static}")
    private String region;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_DATE;//yyyy-MM-dd

    @Bean
    public AwsCredentialsProvider awsCredentialsProvider() {
        if (!(StringUtils.isEmpty(StringUtils.isEmpty(accessKey) && StringUtils.isEmpty(secretKey))))
            return StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey));
        throw new RuntimeException("AWS credentials provider not configured");
    }

    @Bean
    public SqsMessageListenerContainerFactory<Object> defaultSqsListenerContainerFactory(SqsAsyncClient sqsAsyncClient) {
        SqsMessageListenerContainerFactory<Object> factory = new SqsMessageListenerContainerFactory<>();
        factory.setSqsAsyncClient(sqsAsyncClient);
        factory.configure(sqsContainerOptionsBuilder -> sqsContainerOptionsBuilder
                .messageConverter(getConverters())
                .acknowledgementMode(AcknowledgementMode.MANUAL)
                .maxMessagesPerPoll(2)
                .pollTimeout(Duration.ofSeconds(8)).build());
        return factory;
    }

    private MessagingMessageConverter<Message> getConverters() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        module.addSerializer(LocalDate.class, new LocalDateSerializer());
        module.addDeserializer(LocalDate.class, new LocalDateDeserializer(DATE_TIME_FORMATTER));

        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.modules(new JavaTimeModule(), module);
        builder.featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        builder.featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        SqsMessagingMessageConverter messageConverter = new SqsMessagingMessageConverter();
        MappingJackson2MessageConverter jacksonMessageConverter = new MappingJackson2MessageConverter();
        jacksonMessageConverter.setSerializedPayloadClass(String.class);
        jacksonMessageConverter.setStrictContentTypeMatch(true);
        jacksonMessageConverter.setObjectMapper(builder.build());
        messageConverter.setPayloadMessageConverter(jacksonMessageConverter);

        return messageConverter;
    }

}
