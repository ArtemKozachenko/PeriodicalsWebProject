<%--
  Created by IntelliJ IDEA.
  User: Artem
  Date: 27.09.2021
  Time: 8:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag-files" tagdir="/WEB-INF/tags" %>

<div role="tabpanel" class="tab-pane fade ${p_users_panel != null ? 'active in' : ''}"
     id="user-profile__users-list">
    <div class="table-responsive">
        <table class="table table-bordered shopping-cart__table">
            <thead>
            <tr>
                <th>Personal info</th>
                <th>Role</th>
                <th>Creation date</th>
                <th>Status</th>
                <th>Block</th>
                <th>Unblock</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="user" items="${usersList}">
                <c:if test="${loginedUser.id != user.id}">
                    <tr class="js-shop__item">
                        <td>
                            <div class="shopping-cart-item__title">
                                ${user.firstName} ${user.lastName}
                            </div>
                            <div class="shopping-cart-item__title">
                                Login: ${user.login}
                            </div>
                            <div class="shopping-cart-item__title">
                                Email: ${user.email}
                            </div>
                        </td>
                        <td>
                            <div class="shopping-cart-item__title">
                                    ${user.role.name}
                            </div>
                        </td>
                        <td>
                            <div class="shopping-cart-item__title">
                                    ${user.creationDate}
                            </div>
                        </td>
                        <td>
                            <div class="shopping-cart-item__title">
                                <p class="shopping-cart-item__title">
                                    <span style="color: ${user.status == "active" ? 'darkseagreen' : 'red' }">
                                        <c:choose>
                                            <c:when test="${user.status == 'active'}">
                                                <i class="fa fa-check"></i>
                                            </c:when>
                                            <c:otherwise>
                                                <i class="fa fa-lock"></i>
                                            </c:otherwise>
                                        </c:choose>
                                            ${user.status}
                                    </span>
                                </p>
                            </div>
                        </td>
                        <td>
                            <div class="shopping-cart-item__title">
                                <div class="buy-btn">
                                    <form method="post"
                                          action="${pageContext.request.contextPath}/cabinet/changeUserStatus?page=${pageUsr}">
                                        <input type="hidden" name="id" value="${user.id}"/>
                                        <input type="hidden" name="login" value="${user.login}"/>
                                        <input type="hidden" name="statusValue" value="block"/>
                                        <input type="submit" class="btn btn-primary" ${user.status != "active" ? 'disabled="disabled"' : ''} value="Block"/>
                                    </form>
                                </div>
                            </div>
                        </td>
                        <td>
                            <div class="shopping-cart-item__title">
                                <div class="buy-btn">
                                    <form method="post"
                                          action="${pageContext.request.contextPath}/cabinet/changeUserStatus?page=${pageUsr}">
                                        <input type="hidden" name="id" value="${user.id}"/>
                                        <input type="hidden" name="login" value="${user.login}"/>
                                        <input type="hidden" name="statusValue" value="unblock"/>
                                        <input type="submit" class="btn btn-primary" ${user.status == "active" ? 'disabled="disabled"' : ''} value="Unblock"/>
                                    </form>
                                </div>
                            </div>
                        </td>
                    </tr>
                </c:if>
            </c:forEach>
            </tbody>
        </table>
    </div> <!-- / .table-responsive -->
    <tag-files:pagination noOfPages="${noOfUsrPages}" page="${pageUsr}"/>
</div> <!-- / .tab-pane -->
