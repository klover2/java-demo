package com.klover.cloudwatchlog;

import com.klover.jackson.JsonUtil;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cloudwatchlogs.CloudWatchLogsClient;
import software.amazon.awssdk.services.cloudwatchlogs.model.DescribeLogStreamsRequest;
import software.amazon.awssdk.services.cloudwatchlogs.model.DescribeLogStreamsResponse;
import software.amazon.awssdk.services.cloudwatchlogs.model.InputLogEvent;
import software.amazon.awssdk.services.cloudwatchlogs.model.PutLogEventsRequest;

import java.util.Collections;
import java.util.Map;

/**
 * CloudWatchLog
 * <a href="https://github.com/awsdocs/aws-doc-sdk-examples/tree/main/javav2/example_code/cloudwatch/src/main">aws cloud watch</a>
 *
 * @author klover
 * @date 2024/3/20 14:41
 */
public class CloudWatchLog {
    private static final String accessKey = "<your key>";
    private static final String secretKey = "<your secret>";
    private static final String region = "us-west-1";
    // 日志组的名称
    private static final String groupName = "klover-test";
    // 日志流的名称
    private static final String streamName = "klover";

    private static CloudWatchLogsClient slsClient;

    static {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(
                accessKey,
                secretKey
        );
        slsClient = CloudWatchLogsClient.builder()
                .region(Region.of(region))
                .credentialsProvider(() -> credentials)
                .build();
    }

    /**
     * 上传日志
     *
     * @param params 参数
     */
    public static void putLog(Map<String, Object> params) {
        DescribeLogStreamsRequest logStreamRequest = DescribeLogStreamsRequest.builder()
                .logGroupName(groupName)
                .logStreamNamePrefix(streamName)
                .build();

        DescribeLogStreamsResponse describeLogStreamsResponse = slsClient.describeLogStreams(logStreamRequest);

        // Assume that a single stream is returned since a specific stream name was
        // specified in the previous request.
        String sequenceToken = describeLogStreamsResponse.logStreams().getFirst().uploadSequenceToken();

        // Build an input log message to put to CloudWatch.
        InputLogEvent inputLogEvent = InputLogEvent.builder()
                .message(JsonUtil.toJSONString(params))
                .timestamp(System.currentTimeMillis())
                .build();

        // Specify the request parameters.
        // Sequence token is required so that the log can be written to the
        // latest location in the stream.
        PutLogEventsRequest putLogEventsRequest = PutLogEventsRequest.builder()
                .logEvents(Collections.singletonList(inputLogEvent))
                .logGroupName(groupName)
                .logStreamName(streamName)
                .sequenceToken(sequenceToken)
                .build();

        slsClient.putLogEvents(putLogEventsRequest);
    }
}
