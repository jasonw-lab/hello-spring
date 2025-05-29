package hello.spring.webapp1;

import hello.spring.logger.LogMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * サンプルコントローラー
 * @LogMethodアノテーションを使用してメソッドのロギングを実装
 */
@RestController
@RequestMapping("/api")
public class SampleController {

    private static final Logger logger = LoggerFactory.getLogger(SampleController.class);

    @GetMapping("/hello")
    @LogMethod("挨拶API")
    public String hello() {
        String result = "こんにちは、Spring Boot Starterの世界へようこそ！";
        return result;
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
