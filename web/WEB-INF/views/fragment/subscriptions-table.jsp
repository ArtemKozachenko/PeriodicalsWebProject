<%--
  Created by IntelliJ IDEA.
  User: Artem
  Date: 27.09.2021
  Time: 9:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="subscriptions" scope="page" value="${loginedUser.subscriptions}"/>

<table class="table table-bordered shopping-cart__table">
    <thead>
    <tr>
        <th>Cover</th>
        <th>Item</th>
        <th>Price per month</th>
        <th>Status</th>
        <th>Subscription date</th>
        <th>Expiry date</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="subscription" items="${subscriptions}" begin="${begin}" end="${end}">
        <tr class="js-shop__item">
            <td>
                <img class="img-responsive shopping-cart-item__img small"
                     src="${pageContext.request.contextPath}${subscription.magazine.imageLink}"
                     alt="${subscription.magazine.magazineName}"
                     onerror="this.src='${pageContext.request.contextPath}/img/no-image.jpg';">
            </td>
            <td>
                <div class="shopping-cart-item__title">
                        ${subscription.magazine.magazineName}
                </div>
                <br>
                <div class="shopping-cart-item__title">
                    Publisher: ${subscription.magazine.publisherName}
                </div>
                <div class="shopping-cart-item__desc">
                    Description: ${subscription.magazine.description}
                </div>
            </td>
            <td>$ <span class="js-shop-item__price">${subscription.magazine.price}</span></td>
            <td>
                <p class="shopping-cart-item__title">
                   <span style="color: ${subscription.status == "active" ? 'darkseagreen' : 'red' }">
                       <c:choose>
                           <c:when test="${subscription.status == 'active'}">
                               <i class="fa fa-check"></i>
                           </c:when>
                           <c:otherwise>
                               <i class="fa fa-times"></i>
                           </c:otherwise>
                       </c:choose>
                           ${subscription.status}
                   </span>
                </p>
            </td>
            <td>
                <div class="shopping-cart-item__title">
                        ${subscription.startDateAsString}
                </div>
            </td>
            <td>
                <div class="shopping-cart-item__title">
                        ${subscription.endDateAsString}
                </div>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
