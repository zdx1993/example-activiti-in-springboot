package dx.demo.enter.javaconfig.activiti;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * @description: activiti用户任务监听
 * @author: zhang.da.xin
 * @create: 2019-05-16 16:53
 **/


public class TaskListenerTest implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {

        System.out.println("用户任务监听执行");
        delegateTask.addCandidateUser("zhangsan");
        delegateTask.addCandidateUser("zhaoliu");
    }
}
