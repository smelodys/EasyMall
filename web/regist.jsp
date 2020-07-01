<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>欢迎注册EasyMall</title>
    <meta http-equiv="Content-type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" href="${path}/css/regist.css"/>
    <%--引入JQuery--%>
    <script src="${path}/js/jquery-1.4.2.js"></script>
    <script>
        /** 文档就绪事件*/
        $(function () {
            /*为验证码img 添加点击事件*/
            $("#img").click(function () {
                //设置src 的属性
                //在页面中如果元素的信息没有发生任何变化，则页面不会重写加载
                //$(this).attr("src","${path}/ValiImageServlet")
                //添加参数实现元素信息的改变，但是地址值信息不会改变,添加随机数或者时间值，即每次都会变化的值即可
                $(this).attr("src", "${path}/ValiImageServlet?a=" + Math.random())
            })

            //必须为文档就绪事件才行，username 作为例子，单独文档就绪，如下
            //密码校验
            $("input[name=password]").blur(function () {
                //清空提示
                $(this).nextAll("span").html("");
                if ($(this).val().trim() == "") {
                    $(this).nextAll("span").html("密码不能为空").css("color", "red")
                }
            })
            //确认密码校验
            $("input[name=password2]").blur(function () {
                //清空提示
                $(this).nextAll("span").html("");
                if ($(this).val().trim() == "") {
                    $(this).nextAll("span").html("确认密码不能为空").css("color", "red")
                }
                /*密码一致校验*/
                if ($(this).val() != $("input[name=password]").val()) {
                    $(this).nextAll("span").html("两次密码不一致").css("color", "red")
                }
            })
            //昵称校验
            $("input[name=nickname]").blur(function () {
                //清空提示
                $(this).nextAll("span").html("");
                if ($(this).val().trim() == "") {
                    $(this).nextAll("span").html("昵称不能为空").css("color", "red")
                }
            })
            //邮箱校验
            $("input[name=email]").blur(function () {
                //清空提示
                $(this).nextAll("span").html("");
                if ($(this).val().trim() == "") {
                    $(this).nextAll("span").html("邮箱不能为空").css("color", "red")
                }
                /*邮箱格式校验*/
                var reg = /^\w+@\w+(\.\w+)+$/
                if ($(this).val().trim() != "" && !reg.test($(this).val())) {
                    $(this).nextAll("span").html("邮箱格式不正确").css("color", "red");
                }
            })
        });
        /*失去焦点校验*/
        //用户名校验，单独提出，为了验证需要文档就绪才行
        $(document).ready(function () {
            $("input[name=username]").blur(function () {
                /*                alert("aaa")*/
                //清空提示
                $(this).nextAll("span").html("");
                if ($(this).val().trim() == "") {
                    $(this).nextAll("span").html("用户名不能为空").css("color", "red")
                    return
                }
                //ajax校验用户名是否存在，需要写在失去焦点事件中
                //如果用户名为空，则无需发起ajax,为了方便选中username的span
                // 为span 添加id 属性 username_msg
                //获取用户名
                var username = $(this).val()
                //发起ajax 请求
                $.post("${path}/AjaxCheckUnServlet",
                    {"username": username}, function (result) {
                        //result 参数：当请求成功后，服务器响应的数据
                        //将响应的信息存入username对应的span中
                        $("#username_msg").html(result).css("color", "red")
                    })
            })

        })

        /*处理form 提交事件，返回true或false，校验表单*/
        function checkForm() {
            //1.获取参数
            var username = $("input[name=username]").val();
            //alert("username:"+username);
            var password = $("input[name=password]").val();
            var password2 = $("input[name=password2]").val();
            var nickname = $("input[name=nickname]").val();
            var email = $("input[name=email]").val();
            var valistr = $("input[name=valistr]").val();

            //防止校验一次后不能继续校验
            var falg = true;
            //清空提示信息
            $("span").html("");
            //非空校验
            if (username.trim() == "") {
                //提示用户 在输入框后天就span 标签 因为验证码后面有图片，span 在下下个，所以统一用nextAll
                $("input[name=username]").nextAll("span").html("用户名不能为空").css("color", "red");
                falg = false;
            }
            if (password.trim() == "") {
                //提示用户 在输入框后天就span 标签 因为验证码后面有图片，span 在下下个，所以统一用nextAll
                $("input[name=password]").nextAll("span").html("密码不能为空").css("color", "red");
                falg = false;
            }
            if (password2.trim() == "") {
                //提示用户 在输入框后天就span 标签 因为验证码后面有图片，span 在下下个，所以统一用nextAll
                $("input[name=password2]").nextAll("span").html("密码确认不能为空").css("color", "red");
                falg = false;
            }
            if (nickname.trim() == "") {
                //提示用户 在输入框后天就span 标签 因为验证码后面有图片，span 在下下个，所以统一用nextAll
                $("input[name=nickname]").nextAll("span").html("昵称不能为空").css("color", "red");
                falg = false;
            }
            if (email.trim() == "") {
                //提示用户 在输入框后天就span 标签 因为验证码后面有图片，span 在下下个，所以统一用nextAll
                $("input[name=email]").nextAll("span").html("邮箱不能为空").css("color", "red");
                falg = false;
            }
            if (valistr.trim() == "") {
                //提示用户 在输入框后天就span 标签 因为验证码后面有图片，span 在下下个，所以统一用nextAll
                $("input[name=valistr]").nextAll("span").html("验证码不能为空").css("color", "red");
                falg = false;
            }
            //密码一致校验
            if (password != password2) {
                $("input[name=password2]").nextAll("span").html("两次密码不一致").css("color", "red");
                falg = false;
            }
            //邮箱格式校验
            var reg = /^\w+@\w+(\.\w+)+$/
            if (email.trim() != "" && !reg.test(email)) {
                $("input[name=email]").nextAll("span").html("邮箱格式不正确").css("color", "red");
                falg = false;
            }
            return falg;
        }
    </script>
