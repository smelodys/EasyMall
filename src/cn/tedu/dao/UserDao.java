package cn.tedu.dao;

import cn.tedu.bean.User;
import cn.tedu.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


/**
 * 用户dao
 *
 * @author wangyong
 * @date 2020/6/20 16:24
 */
public class UserDao {
    /**
     *  通过用户名查询判断用户是否注册
     * @param username 用户名
     * @return User对象
     */
    public User findUserByUsername(String username) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        System.out.println("wy:2020.8.8-test");
        try {
            //获取连接
            conn = JdbcUtils.getConnection();
            //用户名校验，如果用户名已经存在，跳转到注册页面并提示
            String sql = "select * from user where username=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            //已经被注册
            if (rs.next()) {
                //将查询到的数据存入到User对象中并返回
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setNickname(rs.getString("nickname"));
                user.setEmail(rs.getString("email"));
                return user;
            } else {
                //未查询到数据--返回null
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            JdbcUtils.close(conn, ps, rs);
        }

    }

    /**
     * 注册功能
     *
     * @param u 用户对象
     */
    public void addUser(User u) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //获取连接
            conn = JdbcUtils.getConnection();
            //用户名校验，如果用户名已经存在，跳转到注册页面并提示
            String sql = "insert into user values(null ,?,?,?,?)";
            //TODO 验证下面语句的作用 因为是根据提示添加的
            assert conn != null;
            ps = conn.prepareStatement(sql);
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getNickname());
            ps.setString(4, u.getEmail());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(conn, ps, rs);
        }
    }

    /**
     * 通过用户名和密码查询用户
     * @param username 用户名
     * @param password 密码
     * @return User对象
     */
    public User findUserByUsernameAndPassword(String username, String password) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //获取连接
            conn = JdbcUtils.getConnection();
            //用户名校验，如果用户名已经存在，跳转到注册页面并提示
            String sql = "select * from user where username=?and password=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            //已经被注册
            if (rs.next()) {
                //将查询到的数据存入到User对象中并返回
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setNickname(rs.getString("nickname"));
                user.setEmail(rs.getString("email"));
                return user;
            } else {
                //未查询到数据--返回null
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            JdbcUtils.close(conn, ps, rs);
        }

    }
}
