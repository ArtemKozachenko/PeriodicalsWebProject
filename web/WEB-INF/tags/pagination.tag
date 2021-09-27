<%@ tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ attribute name="noOfPages" required="true" type="java.lang.Integer" rtexprvalue="true" %>
<%@ attribute name="page" required="true" type="java.lang.Integer" rtexprvalue="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="text-center hidden-print">
    <nav>
        <ul class="pagination">
            <c:if test="${page != 1}">
                <li><a class="next-prev-btn" href="?page=${page - 1}" aria-label="Previous">Previous</a></li>
            </c:if>
            <c:forEach begin="1" end="${noOfPages}" var="i">
                <c:choose>
                    <c:when test="${page == i}">
                        <li class="active"><a>${i} <span class="sr-only">(current)</span></a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="?page=${i}">${i} </a></li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <c:if test="${page < noOfPages}">
                <li>
                    <a class="next-prev-btn" href="?page=${page + 1}" aria-label="Next">
                        <span aria-hidden="true">Next</span>
                    </a>
                </li>
            </c:if>
        </ul>
    </nav>
</div>