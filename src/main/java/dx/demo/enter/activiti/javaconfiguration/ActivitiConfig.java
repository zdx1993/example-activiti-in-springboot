package dx.demo.enter.activiti.javaconfiguration;

import org.activiti.engine.*;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

/**
 *  注意此处连接池是直接注入的,如果引入连接池的启动器时,在yml文件配置好之后,直接在这里@Autowired就可以了
 *  如果没有使用连接池的启动器,则需要手动通过@JavaConfiguration的形式将连接池配置完毕,然后再注入activiti的配置中
 *  值得注意的一点是,其实这里跟xml配置bean的方式是完全一样的!!!
 * 使用Java类完成配置文件
 */
//@Configuration
public class ActivitiConfig {

	@Autowired
    private DataSource dataSource;
    @Autowired
    private ResourcePatternResolver resourceLoader;
    
    /**
     * 初始化配置，将创建28张表
     * @return
     */
    @Bean
    public StandaloneProcessEngineConfiguration processEngineConfiguration() {
        StandaloneProcessEngineConfiguration configuration = new StandaloneProcessEngineConfiguration();
        configuration.setDataSource(dataSource);
        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        configuration.setAsyncExecutorActivate(false);
        return configuration;
    }
    
    @Bean
    public ProcessEngine processEngine() {
        return processEngineConfiguration().buildProcessEngine();
    }

    @Bean
    public RepositoryService repositoryService() {
        return processEngine().getRepositoryService();
    }

    @Bean
    public RuntimeService runtimeService() {
        return processEngine().getRuntimeService();
    }

    @Bean
    public TaskService taskService() {
        return processEngine().getTaskService();
    }
    
    /**
     * 部署流程
     * @throws IOException 
     */
//    @PostConstruct
//    public void initProcess() throws IOException {
//        DeploymentBuilder deploymentBuilder= repositoryService().createDeployment();
////        Resource resource = resourceLoader.getResource("classpath:/processes/EceProvinceProcess.bpmn");
////        deploymentBuilder .enableDuplicateFiltering().addInputStream(resource.getFilename(), resource.getInputStream()).name("deploymentTest").deploy();
//        deploymentBuilder .enableDuplicateFiltering().addClasspathResource("TestProcess.bpmn").name("deploymentTest").deploy();
//    }
}
