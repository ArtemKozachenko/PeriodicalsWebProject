<%--
  Created by IntelliJ IDEA.
  User: Artem
  Date: 28.09.2021
  Time: 7:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Periodicals</title>
    <link href="${pageContext.request.contextPath}/static/css/bootstrap.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/css/bootstrap-theme.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/css/font-awesome.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/css/app.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/css/404-page.css" rel="stylesheet">
</head>
<body>
<div class="page-404">
    <div class="outer">
        <div class="middle">
            <div class="inner">
                <!--BEGIN CONTENT-->
                <div class="inner-circle"><i class="fa fa-home"></i><span>404</span></div>
                <span class="inner-status">Oops! You're lost</span>
                <span class="inner-detail">
                    We can not find the page you're looking for.
                    <a href="${pageContext.request.contextPath}/magazines" class="btn btn-info mtl"><i class="fa fa-home"></i>&nbsp;
                        Return home
                    </a>
                </span>
            </div>
        </div>
    </div>
</div>
</body>
</html>
