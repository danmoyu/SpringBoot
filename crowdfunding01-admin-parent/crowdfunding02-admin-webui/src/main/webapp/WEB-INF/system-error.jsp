<%--
  Created by IntelliJ IDEA.
  User: 10671
  Date: 2021/5/4
  Time: 16:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">
<head>
    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/"/>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="keys" content="">
    <meta name="author" content="">
    <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/font-awesome.min.css">
    <link rel="stylesheet" href="css/login.css">
    <script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="layer/layer.js"></script>
    <script type="text/javascript">
        $(function () {

            $("button").click(function () {
                //相当于浏览器的后退按钮
                window.history.back();
            });

            // $(function () {
            //
            //     $("button").click(function () {
            //
            //         layer.msg("弹窗出现了吗");
            //     });
            //
            // })
        });
    </script>
    <style>
    </style>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <div><a class="navbar-brand" href="index.html" style="font-size:32px;">尚筹网-创意产品众筹平台</a></div>
        </div>
    </div>
</nav>

<div class="container">
    <h2  class="form-signin-heading" style="text-align: center;">
        <i class="glyphicon glyphicon-log-in"></i> 众筹网系统提示
    </h2><br/>

    <%--
        requestScopee:对应的是存放request域数据的map
        requestScope.exception:相当于request.getAttribute("exception")
        requestScope.exception.message:相当于exception.getMessage()
    --%>
    <h3 style="text-align: center;">${requestScope.exception.message}</h3>

    <button style="width: 150px;margin: 50px auto 0 auto;" class="btn btn-lg btn-success btn-block">点我返回上一步</button>
</div>
</body>
</html>
