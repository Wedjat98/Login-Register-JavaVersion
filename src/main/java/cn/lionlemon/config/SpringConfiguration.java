package cn.lionlemon.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

//表示该类是一个Spring的核心配置类
@Configuration // <context:component-scan base-package="com.lionlemon"/>
@ComponentScan("cn.lionlemon")
//<context:property-placeholder location="classpath:jdbc.properties"/>
@PropertySource("classpath:jdbc.properties")
//<import resource="applicationContext-dao.xml"/>
@Import({DataSourceConfiguration.class})//参数是个数组，可以写多个引入 用,号隔开
public class SpringConfiguration {
}
