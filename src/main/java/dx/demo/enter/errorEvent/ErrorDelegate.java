package dx.demo.enter.errorEvent;

import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import java.io.Serializable;

/**
 * @description: 测试服务任务中抛出异常
 * @author: zhang.da.xin
 * @create: 2019-05-14 16:42
 **/


public class ErrorDelegate implements JavaDelegate, Serializable {

    @Override
    public void execute(DelegateExecution execution) {
        throw new BpmnError("xxx");
    }
}