</head>
<body>
<%--onsubmit="return true" 当提交时会触发onsubmit 事件，
如果return true  表示继续提交
如果return false 表示阻止提交
--%>
<form onsubmit="return checkForm()" action="${path}/RegistServlet" method="POST">
    <h1>欢迎注册EasyMall</h1>
    <table>
        <%--提示信息时后端返回的 服务器开销大，换为前端判断，打开判断校验验证码，错误则给提示--%>
        <tr>
            <td colspan="2" style="text-align: center; color: red">
                ${msg}
            </td>
        </tr>
        <tr>
            <td class="tds">用户名：</td>
            <td>
                <input type="text" name="username" placeholder="请输入用户名" onfocus="this.placeholder=''"
                       value="${param.username}"/>
                <span id="username_msg"></span>
            </td>
        </tr>
        <tr>
            <td class="tds">密码：</td>
            <td>
                <input type="password" name="password"
                       value="${param.password}"/>
                <span></span>
            </td>
        </tr>
        <tr>
            <td class="tds">确认密码：</td>
            <td>
                <input type="password" name="password2"
                       value="${param.password2}"/>
                <span></span>
            </td>
        </tr>
        <tr>
            <td class="tds">昵称：</td>
            <td>
                <input type="text" name="nickname"
                       value="${param.nickname}"/>
                <span></span>
            </td>
        </tr>
        <tr>
            <td class="tds">邮箱：</td>
            <td>
                <input type="text" name="email"
                       value="${param.email}"/>
                <span></span>
            </td>
        </tr>
        <tr>
            <td class="tds">验证码：</td>
            <td>
                <input type="text" name="valistr"/>
                <img id="img" src="${path}/ValiImageServlet" width="" height="" alt=""/>
                <span></span>
            </td>
        </tr>
        <tr>
            <td class="sub_td" colspan="2" class="tds">
                <input type="submit" value="注册用户"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>

