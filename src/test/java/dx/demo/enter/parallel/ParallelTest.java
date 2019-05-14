package dx.demo.enter.parallel;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sound.midi.Soundbank;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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
    private TaskService taskService;

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

    //<editor-fold desc="忙猜查询当前执行的任务节点逻辑为,1.获得业务主键,通过业务主键查询执行实例,然后通过,执行实例id查询作为PARENT_ID查询当前实例对应的所有执行对象,通过执行对象就可以获取当前业务主键执行到哪一步了">
    @Test
    public void queryExecution() {
        Execution execution = runtimeService.createExecutionQuery().executionId("27501").singleResult();
        List<ProcessInstance> list = runtimeService.createProcessInstanceQuery().list();
        Execution execution1 = runtimeService.createExecutionQuery().executionId("27502").singleResult();
        Execution execution2 = runtimeService.createExecutionQuery().executionId("27505").singleResult();
    }

    //查询当前流程实例所有执行到哪一步了(此并行网关有两个执行对象)
    @Test
    public void queryExecutionAct() {
        List<Execution> list = runtimeService.createExecutionQuery().processInstanceId("27501").list();
    }
    //</editor-fold>
}
