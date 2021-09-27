<%--
  Created by IntelliJ IDEA.
  User: Artem
  Date: 27.09.2021
  Time: 7:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag-files" tagdir="/WEB-INF/tags" %>

<div role="tabpanel" class="tab-pane fade ${p_control_panel != null ? 'active in' : ''}"
     id="user-profile__periodicals">
    <div class="buy-btn create-magazine-btn">
        <a class="btn btn-primary" href="cabinet/createMagazine">Create magazine</a>
    </div>
    <div class="table-responsive">
        <table class="table table-bordered shopping-cart__table">
            <thead>
            <tr>
                <th>Cover</th>
                <th>Item</th>
                <th>Price</th>
                <th>Category</th>
                <th>Id</th>
                <th>Edit</th>
                <th>Delete</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="magazine" items="${magazineList}">
                <tr class="js-shop__item">
                    <td>
                        <img class="img-responsive shopping-cart-item__img small"
                             src="${pageContext.request.contextPath}${magazine.imageLink}"
                             alt="${magazine.magazineName}"
                             onerror="this.src='${pageContext.request.contextPath}/media/no-image.jpg';">
                    </td>
                    <td>
                        <div class="shopping-cart-item__title">
                                ${magazine.magazineName}
                        </div>
                        <br>
                        <div class="shopping-cart-item__title">
                            Publisher: ${magazine.publisherName}
                        </div>
                        <div class="shopping-cart-item__desc">
                            Description: ${magazine.description}
                        </div>
                    </td>
                    <td>$ <span class="js-shop-item__price">${magazine.price}</span></td>
                    <td>
                        <div class="shopping-cart-item__title">
                                ${magazine.categoryName}
                        </div>
                    </td>
                    <td>
                        <div class="shopping-cart-item__title">
                                ${magazine.id}
                        </div>
                    </td>
                    <td>
                        <div class="shopping-cart-item__title">
                            <div class="buy-btn">
                                <form method="get"
                                      action="${pageContext.request.contextPath}/cabinet/editMagazine">
                                    <input type="hidden" name="id" value="${magazine.id}"/>
                                    <input type="hidden" name="imageLink" value="${magazine.imageLink}"/>
                                    <input type="hidden" name="page" value="${page}"/>
                                    <input type="submit" class="btn btn-primary" value="Edit"/>
                                </form>
                            </div>
                        </div>
                    </td>
                    <td>
                        <div class="shopping-cart-item__title">
                            <div class="buy-btn">
                                <form method="post"
                                      action="${pageContext.request.contextPath}/cabinet/deleteMagazine?page=${page}">
                                    <input type="hidden" name="id" value="${magazine.id}"/>
                                    <input type="hidden" name="imageLink" value="${magazine.imageLink}"/>
                                    <input type="submit" class="btn btn-primary" value="Delete"/>
                                </form>
                            </div>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div> <!-- / .table-responsive -->
    <tag-files:pagination noOfPages="${noOfPages}" page="${page}"/>
</div> <!-- / .tab-pane -->
