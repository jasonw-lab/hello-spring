package com.hello.jwt;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

/**
 */
//@RunWith(SpringRunner.class)
@SpringBootTest
public class RsaTest {

  	@Test
  public void test1() throws Exception {
    String jsonN =
        "pTSWJvy04hAU7ev7wmaTvpwHsEbseuPl0AlwoxPHnmoOIMATRT0eTqYpLJxDp4BHRFxDTrcUKHKVHGIA" +
            "Vut_-l6nkEI6ALOVW9C5PP9bXwqeHJ5tiGA6AMpaY1LsJJOd2lgExr0LHUPF2TtO4LOVmlptyGPTRSWhmRpPSc5"
            +
            "bjGFsyTFr78WmeixjEts9icAUCiBDdpwVw9qVdjJPsufyimqp8os5htm-DB_qKsnRwABVSQRKLw2y7Mr7NP31R0"
            +
            "7Mpr108dLS5Et8tKnFoiX0MHf5BbA50NG3DtyT27nSa14T0IvSWYJQhxPWXYtuXkVldpbZZn8E2B1VpcDXXGnSP"
            +
            "XoPstE9BU7w1RaF31UeEsE8sWDFdHgJMUYMfWjxVhdJB_NaVUt7v0X9QPffXcFPWKUUeVx0g_ONMz0bB_HltFSO"
            +
            "DWJeAvcZXk14u4sjpKXNIiK8dJzMe0Qn10m5nIdVtxzXfKHiDNobX1dMENDplhDMVk1eYS9x8dh90qt68Q59vKu"
            +
            "sm6iMjSaXtzwMfqJBwNck41e9Pie2m_cdSu-RIq1u3FScGkezzqNvIuzcse2y-6ApHkkaFMVsNw3CbD87LtVgNu"
            +
            "suom38UVC1rS0LeLYFinU3hnFwvB1UCv1_0Cs8CmrOmdifqd25aZIDf5p8f3kowq3QY1mNtdQc9-HgZ3k";
    String jsonE = "AQAB";

    byte[] nBytes = Base64.getUrlDecoder().decode(jsonN);
    byte[] eBytes = Base64.getUrlDecoder().decode(jsonE);

    BigInteger n = new BigInteger(1, nBytes); // need to use the sign-magnitude constructor
    BigInteger e = new BigInteger(1, eBytes);

    RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(n, e);
    KeyFactory rsaKeyFactory = KeyFactory.getInstance("RSA");
    PublicKey rsaPub = rsaKeyFactory.generatePublic(rsaPublicKeySpec);
    System.out.println(rsaPub);

  }

  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Test
  public void test2() throws Exception {
    String password = "123";

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    String digest = encoder.encode(password);
    System.out.println("ハッシュ値 = " + digest);

    String digest2 = encoder.encode(password);
    System.out.println("ハッシュ値2 = " + digest2);

    if (encoder.matches(password, digest)) {
      System.out.println("一致したよ");
//			return;
    } else {
      System.out.println("一致しなかったよ");
    }

    if (encoder.matches(password, "$10$VhgdS7tMnL410CTgS02WlOU5KU/NWeNHbm5vdOiXlVlbyh2BU79x6")) {
      System.out.println("一致したよ");
    } else {
      System.out.println("一致しなかったよ");
    }

		if (encoder.matches(password, "$2a$10$ymUNQRUfmeLh/YWmQHm5GeCe7cRKshgWTcKbA1whsZXhCowhowhT6")) {
			System.out.println("一致したよ");
		} else {
			System.out.println("一致しなかったよ");
		}

		return;
  }



  @Test
  public void test3() throws Exception {
    String password = "password";

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    String digest = encoder.encode(password);
    System.out.println("ハッシュ値 = " + digest);

    String digest2 = encoder.encode(password);
    System.out.println("ハッシュ値2 = " + digest2);

    if (encoder.matches(password, digest)) {
      System.out.println("一致したよ");
//			return;
    } else {
      System.out.println("一致しなかったよ");
    }

    if (encoder.matches(password, "$10$VhgdS7tMnL410CTgS02WlOU5KU/NWeNHbm5vdOiXlVlbyh2BU79x6")) {
      System.out.println("一致したよ");
    } else {
      System.out.println("一致しなかったよ");
    }

    if (encoder.matches(password, "$2a$10$ymUNQRUfmeLh/YWmQHm5GeCe7cRKshgWTcKbA1whsZXhCowhowhT6")) {
      System.out.println("一致したよ");
    } else {
      System.out.println("一致しなかったよ");
    }


    return;
  }
}
