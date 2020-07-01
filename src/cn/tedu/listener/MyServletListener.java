package cn.tedu.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
/**
 * 监听ServletContext 的创建，方便将${path} 放
 * 到域servletContext中
 * @author wangyong
 * @date 2020/6/23 9:30
 */
public class MyServletListener implements ServletContextListener {
    /**
     * 监听ServletContext 的创建
     * @param servletContextEvent 当前的事件，可以设置一些属性等 如：
     * httpSessionEvent.getSession().setAttribute("msg","success");
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //将当期web应用的路径存入ServletContext中
        ServletContext sc = servletContextEvent.getServletContext();
        sc.setAttribute("path",sc.getContextPath());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
