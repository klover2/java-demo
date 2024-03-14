package com.klover.pgp;

import com.klover.pgp.domain.dto.PgpKeyDTO;
import org.bouncycastle.openpgp.PGPException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * PgpUtil
 *
 * @author klover
 * @date 2024/3/13 19:54
 */
public class PgpUtil {
    /**
     * pgp 解密
     *
     * @param message    密文
     * @param privateKey 私钥
     * @param passphrase 密码
     * @return 原文
     */
    public static String decrypt(String message, String privateKey, String passphrase) {
        try {
            PgpDecryptUtil pgpDecryptUtil = new PgpDecryptUtil(privateKey, passphrase);

            byte[] decrypt = pgpDecryptUtil.decrypt(message.getBytes(Charset.defaultCharset()));

            return new String(decrypt);
        } catch (PGPException | IOException e) {
            throw new RuntimeException("PGP decrypt failed");
        }
    }

    /**
     * pgp 加密
     *
     * @param message   原文
     * @param publicKey 公钥
     * @return 密文
     */
    public static String encrypt(String message, String publicKey) {
        try {
            PgpEncryptUtil pgpSecureUtil = new PgpEncryptUtil();

            byte[] encrypt = pgpSecureUtil.encrypt(message.getBytes(Charset.defaultCharset()), publicKey);

            return new String(encrypt);
        } catch (PGPException | IOException e) {
            throw new RuntimeException("PGP encrypt failed");
        }
    }

    /**
     * 生成pgp 公私钥
     *
     * @param passphrase 密码
     * @param email      邮箱
     * @return {@link PgpKeyDTO}
     */
    public static PgpKeyDTO generate(String passphrase, String email) {
        ByteArrayOutputStream privateKeyOut = new ByteArrayOutputStream();
        ByteArrayOutputStream publicKeyOut = new ByteArrayOutputStream();

        try {
            PgpGenerateUtil.generateAndExportKeyRing(privateKeyOut, publicKeyOut, email, passphrase.toCharArray());

            PgpKeyDTO dto = new PgpKeyDTO();
            dto.setPassphrase(passphrase);
            dto.setPublicKey(publicKeyOut.toString(StandardCharsets.UTF_8));
            dto.setPrivateKey(privateKeyOut.toString(StandardCharsets.UTF_8));
            return dto;
        } catch (PGPException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | IOException |
                 NoSuchProviderException e) {
            throw new RuntimeException("generate pgp key failed");
        }
    }

    /**
     * 生成pgp 公私钥
     *
     * @param passphrase 密码
     * @return {@link PgpKeyDTO}
     */
    public static PgpKeyDTO generate(String passphrase) {
        return generate(passphrase, "");
    }
}
