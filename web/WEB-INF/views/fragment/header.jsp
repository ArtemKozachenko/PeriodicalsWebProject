<%--
  Created by IntelliJ IDEA.
  User: Artem
  Date: 16.09.2021
  Time: 14:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#ishopNav" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${pageContext.request.contextPath}/magazines"><fmt:message key="logo.name"/></a>

            <form class="navbar-brand">
                <select class="sort-block" name="java-navigator" onchange="top.location.href =
                this.options[this.selectedIndex].value;">
                    <option value="#"><fmt:message key="lang.choose"/></option>
                    <option value="?sessionLocale=en"><fmt:message key="lang.en"/></option>
                    <option value="?sessionLocale=ru"><fmt:message key="lang.ru"/></option>
                </select>
            </form>


        </div>
        <div class="collapse navbar-collapse" id="ishopNav" style="margin-right: 20px">
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
