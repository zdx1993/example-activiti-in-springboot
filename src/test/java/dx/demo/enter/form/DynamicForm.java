package dx.demo.enter.form;

import org.activiti.engine.FormService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.StartFormData;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @description: 测试动态表单的相关内容
 * @author: zhang.da.xin
 * @create: 2019-05-15 11:26
 **/

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DynamicForm {
    @Autowired
    private FormService formService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private IdentityService identityService;

    @Test
    public void test01() throws IOException {
        InputStream inputStream = resourceLoader.getResource("classpath:dynamicForm/dynamicForm.bpmn").getInputStream();
        Deployment deploy = repositoryService.createDeployment()
                .name("测试动态表单相关内容")
                .addInputStream("dynamicForm.bpmn", inputStream)
                .deploy();
        ProcessDefinition dxDynamic = repositoryService.createProcessDefinitionQuery().processDefinitionKey("dxDynamic").list().get(0);
        //根据流程定义启动一个流程实例通过formservice的方式
        StartFormData startFormData = formService.getStartFormData(dxDynamic.getId());

        List<FormProperty> formProperties = startFormData.getFormProperties();
        formProperties.stream().forEach(s -> {
            System.out.println(s.getType().getName());
        });

    }

    @Test
    public void startInstanceByForm() {
        ProcessDefinition dxDynamic = repositoryService.createProcessDefinitionQuery().processDefinitionKey("dxDynamic").list().get(0);
        StartFormData startFormData = formService.getStartFormData(dxDynamic.getId());
        List<FormProperty> formProperties = startFormData.getFormProperties();
        HashMap<String, String> hashMap = new HashMap<>();
        formProperties.stream().forEach(s -> {
            hashMap.put(s.getId(),"01/01/1999");
        });
        identityService.setAuthenticatedUserId("123123");
        ProcessInstance processInstance = formService.submitStartFormData(dxDynamic.getId(), hashMap);
        System.out.println("通过表单服务启动的流程实例,id为:" + processInstance.getId());
    }
}
