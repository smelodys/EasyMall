<%@ page import="java.net.URLDecoder" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" href="${path}/css/login.css"/>
    <title>EasyMall欢迎您登陆</title>
    <script>
        /**
         * 利用js 对cookie 中的用户名进行解码
         */
        //文档就绪事件
        onload = function () {
            var inp = document.getElementsByName("username")[0]
            inp.value = decodeURI(inp.value)
        }
    </script>
</head>
<body>
<h1>欢迎登陆EasyMall</h1>
<form action="${path}/LoginServlet" method="POST">
    <table>
        <tr>
            <td colspan="2" style="color: red;text-align: center;">
                ${msg}
            </td>
        </tr>
        <tr>
            <%--获取cookie 用EL 表达式，就不用手动写代码获取,但是需要解码，上面script 进行解码操作--%>
            <td class="tdx">用户名：</td>
            <td><input type="text" name="username" value="${cookie.remname.value}"/></td>
        </tr>
        <tr>
            <td class="tdx">密&nbsp;&nbsp; 码：</td>
            <td><input type="password" name="password"/></td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="checkbox" name="remname" value="true"
                ${empty cookie.remname? "":"checked='checked'"}/>记住用户名
            </td>
        </tr>
        <tr>
            <td colspan="2" style="text-align:center">
                <input type="submit" value="登 陆"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>

