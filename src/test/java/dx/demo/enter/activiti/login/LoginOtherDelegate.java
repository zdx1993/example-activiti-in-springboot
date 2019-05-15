package dx.demo.enter.activiti.login;

import dx.demo.enter.login.LoginRefactory;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import java.io.Serializable;
import java.util.Map;

/**
 * @description: 认证成功后一些其他的操作
 * @author: zhang.da.xin
 * @create: 2019-05-15 15:30
 **/

@Slf4j
public class LoginOtherDelegate implements JavaDelegate, Serializable {

    @Override
    public void execute(DelegateExecution execution) {
        log.info("登录其他操作执行了");
        System.out.println("登录其他操作执行了");

        //获取当前流程节点能取得的所有流程参数---其实是为了获得上一步的用户信息,可以检查sql查询次数
        Map<String, Object> variables = execution.getVariables();
        //一些别的操作对原有数据进行完善
        variables.put("score","99");
        LoginRefactory.mapThreadLocal.set(variables);
    }
}
