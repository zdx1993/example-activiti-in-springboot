package dx.demo.enter.activiti.login;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @description: 用户认证委派类
 * @author: zhang.da.xin
 * @create: 2019-05-15 15:23
 **/

@Slf4j
public class LoginDelegate implements JavaDelegate, Serializable {

    @Override
    public void execute(DelegateExecution execution) {
        log.info("用户认证delegate执行");
        System.out.println("用户认证delegate执行");

        //在这里查询用户信息,如果用户存在的话,则将用户信息传递到下一个节点,如果用户不存在,则抛出异常,走异常流程
        boolean pass = true;
        //查询数据库逻辑代码
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("loginOtherDelegate",new LoginOtherDelegate());
            hashMap.put("loginErrorDelegate",new LoginErrorDelegate());
        if(pass){ //用户身份校验通过
            hashMap.put("userAddress","丈八四路口");
        }else{
            throw new BpmnError("loginError");
        }
            execution.setVariables(hashMap);
    }
}
