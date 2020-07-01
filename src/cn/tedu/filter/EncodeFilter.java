package cn.tedu.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 实现拦截并处理中文乱码问题
 *
 * @author wangyong
 * @date 2020/6/22 11:26
 */
public class EncodeFilter implements Filter {
    /**
     * 声明变量存储编码信息
     */
    String encode = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //获取web.xml中的编码配置
        encode = filterConfig.getServletContext().getInitParameter("encode");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //1.处理响应乱码
        servletResponse.setContentType("text/html;charset=" + encode);
        //2.处理请求乱码
        //2.1 对于post请求，可以直接解决，但是get请求只能手动处理（tomcat8 则无需考虑GET乱码）
        //2.2 修改一个方法的功能
        //2.2.1.继承--通过重写来改造，但是无法改造实例
        //2.2.2.装饰者设计模式--可以改造一个实例的方法
        /*
         *实现装饰过程：
         * 1.写一个类，与被继承者继承相同的父类或者接口
         * 2.将被装饰对象通过构造方法保存起来
         * 3.对于不想改造的方法，调用原有对象的方法，想改造的方法直接改造
         */
        //放行时，应该放行装饰类
        System.out.println("doFilter:servletRequest:+++" + servletRequest.getParameter("username"));
        ServletRequest myReq = new MyServletRequest((HttpServletRequest) servletRequest);
        filterChain.doFilter(myReq, servletResponse);

    }

    @Override
    public void destroy() {

    }

    /**
     * request对象装饰类
     * 继承自HttpServletRequestWrapper，它本身就是request的装饰类，但是此装饰类
     * 没有任何方法的改动，只是默认实现了所有方法,所以不用再去一步步实现父类的方法
     */
    class MyServletRequest extends HttpServletRequestWrapper {
        //将被装饰者对象保存为成员变量
        private HttpServletRequest request;
        //定义一个开关，保证getParameterMap中的方法只执行一次，否则反复改造会就会反复乱码
        private boolean flag = true;

        //通过构造方法将被装饰的对象传进来，同时交给父类来调用默认的方法
        public MyServletRequest(HttpServletRequest request) {
            super(request);
            this.request = request;
        }
        //改造获取参数相关的方法

        @Override
        public Map<String, String[]> getParameterMap() {
            //所有的请求参数组成的map
            //此方法只能改造一次，否则重复改造出现乱码
            Map<String, String[]> map = request.getParameterMap();
            System.out.println("request.getParameter:" + request.getParameter("name"));
            if (flag) {
                for (Map.Entry<String, String[]> entry : map.entrySet()
                ) {
                    String[] values = entry.getValue();
                    for (int i = 0; i < values.length; i++) {
                        try {
                            values[i] = new String(values[i].getBytes("iso8859-1"), encode);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
                flag = false;
            }
            System.out.println(map.toString() + "#####" + map.get("username"));
            return map;
        }

        @Override
        public String[] getParameterValues(String name) {
            //上面的getParameterMap()方法以及处理好数据，直接调用通过name获取即可
            //注意：get("name")--不是get(name); name为字符串
            return getParameterMap().get(name);
        }

        @Override
        public String getParameter(String name) {
            //注意：get("name")--不是get(name); name为字符串
            String[] values = getParameterMap().get(name);
            return values == null ? null : values[0];
        }
    }
}
