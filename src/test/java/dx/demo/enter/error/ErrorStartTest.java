package dx.demo.enter.error;

import dx.demo.enter.errorEvent.ErrorDelegate;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * @description: 测试事件子流程启动错误事件
 * @author: zhang.da.xin
 * @create: 2019-05-14 16:04
 **/

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ErrorStartTest {
    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private TaskService taskService;

    @Test
    public void test01() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:errorStart/throwError.bpmn");
        InputStream inputStream = resource.getInputStream();
        //部署流程文件,部署完成后会自动在流程定义表中存入相应的数据
        Deployment deploy = repositoryService.createDeployment()
                .name("测试事件子流程启动错误事件")
                .addInputStream("throwError.bpmn", inputStream)
                .deploy();
        //根据流程定义启动一个流程实例
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess");
        System.out.println("流程实例id:" + processInstance.getId());
        System.out.println("流程定义的key:" + processInstance.getProcessDefinitionKey());
    }
    @Test
    public void throwError(){
        //测试在任意一个地方抛出错误异常,开始错误事件能否可以触发
        throw new BpmnError("xxx");
    }

    @Test
    public void testJavaDelegate(){
        //启动流程时传入JavaDelegate对象
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("delegatePath",new ErrorDelegate());
        //启动时传入一个错误的流程实例
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("errorProcess", hashMap);
    }

    @Test
    public void testCompleteError() {
        //事件子流程中因为错误事件开始的流程中的用户任务
        taskService.complete("40012");
    }
}
