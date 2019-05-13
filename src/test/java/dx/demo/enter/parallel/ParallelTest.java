package dx.demo.enter.parallel;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @description: 测试并行网关中的一些注意点
 * @author: zhang.da.xin
 * @create: 2019-05-13 15:57
 **/

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ParallelTest {
    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private ResourceLoader resourceLoader;

    //<editor-fold desc="部署带有并行网关的流程">
    @Test
    public void deployment() throws IOException {
        InputStream inputStream = resourceLoader.getResource("classpath:parallel/parallel.bpmn").getInputStream();
        Deployment 部署带有并行网关的流程定义文件 = repositoryService.createDeployment()
                .name("部署带有并行网关的流程定义文件")
                .addInputStream("parallel.bpmn", inputStream)
                .deploy();
    }
    //</editor-fold>

    //<editor-fold desc="启动流程创建一个流程定义">
    @Test
    public void startProcessInstance() {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("dxParallel");
        System.out.println("getName:" + processInstance.getName());
        System.out.println("getId:" + processInstance.getId());
    }
    //</editor-fold>

}
