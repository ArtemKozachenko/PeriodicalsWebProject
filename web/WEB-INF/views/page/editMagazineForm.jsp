<%--
  Created by IntelliJ IDEA.
  User: Artem
  Date: 25.09.2021
  Time: 22:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row edit-magazine-block">
    <form method="post" enctype="multipart/form-data">
        <div class="profile-user-info">
            <input type="hidden" name="id" value="${magazine.id}"/>
            <div ${url == "createMagazine" ? 'style="display: none"' : ''} class="profile-info-row">
                <div class="portfolio__item">
                    <div class="profile-info-name"> Id</div>
                    <div class="profile-info-value">
                        <span>${magazine.id}</span>
                    </div>
                </div>
            </div>
            <div class="profile-info-row">
                <div class="portfolio__item">
                    <div class="profile-info-name"> Magazine name</div>
                    <div class="profile-info-value">
                        <input type="text" name="magazineName" value="${magazine.magazineName}" required="required"/>
                    </div>
                </div>
            </div>
            <div class="profile-info-row">
                <div class="portfolio__item">
                    <div class="profile-info-name"> Price $</div>
                    <div class="profile-info-value">
                        <input type="number" step="0.01" min="0.01" max="100000.0" name="price" value="${magazine.price}" required="required"/>
                    </div>
                </div>
            </div>
            <div class="profile-info-row">
                <div class="portfolio__item">
                    <div class="profile-info-name"> Description</div>
                    <div class="profile-info-value">
                        <input type="text" name="description" value="${magazine.description}"/>
                    </div>
                </div>
            </div>
            <div class="profile-info-row">
                <div class="portfolio__item">
                    <div class="profile-info-name"> Image</div>
                    <div class="profile-info-value">
                        <input type="file" id="fileName" name="fileImage" accept=".png, .jpg, .jpeg" onchange="validateFileType()"/>
                        <script type="text/javascript">
                            function validateFileType(){
                                var fileName = document.getElementById("fileName").value;
                                var idxDot = fileName.lastIndexOf(".") + 1;
                                var extFile = fileName.substr(idxDot, fileName.length).toLowerCase();
                                if (extFile=="jpg" || extFile=="jpeg" || extFile=="png") {
                                    //TO DO
                                } else {
                                    document.getElementById("fileName").value = '';
                                    alert("Only jpg/jpeg and png files are allowed!");
                                }
                            }
                        </script>
                    </div>
                </div>
            </div>
            <div class="profile-info-row">
                <div class="portfolio__item">
                    <div class="profile-info-name"> Category</div>
                    <div class="profile-info-value">
                        <select name="categoryId" required="required">
                            <c:forEach var="category" items="${CATEGORY_LIST}">
                            <option value="${category.id}"
                                ${category.categoryName == magazine.categoryName ? 'selected' : ''}>${category.categoryName}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <div class="profile-info-row">
                <div class="portfolio__item">
                    <div class="profile-info-name"> Publisher</div>
                    <div class="profile-info-value">
                        <select name="publisherId" required="required">
                            <c:forEach var="publisher" items="${PUBLISHER_LIST}">
                                <option value="${publisher.id}"
                                    ${publisher.publisherName == magazine.publisherName ? 'selected' : ''}>${publisher.publisherName}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <div class="profile-info-row">
                <div class="profile-info-value">
                    <input type="submit" class="btn btn-primary" value="Submit"/>
                </div>
            </div>
        </div>
    </form>
</div>
<!-- / .row -->
