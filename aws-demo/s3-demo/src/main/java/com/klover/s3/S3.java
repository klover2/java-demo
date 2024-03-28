package com.klover.s3;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.File;
import java.io.InputStream;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * S3
 *
 * @author klover
 * @date 2024/3/27 20:05
 */
public class S3 {
    private static final S3Client s3;
    private static final String bucketName = "klover-test";

    private static final String accessKeyId = "<your access key id>";

    private static final String secretAccessKey = "<your secret access key>";

    private static final S3Presigner preSigner;

    static {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(
                accessKeyId,
                secretAccessKey
        );

        s3 = S3Client.builder()
                .region(Region.of("us-west-1"))
                .credentialsProvider(() -> credentials)
                .build();

        preSigner = S3Presigner.builder().region(Region.of("us-west-1"))
                .credentialsProvider(() -> credentials)
                .build();
    }

    public static String putFile(String fileName, File file) {
        return putObject(fileName, RequestBody.fromFile(file));
    }

    public static String putFile(String fileName, InputStream stream) {
        RequestBody requestBody = RequestBody.fromContentProvider(() -> stream, "application/octet-stream");
        return putObject(fileName, requestBody);
    }

    /**
     * 上传文件
     * <a href="https://docs.aws.amazon.com/zh_cn/AmazonS3/latest/userguide/example_s3_Scenario_PresignedUrl_section.html">生成预签名文档</a>
     *
     * @param fileName 文件名称
     * @param body     参数
     */
    public static String putObject(String fileName, RequestBody body) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("author", "Klover");
        metadata.put("version", "1.0.0");

        PutObjectRequest putOb = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .metadata(metadata)
                .build();

        s3.putObject(putOb, body);

        // 生成预签名url
        GetObjectRequest objectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        GetObjectPresignRequest preSignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(10))  // The URL will expire in 10 minutes.
                .getObjectRequest(objectRequest)
                .build();

        PresignedGetObjectRequest preSignedRequest = preSigner.presignGetObject(preSignRequest);

        return preSignedRequest.url().toExternalForm();
    }

    /**
     * 下载文件
     *
     * @param fileName 文件名称
     */
    public static InputStream getObject(String fileName) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucketName)
                .key(fileName).build();
        return s3.getObject(getObjectRequest);
    }


}
