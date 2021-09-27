<%--
  Created by IntelliJ IDEA.
  User: Artem
  Date: 16.09.2021
  Time: 14:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--<%@ taglib prefix="ishop" tagdir="/WEB-INF/tags"%>--%>

<div class="visible-xs-block xs-option-container">
    <a class="pull-right" data-toggle="collapse" href="#productCatalog">Product catalog <span class="caret"></span></a>
    <a data-toggle="collapse" href="#findProducts">Find magazines<span class="caret"></span></a>
</div>
<%-- Search form --%>
<form class="search" action="${pageContext.request.contextPath}/search">
    <div id="findProducts" class="panel panel-success collapse">
        <div class="panel-heading">Find magazines</div>
        <div class="panel-body">
            <div class="input-group">
                <input type="text" name="query" class="form-control" placeholder="Search query" value="${searchForm.query}">
                <span class="input-group-btn">
					<a id="goSearch" class="btn btn-default">Search</a>
				</span>
            </div>
            <%--<div class="more-options">
                <a data-toggle="collapse" href="#searchOptions">More filters <span class="caret"></span></a>
            </div>--%>
        </div>
        <div id="searchOptions" class="collapse">
            <%--<ishop:category-filter categories="${CATEGORY_LIST}" />--%>
                <%-- <ishop:producer-filter producers="${PRODUCER_LIST}" />--%>
                <div class="panel-heading">Category filters</div>
                <div class="panel-body categories">
                    <label><input type="checkbox" id="allCategories"> All</label>
                    <c:forEach var="category" items="${CATEGORY_LIST}">
                        <div class="form-group">
                            <div class="checkbox">
                                <label><input type="checkbox" name="category" value="${category.id}" class="search-option">
                                        ${category.categoryName} (${category.productCount})
                                </label>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <div class="panel-heading">Publisher filters</div>
                <div class="panel-body producers">
                    <label><input type="checkbox" id="allProducers"> All</label>
                    <c:forEach var="publisher" items="${PUBLISHER_LIST}">
                        <div class="form-group">
                            <div class="checkbox">
                                <label><input type="checkbox" name="publisher" value="${publisher.id }" class="search-option">
                                        ${publisher.publisherName }
                                </label>
                            </div>
                        </div>
                    </c:forEach>
                </div>
        </div>
    </div>
</form>
<%-- /Search form --%>
<%-- Categories --%>
<div id="productCatalog" class="panel panel-success collapse">
    <div class="panel-heading">Periodicals categories</div>
    <div class="list-group">
        <c:forEach var="category" items="${CATEGORY_LIST}">
            <a href="${pageContext.request.contextPath}/magazines${category.categoryUrl}" class="list-group-item ${selectedCategoryUrl == category.categoryUrl ? 'active' : '' }">
                <span class="badge">${category.productCount}</span> ${category.categoryName}
            </a>
        </c:forEach>
    </div>
</div>
<%-- /Categories --%>
