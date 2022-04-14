package cn.lionlemon.util;


import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

//JDBC的工具类  使用Druid连接池
public class JDBCUtils {

    private static DataSource dataSource;

    static {
        //加载配置文件
        try {
            Properties properties = new Properties();
            //1.load（）中需要一个输入流  获取字节输入流
//            getRealPath();
            BufferedReader bufferedReader = new BufferedReader(new FileReader("C:\\Users\\ame98\\IdeaProjects\\LoginProject\\src\\main\\resources\\druid.properties"));
//            InputStream is = JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties");
            properties.load(bufferedReader);
            //2.初始化连接池对象
             dataSource = DruidDataSourceFactory.createDataSource(properties);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //获取连接池对象
    public static DataSource getDataSource(){
        return dataSource;
    }




    //获取Connection对象
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
