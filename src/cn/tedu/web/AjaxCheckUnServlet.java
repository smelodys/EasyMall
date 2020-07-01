package cn.tedu.web;

import cn.tedu.bean.User;
import cn.tedu.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Ajax 校验用户名
 *
 * @author wangyong
 * @date 2020/6/19 16:24
 */
@WebServlet(name = "AjaxCheckUnServlet", urlPatterns = "/AjaxCheckUnServlet")
public class AjaxCheckUnServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1..获取请求参数
        String username = request.getParameter("username");
        //2.调用Uservice
        UserService userService = new UserService();
        User user = userService.findUserByUsername(username);
        if (user != null) {
            //用户名存在
            response.getWriter().write("用户名已经存在");
        } else {
            //用户名不存在
            response.getWriter().write("恭喜您，可以注册！");
        }
    }
}
