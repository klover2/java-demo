package com.klover.pgp;

import org.bouncycastle.bcpg.*;
import org.bouncycastle.bcpg.sig.Features;
import org.bouncycastle.bcpg.sig.KeyFlags;
import org.bouncycastle.jcajce.util.BCJcaJceHelper;
import org.bouncycastle.jcajce.util.JcaJceHelper;
import org.bouncycastle.jce.spec.ECNamedCurveGenParameterSpec;
import org.bouncycastle.openpgp.*;
import org.bouncycastle.openpgp.operator.PBESecretKeyEncryptor;
import org.bouncycastle.openpgp.operator.PGPContentSignerBuilder;
import org.bouncycastle.openpgp.operator.PGPDigestCalculator;
import org.bouncycastle.openpgp.operator.jcajce.JcaPGPContentSignerBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcaPGPDigestCalculatorProviderBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcaPGPKeyPair;
import org.bouncycastle.openpgp.operator.jcajce.JcePBESecretKeyEncryptorBuilder;

import java.io.IOException;
import java.io.OutputStream;
import java.security.*;
import java.util.*;

/**
 * PgpGenerateUtil
 *
 * @author klover
 * @date 2024/3/13 19:57
 */
public class PgpGenerateUtil {
    private static final JcaJceHelper helper = new BCJcaJceHelper();
    private static final int SIG_HASH = HashAlgorithmTags.SHA512;
    private static final int[] HASH_PREFERENCES = new int[]{
            HashAlgorithmTags.SHA512, HashAlgorithmTags.SHA384, HashAlgorithmTags.SHA256, HashAlgorithmTags.SHA224
    };
    private static final int[] SYM_PREFERENCES = new int[]{
            SymmetricKeyAlgorithmTags.AES_256, SymmetricKeyAlgorithmTags.AES_192, SymmetricKeyAlgorithmTags.AES_128
    };
    private static final int[] COMP_PREFERENCES = new int[]{
            CompressionAlgorithmTags.ZLIB, CompressionAlgorithmTags.BZIP2, CompressionAlgorithmTags.ZLIB, CompressionAlgorithmTags.UNCOMPRESSED
    };

    public static void generateAndExportKeyRing(
            OutputStream secretOut,
            OutputStream publicOut,
            String identity,
            char[] passphrase
    )
            throws IOException, NoSuchProviderException, PGPException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        secretOut = new ArmoredOutputStream(secretOut, new Hashtable<>());

        PGPDigestCalculator sha1Calc = new JcaPGPDigestCalculatorProviderBuilder().build().get(HashAlgorithmTags.SHA1);


        KeyPairGenerator eddsaGen = helper.createKeyPairGenerator("EdDSA");
        KeyPairGenerator xdhGen = helper.createKeyPairGenerator("XDH");

        PGPContentSignerBuilder contentSignerBuilder = new JcaPGPContentSignerBuilder(PublicKeyAlgorithmTags.EDDSA_LEGACY, SIG_HASH);
        PBESecretKeyEncryptor secretKeyEncryptor = new JcePBESecretKeyEncryptorBuilder(SymmetricKeyAlgorithmTags.AES_256, sha1Calc)
                .build(passphrase);

        Date now = new Date();

        eddsaGen.initialize(new ECNamedCurveGenParameterSpec("ed25519"));
        KeyPair primaryKP = eddsaGen.generateKeyPair();
        PGPKeyPair primaryKey = new JcaPGPKeyPair(PGPPublicKey.EDDSA_LEGACY, primaryKP, now);
        PGPSignatureSubpacketGenerator primarySubpackets = new PGPSignatureSubpacketGenerator();
        primarySubpackets.setKeyFlags(true, KeyFlags.CERTIFY_OTHER);
        primarySubpackets.setPreferredHashAlgorithms(false, HASH_PREFERENCES);
        primarySubpackets.setPreferredSymmetricAlgorithms(false, SYM_PREFERENCES);
        primarySubpackets.setPreferredCompressionAlgorithms(false, COMP_PREFERENCES);
        primarySubpackets.setFeature(false, Features.FEATURE_MODIFICATION_DETECTION);
        primarySubpackets.setIssuerFingerprint(false, primaryKey.getPublicKey());

        eddsaGen.initialize(new ECNamedCurveGenParameterSpec("ed25519"));
        KeyPair signingKP = eddsaGen.generateKeyPair();
        PGPKeyPair signingKey = new JcaPGPKeyPair(PGPPublicKey.EDDSA_LEGACY, signingKP, now);
        PGPSignatureSubpacketGenerator signingKeySubpacket = new PGPSignatureSubpacketGenerator();
        signingKeySubpacket.setKeyFlags(true, KeyFlags.SIGN_DATA);
        signingKeySubpacket.setIssuerFingerprint(false, primaryKey.getPublicKey());

        xdhGen.initialize(new ECNamedCurveGenParameterSpec("X25519"));
        KeyPair encryptionKP = xdhGen.generateKeyPair();
        PGPKeyPair encryptionKey = new JcaPGPKeyPair(PGPPublicKey.ECDH, encryptionKP, now);
        PGPSignatureSubpacketGenerator encryptionKeySubpackets = new PGPSignatureSubpacketGenerator();
        encryptionKeySubpackets.setKeyFlags(true, KeyFlags.ENCRYPT_COMMS | KeyFlags.ENCRYPT_STORAGE);
        encryptionKeySubpackets.setIssuerFingerprint(false, primaryKey.getPublicKey());

        PGPKeyRingGenerator gen = new PGPKeyRingGenerator(PGPSignature.POSITIVE_CERTIFICATION, primaryKey, identity,
                sha1Calc, primarySubpackets.generate(), null, contentSignerBuilder, secretKeyEncryptor);
        gen.addSubKey(signingKey, signingKeySubpacket.generate(), null, contentSignerBuilder);
        gen.addSubKey(encryptionKey, encryptionKeySubpackets.generate(), null);

        PGPSecretKeyRing secretKeys = gen.generateSecretKeyRing();
        secretKeys.encode(secretOut);

        secretOut.close();

        publicOut = new ArmoredOutputStream(publicOut, new Hashtable<>());


        List<PGPPublicKey> publicKeyList = new ArrayList<>();
        Iterator<PGPPublicKey> it = secretKeys.getPublicKeys();
        while (it.hasNext()) {
            publicKeyList.add(it.next());
        }

        PGPPublicKeyRing publicKeys = new PGPPublicKeyRing(publicKeyList);

        publicKeys.encode(publicOut);

        publicOut.close();
    }
}
