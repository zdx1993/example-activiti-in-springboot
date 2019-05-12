package dx.demo.enter.helloworld;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @description: 第一个小的demo
 * @author: zhang.da.xin
 * @create: 2019-05-12 14:47
 **/

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class HelloWorld {
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private TaskService taskService;

    //<editor-fold desc="部署流程定义">
    @Test
    public void deploymentProcessDefinition() {
        //调用service,告诉service我们是需要deployment还是query
        Deployment deploy = repositoryService
                .createDeployment() //创建一个部署对象
                .name("这是helloword部署的名称")
                .addClasspathResource("bpmn/MyProcess.bpmn")
                .addClasspathResource("bpmn/MyProcess.png")
                .deploy();
        //activiti与jpa操作很类似,上面相当于入库的操作,当我们操作入库时给我们返回一个对象,这个对象中就封装着数据库中的字段
        System.out.println(deploy.getId());
        System.out.println(deploy.getName());

    }
    //</editor-fold>

    //<editor-fold desc="启动流程实例">
    @Test
    public void startProcessInstance() {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("dxProcess");//key对应bpmn文件中的process标签的id值,key启动默认是最新的版本,是有点也是缺点
        //上一步也是一个入库操作,根据jpa的套路,返回的也是封装着数据库字段的对象!
        System.out.println("流程实例id" + processInstance.getId());
        System.out.println("流程定义id" + processInstance.getProcessDefinitionId());
        //当我们执行上述操作后,activiti操作相应的数据表,流程就表示启动了

    }
    //</editor-fold>

    //<editor-fold desc="根据办理人查询他自己的任务,别的办理人是不该查询到其他办理人的任务的">
    @Test
    public void findTaskByAssignee() {
        //其实taskService操作的就是RU_TASK表,这个表中有assignee字段,所以我们既可以通过assignee进行查询,也可以查询获得assignee字段的值
        List<Task> tasks = taskService.createTaskQuery().taskAssignee("李四").listPage(0, 100);
        tasks.stream().forEach(s -> {
            //其实能输出的很多
            System.out.println("任务id:" + s.getId());
            System.out.println("任务名称:" + s.getName());
            System.out.println("任务创建时间:" + s.getCreateTime());
            System.out.println("任务的办理人:" + s.getAssignee());
            System.out.println("流程实例的id:" + s.getProcessInstanceId());
        });
    }
    //</editor-fold>

    //<editor-fold desc="张三完成自己启动的申请请假任务,当任务完成后,任务就流转到李四了">
    @Test
    public void completeMyTask() {
        List<Task> tasks = taskService.createTaskQuery().taskAssignee("王五").listPage(0, 100);
        tasks.stream().forEach(s -> { //张三完成自己的所有任务(这里其实就是那一条请假任务)
            taskService.complete(s.getId());
        });
    }
    //</editor-fold>
}

