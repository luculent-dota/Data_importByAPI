package base;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
    "../spring-context.xml",
    "classpath:spring-context-cache.xml"
})
public class BaseTestSpringTestCase {

}
