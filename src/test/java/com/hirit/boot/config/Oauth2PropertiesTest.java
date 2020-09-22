//package com.hirit.boot.config;
//
//
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//import java.security.KeyPair;
//import java.security.PublicKey;
//import java.security.interfaces.RSAPublicKey;
//
//import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
//import org.bouncycastle.cert.X509CertificateHolder;
//import org.bouncycastle.openssl.PEMKeyPair;
//import org.bouncycastle.openssl.PEMParser;
//import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class Oauth2PropertiesTest {
//
//    @Autowired
//    Oauth2Properties properties;
//
//    @Test
//    public void propertiesTest() {
//        assertThat("hirit-server").isEqualTo(properties.getResourceId());
//        assertThat(3600).isEqualTo(properties.getExpiredPeriod());
//        System.out.println("properties.get = " + properties.getPublicKey());
//    }
//
//    @Autowired
//    PasswordEncoder passwordEncoder;
//
//    @Test
//    public void password() {
//        String password = "hiritpw";
//        String encode = passwordEncoder.encode(password);
//        System.out.println("encode = " + encode);
//    }
//
//    @Test
//    public void getPublicKeyFromPEM() throws IOException {
//        FileReader fileReader = new FileReader(new File("/Users/ksm/Downloads/test-user.pem"));
//
//        PublicKey key;
//
//        try (PEMParser pem = new PEMParser(fileReader)) {
//            JcaPEMKeyConverter jcaPEMKeyConverter = new JcaPEMKeyConverter();
//            Object pemContent = pem.readObject();
//            if (pemContent instanceof PEMKeyPair) {
//                PEMKeyPair pemKeyPair = (PEMKeyPair) pemContent;
//                KeyPair keyPair = jcaPEMKeyConverter.getKeyPair(pemKeyPair);
//                key = keyPair.getPublic();
//            } else if (pemContent instanceof SubjectPublicKeyInfo) {
//                SubjectPublicKeyInfo keyInfo = (SubjectPublicKeyInfo) pemContent;
//                key = jcaPEMKeyConverter.getPublicKey(keyInfo);
//            } else if (pemContent instanceof X509CertificateHolder) {
//                X509CertificateHolder cert = (X509CertificateHolder) pemContent;
//                key = jcaPEMKeyConverter.getPublicKey(cert.getSubjectPublicKeyInfo());
//            } else {
//                throw new IllegalArgumentException("Unsupported public key format '" +
//                    pemContent.getClass().getSimpleName() + '"');
//            }
//        }
//
//        System.out.println("key = " + key);
//        System.out.println("key.getFormat() = " + key.getFormat());
//        System.out.println("key.getFormat() = " + key.getAlgorithm());
//        System.out.println(" = " + new String(key.getEncoded()));
//
//
//    }
//
//
//}