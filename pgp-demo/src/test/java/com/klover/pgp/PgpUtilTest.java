package com.klover.pgp;


import com.klover.pgp.domain.dto.PgpKeyDTO;
import org.junit.jupiter.api.Test;

class PgpUtilTest {
    private final String publicKey = "-----BEGIN PGP PUBLIC KEY BLOCK-----\n" +
            "Version: BCPG v1.77.00\n" +
            "\n" +
            "mDMEZfKdCBYJKwYBBAHaRw8BAQdAPD3sJ8ZFnAWfDVqC1NzD/gkra4m4dR5f8iIC\n" +
            "2Z9dDuG0CjEyM0BxcS5jb22IjAQTFgoANAUCZfKdCAKbAQUVCgkICwQLCQgHBRYC\n" +
            "AwIAAh4BFiEElL6gSa1wpiqsyg5DlBisl7cXUyQACgkQlBisl7cXUyQ3XgEArZZn\n" +
            "yMX9mcYHWI4BNEkFg1KOR8FAdquRiTJd1K1ql5IA/0H58xxMTsbuzBB+w8cDVCdb\n" +
            "i0X8VzCSt9PPptp4mc4FuDMEZfKdCBYJKwYBBAHaRw8BAQdAVZ9+4RurisekJJWC\n" +
            "bqYdK1LEY1CPexUltUZM5LSHXIKI2AQYFgoAgAUCZfKdCAKbAhYhBJS+oEmtcKYq\n" +
            "rMoOQ5QYrJe3F1MkXyAEGRYKAAYFAmXynQgACgkQvZstiCMFV1cMygEA855dlv3w\n" +
            "0uudRSwXx5sd4YuqqmPdfSNwRB8cnKikp8wBAPgOzZgA7vPQQ3sFAK04bNO9/69p\n" +
            "6p5si8Y3qxuiWhAKAAoJEJQYrJe3F1MkN+UA/juKO88nHrLiIYPeJ3CfiP3rztTP\n" +
            "y4Eh097CEsqqWqceAP4hStAfvNYcEZ3pemnbrbopXKEGVHLeN+xS/kBP4yJsA7g4\n" +
            "BGXynQgSCisGAQQBl1UBBQEBB0Da4CePRN833tRihkTDcVJstIzT2iejEGtDvwd4\n" +
            "ndabLAMBCAeIeAQYFgoAIAUCZfKdCAKbDBYhBJS+oEmtcKYqrMoOQ5QYrJe3F1Mk\n" +
            "AAoJEJQYrJe3F1MkXQ0BAPQRIVd8O3/r1Oh3nwTHWmLhuDxQo6Hp617ZaMYNm1tp\n" +
            "AQCCR2zkp6ovpJ0VJoPCrZLCvOWhh2nv8JyM/iNNBsXbBg==\n" +
            "=/RiS\n" +
            "-----END PGP PUBLIC KEY BLOCK-----";

