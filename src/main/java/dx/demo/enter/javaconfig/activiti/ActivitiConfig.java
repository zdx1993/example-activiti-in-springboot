package dx.demo.enter.javaconfig.activiti;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @description: 在springboot中配置activiti
 * @author: zhang.da.xin
 * @create: 2019-05-12 09:18
 **/

@Configuration
@ImportResource(locations = {"classpath:activiti-context.xml"})
public class ActivitiConfig {

}
