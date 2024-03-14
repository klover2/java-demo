package com.klover.pgp.domain.dto;

import lombok.Data;

/**
 * PgpKeyDTO
 *
 * @author klover
 * @date 2024/3/13 19:58
 */
@Data
public class PgpKeyDTO {
    /**
     * 公钥
     */
    private String publicKey;

    /**
     * 私钥
     */
    private String privateKey;

    /**
     * 密码
     */
    private String passphrase;
}
