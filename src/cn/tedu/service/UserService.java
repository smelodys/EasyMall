package cn.tedu.service;

import cn.tedu.bean.User;
import cn.tedu.dao.UserDao;

/**
 * 用户注册用的Service
 *
 * @author wangyong
 * @date 2020/6/20 15:42
 */
public class UserService {
    /**
     * 引入dao
     */
    private UserDao userDao = new UserDao();

    /**
     * 通过用户名查询用户信息，并存储在User中
     *
     * @param username 用户名
     * @return 查询到的用户对象
     */
    public User findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }

    /**
     * 将User封装数据进行注册--向数据库写入User
     *
     * @param u 用户对象
     */
    public void addUser(User u) {
        userDao.addUser(u);
    }

    /**
     * 根据用户名密码查询用户
     *
     * @param username 用户名
     * @param password 密码
     * @return User对象
     */
    public User findUserByUsernameAndPassword(String username, String password) {

        return userDao.findUserByUsernameAndPassword(username,password);
    }
}
