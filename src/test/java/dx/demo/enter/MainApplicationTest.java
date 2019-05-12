package dx.demo.enter;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.Execution;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;


@SpringBootTest
@RunWith(SpringRunner.class)
public class MainApplicationTest {
    @Autowired
    private RuntimeService runtimeService;

    @Test
    public void t01(){
        List<Execution> executions = runtimeService.createExecutionQuery().listPage(0, 100);
        executions.stream().forEach(s -> {
            System.out.println("---");
            System.out.println(s);
        });
    }
}