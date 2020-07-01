package cn.tedu.web;

import cn.tedu.bean.User;
import cn.tedu.service.UserService;
import cn.tedu.utils.Md5Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * 注册Servlet
 * @author wangyong
 * @date 2020/6/22 18:00
 */
@WebServlet(name = "RegistServlet", urlPatterns = "/RegistServlet")
public class RegistServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取请求参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");
        String nickname = request.getParameter("nickname");
        String email = request.getParameter("email");
        String valistr = request.getParameter("valistr");

        //2.数据校验
        //非空校验
        if (username == null || "".equals(username.trim())) {
            //说明用户名为空，需要跳转回注册页面，并提示
            //将提示信息存入request域中
            request.setAttribute("msg", "用户名不能为空！");
            //转发到注册页面
            request.getRequestDispatcher("/regist.jsp")
                    .forward(request, response);
            return;
        }
        if (password == null || "".equals(password.trim())) {
            request.setAttribute("msg", "密码不能为空！");
            request.getRequestDispatcher("/regist.jsp").forward(request, response);
            return;
        }
        if (password2 == null || "".equals(password2.trim())) {
            request.setAttribute("msg", "确认密码不能为空！");
            request.getRequestDispatcher("/regist.jsp").forward(request, response);
            return;
        }
        if (nickname == null || "".equals(nickname.trim())) {
            request.setAttribute("msg", "昵称不能为空！");
            request.getRequestDispatcher("/regist.jsp").forward(request, response);
            return;
        }
        if (email == null || "".equals(email.trim())) {
            request.setAttribute("msg", "邮箱不能为空！");
            request.getRequestDispatcher("/regist.jsp").forward(request, response);
            return;
        }
        if (valistr == null || "".equals(valistr.trim())) {
            request.setAttribute("msg", "验证码不能为空！");
            request.getRequestDispatcher("/regist.jsp").forward(request, response);
            return;
        }
        //两次密码一致校验
        if (!password.equals(password2)) {
            request.setAttribute("msg", "两次密码不一致！");
            request.getRequestDispatcher("/regist.jsp").forward(request, response);
            return;
        }
        //邮箱格式校验
        //邮箱正则 zhangsan@tedu.cn
        if (!email.matches("^\\w+@\\w+(\\.\\w+)+$")) {
            request.setAttribute("msg", "邮箱格式不正确！");
            request.getRequestDispatcher("/regist.jsp").forward(request, response);
            return;
        }
        //验证码，使用session存取
        //从session中获取验证码
        String code = (String) request.getSession().getAttribute("code");
        //需要忽略大小写
        if (!code.equalsIgnoreCase(valistr)) {
            request.setAttribute("msg", "验证码不正确！");
            request.getRequestDispatcher("/regist.jsp").forward(request, response);
            return;
        }
        //5.调用service层
        UserService userService = new UserService();
        //用户名校验
        User user = userService.findUserByUsername(username);
        //不为null，则查询到数据
        if (user != null) {
            request.setAttribute("msg", "用户名已存在！");
            request.getRequestDispatcher("/regist.jsp").forward(request, response);
            return;
        }
        //用户不存在
        //利用User封装数据，并将用户信息进行注册
        User u = new User();
        u.setUsername(username);
        //将密码进行md5加密
        u.setPassword(Md5Utils.md5(password));
        u.setNickname(nickname);
        u.setEmail(email);
        userService.addUser(u);

        //定时刷新到首页,可以添加样式
        response.getWriter().write(
                "<font style='color:red;font-size:30px'>" +
                        "恭喜您注册成功，3秒后跳转到首页！</font>");
        response.setHeader("refresh", "3;url="
                + request.getContextPath() + "/index.jsp");
    }
}
