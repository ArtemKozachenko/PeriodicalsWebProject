<%--
  Created by IntelliJ IDEA.
  User: Artem
  Date: 25.09.2021
  Time: 21:48
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

<div class="user-profile__tabs">
    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="${subscribe != null || p_control_panel != null || p_users_panel != null ? '' : 'active'}">
            <a href="#user-profile__portfolio" aria-controls="user-profile__portfolio" role="tab" data-toggle="tab"
               aria-expanded="${subscribe != null || p_control_panel != null || p_users_panel != null ? 'false' : 'true'}">My Profile</a>
        </li>
        <li role="presentation" class="${subscribe != null ? 'active' : ''}">
            <a href="#user-profile__shopping-cart" aria-controls="user-profile__shopping-cart" role="tab"
               data-toggle="tab"
               aria-expanded="${subscribe != null ? 'true' : 'false'}">My Subscriptions</a>
        </li>
        <c:if test="${role == 'admin'}">
            <jsp:include page="../fragment/admin/panel-tabs.jsp"/>
        </c:if>
    </ul>

    <!-- Tab panes -->
    <div class="tab-content">
        <div role="tabpanel" class="tab-pane fade ${subscribe != null || p_control_panel != null || p_users_panel != null ? '' : 'active in'}"
             id="user-profile__portfolio">
            <div class="row">
                <div class="profile-user-info">
                    <div class="profile-info-row">
                        <div class="portfolio__item">
                            <div class="profile-info-name"> User name</div>
                            <div class="profile-info-value">
                                <span>${firstName}</span>
                            </div>
                        </div>
                    </div>
                    <div class="profile-info-row">
                        <div class="portfolio__item">
                            <div class="profile-info-name"> Last name</div>
                            <div class="profile-info-value">
                                <span>${lastName}</span>
                            </div>
                        </div>
                    </div>
                    <div class="profile-info-row">
                        <div class="portfolio__item">
                            <div class="profile-info-name"> Login</div>
                            <div class="profile-info-value">
                                <span>${login}</span>
                            </div>
                        </div>
                    </div>
                    <div class="profile-info-row">
                        <div class="portfolio__item">
                            <div class="profile-info-name">
                                <i class="fa fa-envelope-o"></i>
                                Email
                            </div>
                            <div class="profile-info-value">
                                <span>${email}</span>
                            </div>
                        </div>
                    </div>
                    <div class="profile-info-row">
                        <div class="portfolio__item">
                            <div class="profile-info-name"> Gender</div>
                            <div class="profile-info-value">
                                <span>${gender}</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div> <!-- / .row -->
        </div> <!-- / .tab-pane -->
        <div role="tabpanel" class="tab-pane fade ${subscribe != null ? 'active in' : ''}"
             id="user-profile__shopping-cart">
            <div class="table-responsive">
                <c:choose>
                    <c:when test="${empty subscriptions}">
                        <p>You don't have any subscriptions yet</p>
                    </c:when>
                    <c:otherwise>
                        <jsp:include page="../fragment/subscriptions-table.jsp"/>
                    </c:otherwise>
                </c:choose>

            </div> <!-- / .table-responsive -->
            <c:if test="${not empty subscriptions}">
                <tag-files:pagination noOfPages="${noOfSubPages}" page="${pageSub}"/>
            </c:if>
        </div> <!-- / .tab-pane -->

        <c:if test="${role == 'admin'}">
            <jsp:include page="../fragment/admin/periodicals-control-panel.jsp"/>
            <jsp:include page="../fragment/admin/users-control-panel.jsp"/>
        </c:if>

    </div> <!-- / .tab-content -->
</div>
