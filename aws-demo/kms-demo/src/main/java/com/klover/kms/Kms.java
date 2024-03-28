package com.klover.kms;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.kms.KmsClient;
import software.amazon.awssdk.services.kms.model.*;

import java.util.Base64;

/**
 * Kms
 * <a href="https://github.com/awsdocs/aws-doc-sdk-examples/blob/main/javav2/example_code/kms/src/main/java/com/example/kms/EncryptDataKey.java">docs</a>
 *
 * @author klover
 * @date 2024/3/28 19:14
 */
public class Kms {
    private static final KmsClient kmsClient;

    private static final String accessKeyId = "<your access key id>";

    private static final String secretAccessKey = "<your secret access key>";

    private static final String keyId = "<your key id>"; // 密钥keyId

    static {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(
                accessKeyId,
                secretAccessKey
        );

        kmsClient = KmsClient.builder()
                .region(Region.US_WEST_1)
                .credentialsProvider(() -> credentials)
                .build();

    }

    /**
     * 加密
     *
     * @return base64 加密好的数据
     */
    public static String encrypt(String message) {
        try {
            EncryptRequest encryptRequest = EncryptRequest.builder()
                    .keyId(keyId)
                    .plaintext(SdkBytes.fromByteArray(message.getBytes()))
                    .encryptionAlgorithm(EncryptionAlgorithmSpec.RSAES_OAEP_SHA_256)
                    .build();

            EncryptResponse response = kmsClient.encrypt(encryptRequest);
            String algorithm = response.encryptionAlgorithm().toString();
            System.out.println("The encryption algorithm is " + algorithm);

            // Get the encrypted data.
            SdkBytes ciphertext = response.ciphertextBlob();
            return Base64.getEncoder().encodeToString(ciphertext.asByteArray());
        } catch (KmsException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    /**
     * 解密
     */
    public static String decrypt(String encryptedMessage) {
        try {
            // 先base64解密
            byte[] decode = Base64.getDecoder().decode(encryptedMessage);

            DecryptRequest decryptRequest = DecryptRequest.builder()
                    .ciphertextBlob(SdkBytes.fromByteArray(decode))
                    .keyId(keyId)
                    .encryptionAlgorithm(EncryptionAlgorithmSpec.RSAES_OAEP_SHA_256)
                    .build();

            DecryptResponse decryptResponse = kmsClient.decrypt(decryptRequest);
            SdkBytes plaintext = decryptResponse.plaintext();

            return plaintext.asUtf8String();

        } catch (KmsException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}
