<%--
  Created by IntelliJ IDEA.
  User: Artem
  Date: 19.09.2021
  Time: 18:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="com.periodicals.constant.Constants" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="${pageContext.request.contextPath}/static/css/registration-form.css" rel="stylesheet">

<div class="signup-form">
    <form method="post">
        <h2>Register</h2>
        <p class="hint-text">Create your account.</p>
        <c:if test="${registrationErrorString != null}">
            <p style="color: #ff0000;">${registrationErrorString}</p>
        </c:if>
        <div class="form-group">
            <input type="text" class="form-control" name="login" placeholder="Login" value="${user.login}" required="required">
        </div>
        <div class="form-group">
            <input type="text" class="form-control" name="first_name" value="${user.firstName}" placeholder="First Name">
        </div>
        <div class="form-group">
            <input type="text" class="form-control" name="last_name" value="${user.lastName}" placeholder="Last Name">
        </div>
        <div class="form-group">
            <div class="bs-example">
                <p><strong>Gender:</strong></p>
                <div class="btn-group btn-group-sm" data-toggle="buttons">
                    <label class="btn btn-info ${user.gender == "male" ? 'active' : '' }">
                        <input type="radio" name="gender" value="male" ${user.gender == "male" ? 'checked' : '' }> Male
                    </label>
                    <label class="btn btn-info ${user.gender == "female" ? 'active' : '' }">
                        <input type="radio" name="gender" value="female" ${user.gender == "female" ? 'checked' : '' }> Female
                    </label>
                </div>
            </div>
        </div>
        <div class="form-group">
            <input type="email" class="form-control" name="email" value="${user.email}" placeholder="Email" required="required">
        </div>
        <div class="form-group">
            <input type="password" class="form-control" data-toggle="tooltip" data-placement="right" name="password"
                   value="${user.password}" placeholder="Password" required="required"
            title="<%= Constants.PASSWORD_VALIDATION_MESSAGE %>" >
        </div>
        <div class="form-group">
            <input type="password" class="form-control" name="confirm_password" placeholder="Confirm Password"
                   required="required">
        </div>
        <div class="form-group">
            <label class="form-check-label"><input type="checkbox" required="required"> I accept the <a href="#">Terms
                of Use</a> &amp; <a href="#">Privacy Policy</a></label>
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-success btn-lg btn-block">Create Account</button>
        </div>
    </form>
</div>
