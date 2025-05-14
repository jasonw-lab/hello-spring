package hello.spring.aop;

import hello.spring.aop.service.SampleService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
    "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration"
})
public class TestAop {

    @Autowired
    private SampleService sampleService;

    @Test
    public void testSample() {
        sampleService.getRandomValue(100);
    }

    @Test
    public void test1() throws Exception {
        String password = "123";

    }

}
