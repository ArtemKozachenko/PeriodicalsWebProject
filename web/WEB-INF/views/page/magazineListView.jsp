<%--
  Created by IntelliJ IDEA.
  User: Artem
  Date: 16.09.2021
  Time: 14:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tag-files" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>

<c:set var="searchQuery" scope="page" value="${searchQuery}"/>
<div id="productList" data-page-count="${pageCount}" data-page-number="1">
    <form>
        <select class="sort-block" name="java-navigator" onchange="top.location.href =
  this.options[this.selectedIndex].value;">
            <option value="#"><fmt:message key="sorting.sortBy"/> ${sortingName == null ? 'Name: A-Z' : sortingName}</option>
            <option value="?sort=magazine_name-asc${searchQuery}"><fmt:message key="sorting.nameAsc"/></option>
            <option value="?sort=magazine_name-desc${searchQuery}"><fmt:message key="sorting.nameDesc"/></option>
            <option value="?sort=price-asc${searchQuery}"><fmt:message key="sorting.priceAsc"/></option>
            <option value="?sort=price-desc${searchQuery}"><fmt:message key="sorting.priceDesc"/></option>
        </select>
    </form>
    <div class="row">
        <c:forEach var="magazine" items="${magazineList}">
            <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3 col-xlg-2">
                <div id="product${magazine.id}" class="panel panel-default product">
                    <div class="panel-body">
                        <div class="thumbnail">
                            <img src="${pageContext.request.contextPath}${magazine.imageLink}"
                                 onerror="this.src='${pageContext.request.contextPath}/img/no-image.jpg';" alt="${magazine.magazineName}">
                            <div class="desc">
                                <div class="cell">
                                    <p>
                                        <span class="title"><fmt:message key="magazineItem.details"/></span> ${magazine.description}
                                    </p>
                                </div>
                            </div>
                        </div>
                        <h4 class="name">${magazine.magazineName}</h4>
                        <div class="code"><fmt:message key="magazineItem.code"/>: ${magazine.id}</div>
                        <div class="price">$ ${magazine.price}</div>
                        <c:if test="${loginedUser != null}">
                            <c:set var="subscriptions" scope="page" value="${loginedUser.subscriptions}"/>
                            <c:forEach var="subscription" items="${subscriptions}">
                                <c:if test="${magazine.id == subscription.magazine.id && subscription.status == 'active'}">
                                    <c:set var="subscribed" scope="page" value="${magazine.id}"/>
                                </c:if>
                            </c:forEach>
                        </c:if>
                        <div class="pull-right buy-btn">
                            <form method="post" action="${pageContext.request.contextPath}/subscribe">
                                <input type="hidden" name="id" value="${magazine.id}"/>
                                <input ${loginedUser == null ? 'data-toggle="modal" data-target="#myModal" type="button"' : 'type="submit"'}
                                        ${loginedUser != null && loginedUser.status != 'active' ? 'disabled="disabled" title="You cannot subscribe on magazine being banned"' : ''}
                                    ${subscribed == magazine.id ? 'class="btn btn-success" value="Subscribed" disabled="disabled"' : 'class="btn btn-primary" value="Subscribe"'}
                                ${loginedUser != null && magazine.price > loginedUser.wallet.amountOfMoney && subscribed != magazine.id ? 'disabled="disabled" title="Insufficient wallet balance"' : ''}/>
                            </form>
                        </div>

                        <div class="list-group">
                            <span class="list-group-item"><small><fmt:message key="magazineItem.category"/>: </small><span class="category">${magazine.categoryName}</span></span>
                            <span class="list-group-item"><small><fmt:message key="magazineItem.publisher"/>: </small><span class="producer">${magazine.publisherName}</span></span>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

    <!-- Modal -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog modal-form" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel"><fmt:message key="loginDropDown.signIn"/></h4>
                </div>
                <div class="modal-body">
                    <div style="text-align: center"><span style="color: #ff0000;"><fmt:message key="loginDropDown.warningLabel"/></span></div>
                    <jsp:include page="../fragment/login-form-fields.jsp"/>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="loginDropDown.closeButton"/></button>

                </div>
            </div>
        </div>
    </div>
    <!-- / Modal -->

    <tag-files:pagination noOfPages="${noOfPages}" page="${page}"/>

</div>

