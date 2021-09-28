<%--
  Created by IntelliJ IDEA.
  User: Artem
  Date: 20.09.2021
  Time: 8:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link href="${pageContext.request.contextPath}/static/css/registration-form.css" rel="stylesheet">

<div class="signup-form">
    <form method="post">
        <h2>Sign in</h2>
        <c:choose>
            <c:when test="${errorString != null}">
                <p style="color: #ff0000;">${errorString}</p>
            </c:when>
            <c:otherwise>
                <p class="hint-text" style="color: #219c40;">Your account has been successfully created. Please, sign in.</p>
            </c:otherwise>
        </c:choose>
        <div class="form-group">
            <input type="text" class="form-control" name="login" placeholder="Login" value="${user.login}" >
        </div>
        <div class="form-group">
            <input type="password" class="form-control" name="password" value="${user.password}" placeholder="Password">
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-primary btn-block">Sign in</button>
        </div>
        <div class="checkbox">
            <label>
                <input type="checkbox" name="rememberMe" value="Y"> Remember me
            </label>
        </div>
    </form>
    <c:remove var="errorString" scope="session"/>
    <c:remove var="user" scope="session"/>
</div>
