package cn.tedu.web;

import cn.tedu.bean.User;
import cn.tedu.service.UserService;
import cn.tedu.utils.Md5Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * 用户登录功能
 *
 * @author wangyong
 * @date 2020/6/17 16:47
 */
@WebServlet(name = "LoginServletServlet", urlPatterns = "/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取参数
        String username = request.getParameter("username");
        //对密码进行加密后查询
        String password = Md5Utils.md5(request.getParameter("password"));
        System.out.println("username:" + username + "@password:" + password);
        //获取用户名是否是勾选状态，如果勾选，remname = true;
        String remname = request.getParameter("remname");

        //2.调用UserService
        UserService userService = new UserService();
        User user = userService.findUserByUsernameAndPassword(username, password);

        if (user != null) {
            //用户名和密码必须要正确，才判断是否勾选了记住用户名
            //避免魔法值
            String remNameVal = "true";
            Cookie cookie;
            if (remNameVal.equals(remname)) {
                //勾选了，通过cookie交给浏览器保存
                //由于cookie是通过http协议响应发送给浏览器，所以需要单独进行编码
                cookie = new Cookie("remname",
                        URLEncoder.encode(username, "utf-8"));
                cookie.setPath(request.getContextPath() + "/");
                //设置保存时间为一天
                cookie.setMaxAge(60 * 60 * 24);
            } else {
                //没有勾选,则删除之前记住的cookie
                cookie = new Cookie("remname",
                        URLEncoder.encode(username, "utf-8"));
                cookie.setPath(request.getContextPath() + "/");
                //设置保存时间为0 ,删除cookie
                cookie.setMaxAge(0);
            }
            response.addCookie(cookie);
            //去登录
            //将User 对象保存到Session中
            request.getSession().setAttribute("user", user);

            //重定向到首页
            response.sendRedirect(request.getContextPath() + "/index.jsp");

        } else {
            //用户名或密码错误，跳转到登录页面
            request.setAttribute("msg", "用户名或密码错误！");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }
    }
}
