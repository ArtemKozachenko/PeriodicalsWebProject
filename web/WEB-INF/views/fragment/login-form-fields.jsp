<%--
  Created by IntelliJ IDEA.
  User: Artem
  Date: 22.09.2021
  Time: 20:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<div class="row">
    <div class="col-md-12">
        <c:if test="${errorString != null}">
            <p style="color: #ff0000;">${errorString}</p>
        </c:if>
        <form class="form" role="form" method="post" accept-charset="UTF-8"
              id="login-nav">
            <div class="form-group">
                <label class="sr-only" for="exampleInputEmail2">Email address</label>
                <input type="text" name="login" value="${user.login}" class="form-control" id="exampleInputEmail2"
                       placeholder="Login" >
            </div>
            <div class="form-group">
                <label class="sr-only" for="exampleInputPassword2">Password</label>
                <input type="password" name="password" value="${user.password}" class="form-control" id="exampleInputPassword2"
                       placeholder="Password" >
                <div class="help-block text-right"><a href="">Forget the password ?</a></div>
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-primary btn-block" name="loginAttempt" value="Y">Sign in</button>
            </div>
            <div class="checkbox">
                <label>
                    <input type="checkbox" name="rememberMe" value="Y"> Remember me
                </label>
            </div>
        </form>
    </div>
    <div class="bottom text-center">
        New here ? <a href="${pageContext.request.contextPath}/register"><b>Create account</b></a>
    </div>
</div>
