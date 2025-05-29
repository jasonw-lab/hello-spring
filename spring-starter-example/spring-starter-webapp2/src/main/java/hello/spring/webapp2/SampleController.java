package hello.spring.webapp2;

import hello.spring.webapp2.logger.LogMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * サンプルコントローラー
 * @LogMethodアノテーションを使用してメソッドのロギングを実装
 * （スターターを使用せず、アプリケーション内で直接定義したアノテーションを使用）
 */
@RestController
@RequestMapping("/api")
public class SampleController {

    @GetMapping("/hello")
    @LogMethod("挨拶API")
    public String hello() {
        return "こんにちは、Spring Boot Starterを使わない世界へようこそ！";
    }

    @GetMapping("/hello/{name}")
    @LogMethod(value = "名前付き挨拶API", parameters = true, returnValue = true)
    public String helloWithName(@PathVariable String name) {
        return name + "さん、こんにちは！";
    }

    @GetMapping("/calculate/{a}/{b}")
    @LogMethod(value = "計算API", executionTime = true)
    public String calculate(@PathVariable int a, @PathVariable int b) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        int result = a + b;
        return a + " + " + b + " = " + result;
    }
}
