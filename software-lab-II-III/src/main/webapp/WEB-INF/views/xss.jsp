<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<h2>Burasi bagcilar welcome to cehennem</h2>


<div>
    <form:form method="POST" action="/xss">
        <input name="comment" placeholder="your comment..."/>

        <button type="submit">Post</button>
    </form:form>
</div>


<h2>Yorumlar</h2>

<ul>
    <c:forEach var="comment" items="${comments}">
        <li><c:out escapeXml="false"  value="${comment.message}"></c:out></li>
    </c:forEach>

</ul>