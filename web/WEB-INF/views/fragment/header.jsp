<%--
  Created by IntelliJ IDEA.
  User: Artem
  Date: 16.09.2021
  Time: 14:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#ishopNav" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${pageContext.request.contextPath}/magazines">Periodicals</a>
        </div>
        <div class="collapse navbar-collapse" id="ishopNav">
            <ul id="currentShoppingCart" class="nav navbar-nav navbar-right hidden">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                        <i class="fa fa-shopping-cart" aria-hidden="true"></i> Shopping cart (<span class="total-count">0</span>)<span class="caret"></span>
                    </a>
                    <div class="dropdown-menu shopping-cart-desc">
                        Total count: <span class="total-count">0</span><br>
                        Total cost: <span class="total-cost">0</span><br>
                        <a href="/shopping-cart" class="btn btn-primary btn-block">View cart</a>
                    </div>
                </li>
            </ul>
            <c:choose>
                <c:when test="${loginedUser == null && loginPage == null && registrationPage == null}">
                    <jsp:include page="login-dropdown.jsp"/>
                </c:when>
                <c:when test="${loginPage != null || registrationPage != null}">

                </c:when>
                <c:otherwise>
                    <jsp:include page="user-menu.jsp"/>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</nav>
