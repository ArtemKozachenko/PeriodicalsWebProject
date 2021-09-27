<%--
  Created by IntelliJ IDEA.
  User: Artem
  Date: 20.09.2021
  Time: 21:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Periodicals</title>
    <link href="${pageContext.request.contextPath}/static/css/bootstrap.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/css/bootstrap-theme.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/css/font-awesome.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/css/app.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/css/user-cabinet.css" rel="stylesheet">
</head>
<body>

<header>
    <jsp:include page="fragment/header.jsp"/>
</header>
<div class="container-fluid">
    <div class="row">
        <main class="col-xs-12 col-sm-8 col-md-9 col-lg-12">
            <jsp:include page="page/cabinet.jsp"/>
        </main>
    </div>
</div>
<footer class="footer">
    <jsp:include page="fragment/footer.jsp"/>
</footer>
<script src="${pageContext.request.contextPath}/static/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/static/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/static/js/app.js"></script>
</body>
</html>
