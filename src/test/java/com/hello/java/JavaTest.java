package com.hello.java;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@Slf4j
/** */
@SpringBootTest
public class JavaTest {

  private String getHoge() {

    return "123";
  }

    @Test
  public void test1() throws Exception {
    String password = "123";

    // Optionalオブジェクトを生成するofメソッドは、引数がnullだとNullPointerExceptionを投げる
    Optional<String> hogeOpt = Optional.of(getHoge());

    if (hogeOpt.isPresent()) { // 事前にわざわざ値の存在をチェックしている

      // 値を取得するgetメソッドは、値が存在していない場合実行時例外を投げる(NoSuchElementException)
      String hoge = hogeOpt.get();

      System.out.println(hoge);
    }

    return;
  }

  //  @Test
  public void test2() throws Exception {

    String str = "nonNull";
    Optional<String> nonNullString = Optional.of(str);

    System.out.println(nonNullString.isPresent());
    System.out.println(nonNullString.get());
    //    System.out.println(Optional.of(null));//ava.lang.NullPointerException

    str = null;
    Optional<String> nullableString = Optional.ofNullable(str);

    System.out.println(nullableString.isPresent()); // false
    System.out.println(nullableString.orElse("other")); // other

    str = null;
    nullableString = Optional.ofNullable(str);
    log.info(String.valueOf(nullableString.isPresent())); // false
    log.info(nullableString.orElse("other"));
    //    System.out.println(nullableString.get()); //other
    log.info(nullableString.orElse(null));

    return;


  }

    @Test
  public void testFinally() throws Exception {

    String fileContent = null;

    for (int i = 0; i < 2; i++) {


      try {
        // ファイルを読む。
        log.info("PDFファイル読む。");

        //       fileContent = FileUtil.readPdf(fileInputStream);
        //      if (StringUtil.isNullOrEmpty(fileContent)) {
        throw new Exception();
        //      }
        //        log.info(i + "  PDFファイル読み完了。");
      } catch (Exception e) {
        // PDFファイルの内容がない場合、メッセージ(I00002)を返却し、処理を終了する。
        log.warn(i + "  ファイル読み失敗。");
        log.warn(e.toString());
        //				throw new Exception(StatusCode.I00002);
        continue;

      } finally {

        log.warn(i + "finally");
      }

      //      log.info(i + " end ");
    } // end for
  }

  boolean check(String a, String b) {

    b = "error";
    return true;
  }

  @Test
  public void test3() throws Exception {

    String a, b;
    b = "test";
    check("test", b);
    log.info(b);

    return;
  }

  /**
       * 书
       */

      public static class Hoge {
          private String id;
          private String name;

          public Hoge(String id, String name) {
              super();
              this.id = id;
              this.name = name;
          }

          public Hoge() {
              super();
          }

          public String getId() {
              return id;
          }

          public void setId(String id) {
              this.id = id;
          }

          public String getName() {
              return name;
          }

          public void setName(String name) {
              this.name = name;
          }

  }
}
