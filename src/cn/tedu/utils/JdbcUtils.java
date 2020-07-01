package cn.tedu.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * 数据库连接工具类
 * @author wangyong
 * @date 2020/6/22 17:55
 */
public class JdbcUtils {
    /**
     * 创建连接池
     */
    private static ComboPooledDataSource pool = new ComboPooledDataSource();
    /**
     * 获取连接
     */
    public static Connection getConnection(){
        try {
            return pool.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 释放资源
     */
    public static void close(Connection conn, Statement stat, ResultSet rs){
        if(rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                rs = null;
            }
        }
        if(stat != null){
            try {
                stat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                stat = null;
            }
        }
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                conn = null;
            }
        }
    }
}
