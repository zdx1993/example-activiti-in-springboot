package dx.demo.enter;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @program: activiti-springboot
 * @description: activiti集成springboot程序的入口
 * @author: zhang.da.xin
 * @create: 2019-05-12 09:09
 **/

@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(MainApplication.class, args);
    }
}
