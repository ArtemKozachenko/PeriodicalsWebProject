<%--
  Created by IntelliJ IDEA.
  User: Artem
  Date: 20.09.2021
  Time: 21:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag-files" tagdir="/WEB-INF/tags" %>

<c:set var="firstName" scope="page" value="${loginedUser.firstName}"/>
<c:set var="lastName" scope="page" value="${loginedUser.lastName}"/>
<c:set var="login" scope="page" value="${loginedUser.login}"/>
<c:set var="status" scope="page" value="${loginedUser.status}"/>
<c:set var="email" scope="page" value="${loginedUser.email}"/>
<c:set var="gender" scope="page" value="${loginedUser.gender}"/>
<c:set var="role" scope="page" value="${loginedUser.role.name}"/>
<c:set var="creationDate" scope="page" value="${loginedUser.creationDateAsString}"/>
<c:set var="eWallet" scope="page" value="${loginedUser.wallet.amountOfMoney}"/>
<c:set var="subscriptions" scope="page" value="${loginedUser.subscriptions}"/>

<div class="container">
    <div class="row">
        <div class="col-sm-3">
            <div class="user-profile__avatar shadow-effect text-center">
                <img class="img-responsive center-block" src="${pageContext.request.contextPath}/img/user-avatar.png" alt="...">
                ${firstName} ${lastName}
            </div>

            <div class="panel panel-default">
                <div class="panel-heading">
                    User Menu
                </div>
                <div class="panel-body">
                    <ul>
                        <form method="post">
                            <li><button type="submit" class="fa fa-sign-out" name="signOut" value="Y">Sign out</button></li>
                        </form>
                    </ul>
                </div>
            </div>

            <form method="POST" action="${pageContext.request.contextPath}/cabinet">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        Top up wallet balance
                    </div>
                    <div class="panel-body">
                        <ul>
                            <li><i class="fa fa-credit-card"></i> Wallet Balance: $ ${eWallet}</li>
                            <li><input type="number" name="money" step="0.01" min="0.01" max="100000.0" value="0" required="required"/></li>
                            <li><input type="submit" value="Submit" ${status != 'active' ? 'disabled="disabled"' : ''}/></li>
                            <c:if test="${status != 'active'}">
                                You cannot top up your wallet balance being banned
                            </c:if>
                        </ul>
                    </div>
                </div>
            </form>
        </div>

        <!-- Modal -->
        <div class="modal ${message == null ? 'fade' : 'show'}" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog modal-form" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"
                                onclick = "$('.modal').removeClass('show').addClass('fade');"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel">Information window</h4>
                    </div>
                    <div class="modal-body">
                        <span style="color: ${messageType == 'success' ? '#1c9837' : '#ff0000'};">
                            <i class="${messageType == 'success' ? 'fa fa-check' : 'fa fa-remove'}"></i> ${message}
                        </span>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal"
                                onclick = "$('.modal').removeClass('show').addClass('fade');">Close</button>

                    </div>
                </div>
            </div>
            <c:remove var="message" scope="session"/>
            <c:remove var="messageType" scope="session"/>
        </div>
        <!-- / Modal -->
        <div class="col-sm-9">
            <div class="row">
                <div class="col-sm-6">

                    <!-- User name -->
                    <h3 class="user-profile__title">
                        <c:choose>
                            <c:when test="${firstName != null || lastName != null}">
                                <span>${firstName} ${lastName}</span>
                            </c:when>
                            <c:otherwise>
                                <span>${login}</span>
                            </c:otherwise>
                        </c:choose>
                    </h3>

                    <!-- User description -->
                    <p class="user-profile__desc">
                        Account status: <span style="color: ${status == "active" ? 'darkseagreen' : 'red' }">
                        <c:choose>
                            <c:when test="${status == 'active'}">
                                <i class="fa fa-check"></i>
                            </c:when>
                            <c:otherwise>
                                <i class="fa fa-lock"></i>
                            </c:otherwise>
                        </c:choose>
                        <b>${status}</b></span>
                    </p>
                    <c:if test="${role == 'admin' }">
                        <p class="user-profile__desc">
                            Role: <i class="fa fa-cog"></i> <span style="color: cornflowerblue"><b>${role}</b></span>
                        </p>
                    </c:if>
                </div>
                <div class="col-sm-6">

                    <!-- Profile info -->
                    <ul class="user-profile__info">
                        <li>
                            <i class="fa fa-calendar-o"></i> Account created: ${creationDate}
                        </li>
                    </ul>
                </div>
                <div class="col-sm-12">
                    <jsp:include page="${currentPage}"/>
                </div>
            </div> <!-- / .row -->
        </div>
        <c:remove var="subscribe" scope="session"/>
        <c:remove var="p_control_panel" scope="session"/>
    </div> <!-- / .row -->
</div>
