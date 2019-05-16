package dx.demo.enter.claim;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;

/**
 * @description: 测试用户认领流程
 * @author: zhang.da.xin
 * @create: 2019-05-16 15:50
 **/

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ClaimTest {
    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private IdentityService identityService;

    @Test
    public void deploy(){
        Deployment deployment = repositoryService.createDeployment()
                .name("测试用户认领流程")
                .addClasspathResource("claim/companyClaim.bpmn")
                .addClasspathResource("claim/companyClaim.png")
                .deploy();
    }

    @Test
    public void startInstance() {

        //将initiator设置为业务系统的用户id
        identityService.setAuthenticatedUserId("dx-001");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("dxClaim","businessKey-110");
    }

    @Test
    public void completeCompanyTask(){
        //taskAssignee其实就是业务系统申请启动流程的那个用户
        Task task = taskService.createTaskQuery().processInstanceBusinessKey("businessKey-110").taskAssignee("dx-001").singleResult();
        //完成企业任务
        taskService.complete(task.getId());
        //完成企业任务后流程向后流转
    }

    //资料审核员查询自己的任务列表
    //在本步的任务创建生命周期,用户任务中已经设置了相关的用户组
    @Test
    public void queryCompanySubmit(){
        List<Task> zhaoliu = taskService.createTaskQuery().taskCandidateUser("zhaoliu").list();
    }

    //资料审核员拾取任务
    @Test
    public void claimTask(){
        taskService.claim("137502","zhaoliu");
    }

    //完成拾取的任务
    @Test
    public void completeTask(){
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("isPass","1");
        taskService.complete("137502",hashMap);
    }
}
