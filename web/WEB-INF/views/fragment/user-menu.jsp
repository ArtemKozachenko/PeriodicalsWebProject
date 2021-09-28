<%--
  Created by IntelliJ IDEA.
  User: Artem
  Date: 19.09.2021
  Time: 13:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>

<ul class="nav navbar-nav navbar-right">
    <li>
        <p class="navbar-text"><fmt:message key="userMenu.walletBalance"/>: $ ${loginedUser.wallet.amountOfMoney}</p>
    </li>
    <li class="dropdown navbar-right user_menu">
        <h4 class="dropdown-toggle" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
            <fmt:message key="userMenu.welcoming"/>,
            <c:choose>
                <c:when test="${loginedUser.firstName != null}">
                    <span>${loginedUser.firstName}</span>
                </c:when>
                <c:otherwise>
                    <span>${loginedUser.login}</span>
                </c:otherwise>
            </c:choose>
            <span class="caret"></span>
        </h4>
        <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
            <li><a href="${pageContext.request.contextPath}/cabinet"><fmt:message key="userMenu.account"/></a></li>
            <li role="separator" class="divider"></li>
            <%--<li><a href="${pageContext.request.contextPath}/logout">Sign out</a></li>--%>
            <form class="form" role="form" method="post" action="${pageContext.request.contextPath}/logout" accept-charset="UTF-8">
                <li><button type="submit" class="btn btn-danger btn-block"><fmt:message key="userMenu.signOut"/></button></li>
            </form>
        </ul>
    </li>
</ul>
