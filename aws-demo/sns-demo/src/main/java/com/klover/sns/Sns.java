package com.klover.sns;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Sns
 * <a href="https://github.com/awsdocs/aws-doc-sdk-examples/blob/main/javav2/example_code/sns/src/main/java/com/example/sns/PublishTopic.java">sns 代码</a>
 *
 * @author klover
 * @date 2024/3/28 15:05
 */
public class Sns {
    private static final SnsClient snsClient;

    private static final String accessKeyId = "<your access key id>";

    private static final String secretAccessKey = "<your secret access key>";

    private static final String topicArn = "<your topic ARN>";

    static {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(
                accessKeyId,
                secretAccessKey
        );
        snsClient = SnsClient.builder()
                .region(Region.US_WEST_1)
                .credentialsProvider(() -> credentials)
                .build();
    }

    /**
     * 推送消息
     *
     * @param message
     */
    public static void pubTopic(String message) {
        try {
            // 通过筛选策略 指定推送者
            Map<String, MessageAttributeValue> map = new HashMap<>();
            map.put("clientId", MessageAttributeValue.builder().dataType("String").stringValue("123456").build());

            PublishRequest request = PublishRequest.builder()
                    .message(message)
                    .topicArn(topicArn)
                    .messageAttributes(map)
                    .build();

            PublishResponse result = snsClient.publish(request);
            System.out.println(result.messageId() + " Message sent. Status is " + result.sdkHttpResponse().statusCode());
        } catch (SnsException e) {
            System.out.println(e.awsErrorDetails().errorMessage());
        }
    }

    /**
     * 订阅
     *
     * @param url
     * @param protocol http https
     */
    public static void subscribe(String url, String protocol) {
        try {
            SubscribeRequest request = SubscribeRequest.builder()
                    .protocol(protocol)
                    .endpoint(url)
                    .returnSubscriptionArn(true)
                    .topicArn(topicArn)
                    .build();

            SubscribeResponse result = snsClient.subscribe(request);
            System.out.println("Subscription ARN is " + result.subscriptionArn() + "\n\n Status is "
                    + result.sdkHttpResponse().statusCode());

            // 设置筛选策略
            SetSubscriptionAttributesRequest subscriptionAttributesRequest = SetSubscriptionAttributesRequest
                    .builder()
                    .subscriptionArn(result.subscriptionArn())
                    .attributeName("FilterPolicy") // 这个名称不能动
                    .attributeValue("{\"clientId\": [\"123457\"]}") // 底层要求 必须是对象 这个字段可以自己自定义
                    .build();
            SetSubscriptionAttributesResponse setSubscriptionAttributesResponse = snsClient.setSubscriptionAttributes(subscriptionAttributesRequest);
            System.out.println(setSubscriptionAttributesResponse.sdkHttpResponse().statusCode());
        } catch (SnsException e) {
            System.out.println(e.awsErrorDetails().errorMessage());
        }
    }

    /**
     * 取消订阅
     *
     */
    public static void unsubscribe(String subscriptionArn) {
        try {
            UnsubscribeRequest request = UnsubscribeRequest.builder()
                    .subscriptionArn(subscriptionArn)
                    .build();

            UnsubscribeResponse result = snsClient.unsubscribe(request);
            System.out.println("\n\nStatus was " + result.sdkHttpResponse().statusCode()
                    + "\n\nSubscription was removed for " + request.subscriptionArn());

        } catch (SnsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }
}
