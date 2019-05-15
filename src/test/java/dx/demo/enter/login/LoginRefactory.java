package dx.demo.enter.login;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 登陆逻辑重构
 * @author: zhang.da.xin
 * @create: 2019-05-15 15:01
 **/

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Slf4j
public class LoginRefactory {
    public static ThreadLocal<Map> mapThreadLocal = new ThreadLocal<>();

    @Autowired
    private FormService formService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ResourceLoader resourceLoader;

    @Test
    public void deploy() throws IOException {
        InputStream inputStream = resourceLoader.getResource("classpath:login/login.bpmn").getInputStream();
        Deployment deployment = repositoryService.createDeployment()
                .name("测试将登陆逻辑与activiti进行集成")
                .addInputStream("login.bpmn", inputStream)
                .deploy();
        //查看部署id
        System.out.println("部署id为:" + deployment.getId());
        //查看部署完成后自动帮我们在流程定义表中加入数据的id
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
        System.out.println("定义id为:" + processDefinition.getId());
    }

    @Test
    public void loginTest() {
        log.info("流程启动");
        //登陆的时候流程走向是由bpmn流程图决定的,我们只需要在合适的地方加入适当的代码就行了
        String definitionId = "myProcess:3:72504";
        //模拟从前端获取数据
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("username","dx");
        stringStringHashMap.put("password","123456");
        ProcessInstance processInstance = formService.submitStartFormData(definitionId, stringStringHashMap);
        //获取查询到的数据,或返回的错误结果
        Map map = mapThreadLocal.get();
    }
}
