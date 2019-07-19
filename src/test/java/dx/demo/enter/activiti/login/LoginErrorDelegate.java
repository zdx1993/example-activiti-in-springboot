package dx.demo.enter.activiti.login;

import dx.demo.enter.login.LoginRefactory;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 登录失败的delegate
 * @author: zhang.da.xin
 * @create: 2019-05-15 15:41
 **/


public class LoginErrorDelegate implements JavaDelegate, Serializable {

    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("登录失败逻辑执行了");
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("err","登录失败+1");
        LoginRefactory.mapThreadLocal.set(stringStringHashMap);
    }
}
