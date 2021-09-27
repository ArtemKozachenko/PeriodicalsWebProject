<%@ tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="firstName" scope="page" value="${loginedUser.firstName}"/>
<c:set var="lastName" scope="page" value="${loginedUser.lastName}"/>
<c:set var="login" scope="page" value="${loginedUser.login}"/>
<c:set var="status" scope="page" value="${loginedUser.status}"/>
<c:set var="email" scope="page" value="${loginedUser.email}"/>
<c:set var="gender" scope="page" value="${loginedUser.gender}"/>
<c:set var="role" scope="page" value="${loginedUser.roleName}"/>
<c:set var="creationDate" scope="page" value="${loginedUser.creationDateAsString}"/>
<c:set var="eWallet" scope="page" value="${loginedUser.wallet.amountOfMoney}"/>
<c:set var="subscriptions" scope="page" value="${loginedUser.subscriptions}"/>