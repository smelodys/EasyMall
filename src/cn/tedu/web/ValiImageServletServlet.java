package cn.tedu.web;

import cn.tedu.utils.VerifyCode;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * 验证码
 * @author wangyong
 * @date 2020/6/17 9:12
 */
@WebServlet(name = "ValiImageServletServlet",urlPatterns = "/ValiImageServlet")
public class ValiImageServletServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //创建VerifyCode 类对象
        VerifyCode vc = new VerifyCode();
        //绘图
        vc.drawImage(response.getOutputStream());
        //将验证码存入Session中
        String code = vc.getCode();
        request.getSession().setAttribute("code",code);

    }
}