    private final String privateKey = "-----BEGIN PGP PRIVATE KEY BLOCK-----\n" +
            "Version: BCPG v1.77.00\n" +
            "\n" +
            "lIYEZfKdCBYJKwYBBAHaRw8BAQdAPD3sJ8ZFnAWfDVqC1NzD/gkra4m4dR5f8iIC\n" +
            "2Z9dDuH+CQMCdjTolDDV23VgUO5L+HjKLsgJfrtqwn9YHXK1H457Nf+8+5XWc+Ak\n" +
            "euHmfn8hfUCsuolqdGw+OsczgpUbed5i9PAAhNgSo6pu6w6JmwSgFrQKMTIzQHFx\n" +
            "LmNvbYiMBBMWCgA0BQJl8p0IApsBBRUKCQgLBAsJCAcFFgIDAgACHgEWIQSUvqBJ\n" +
            "rXCmKqzKDkOUGKyXtxdTJAAKCRCUGKyXtxdTJDdeAQCtlmfIxf2ZxgdYjgE0SQWD\n" +
            "Uo5HwUB2q5GJMl3UrWqXkgD/QfnzHExOxu7MEH7DxwNUJ1uLRfxXMJK308+m2niZ\n" +
            "zgWchgRl8p0IFgkrBgEEAdpHDwEBB0BVn37hG6uKx6QklYJuph0rUsRjUI97FSW1\n" +
            "RkzktIdcgv4JAwJ2NOiUMNXbdWBukhE4DvddloKwG9gVA2k6lV+Octt4ywEOZtdO\n" +
            "s7Z7hBiIh/722hnQeaJw+ZzA4V7iyyOfZfc7xXobvbpBio9S5PV67i5FiNgEGBYK\n" +
            "AIAFAmXynQgCmwIWIQSUvqBJrXCmKqzKDkOUGKyXtxdTJF8gBBkWCgAGBQJl8p0I\n" +
            "AAoJEL2bLYgjBVdXDMoBAPOeXZb98NLrnUUsF8ebHeGLqqpj3X0jcEQfHJyopKfM\n" +
            "AQD4Ds2YAO7z0EN7BQCtOGzTvf+vaeqebIvGN6sboloQCgAKCRCUGKyXtxdTJDfl\n" +
            "AP47ijvPJx6y4iGD3idwn4j9687Uz8uBIdPewhLKqlqnHgD+IUrQH7zWHBGd6Xpp\n" +
            "2626KVyhBlRy3jfsUv5AT+MibAOciwRl8p0IEgorBgEEAZdVAQUBAQdA2uAnj0Tf\n" +
            "N97UYoZEw3FSbLSM09onoxBrQ78HeJ3WmywDAQgH/gkDAnY06JQw1dt1YCHU/rfX\n" +
            "nHMYKTO0uHltc4Qhjs38znVKmKNkgZHzuu/PzQUx1oeYLiHbqZx9jJrxsSIPTmkZ\n" +
            "6Oh9aVr0kRFrGazLUbd7FeCIeAQYFgoAIAUCZfKdCAKbDBYhBJS+oEmtcKYqrMoO\n" +
            "Q5QYrJe3F1MkAAoJEJQYrJe3F1MkXQ0BAPQRIVd8O3/r1Oh3nwTHWmLhuDxQo6Hp\n" +
            "617ZaMYNm1tpAQCCR2zkp6ovpJ0VJoPCrZLCvOWhh2nv8JyM/iNNBsXbBg==\n" +
            "=wQI+\n" +
            "-----END PGP PRIVATE KEY BLOCK-----\n";

    private final String passphrase ="123456";

    @Test
    void testDecrypt() {
        String testMessage = PgpUtil.encrypt("test message", this.publicKey);

        System.out.println(testMessage);
    }

    @Test
    void testEncrypt() {
        String message = "-----BEGIN PGP MESSAGE-----\n" +
                "Version: BCPG v1.77.00\n" +
                "\n" +
                "wV4DhXFV+u5ReLESAQdAWnEMf99ijVNzKU/dGE8l338ZOmxFN5s6tB4387v6fCkw\n" +
                "TvJ8Bn/iDNPKJTIVRYNkuB0WqTqq8chDWMMfx7HTzMmD6T9CW5LzG8o1br26OCar\n" +
                "0kkB1jb4G4xsKqZOiSrijF2DY8mcgPONOH50b0XL4eSZoNHN2GOHbBkZP+ZvDlNG\n" +
                "H1ORNcFo8b7YjZwm2YR5JpXp7KD+tj+iCQ6U\n" +
                "=R2mH\n" +
                "-----END PGP MESSAGE-----";
        String decrypt = PgpUtil.decrypt(message, this.privateKey, this.passphrase);
        System.out.println(decrypt);
    }

    @Test
    void testGenerate() {
        PgpKeyDTO generate = PgpUtil.generate("123456", "123@qq.com");
        System.out.println(generate.getPublicKey());
        System.out.println(generate.getPrivateKey());
    }
}