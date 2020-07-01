package cn.tedu.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * t退出登录
 * @author wangyong
 * @date 2020/6/19 14:23
 */
@WebServlet(name = "loginOutServlet",urlPatterns = "/loginOutServlet")
public class LoginOutServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //杀死session
        request.getSession().invalidate();
        //重定向到首页
        response.sendRedirect(request.getContextPath()+"/index.jsp");
    }
}
