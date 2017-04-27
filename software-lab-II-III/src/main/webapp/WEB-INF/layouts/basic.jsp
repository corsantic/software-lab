<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<tiles:importAttribute name="scripts" ignore="true"/>
<tiles:importAttribute name="stylesheets" ignore="true"/>
<!doctype html>
<head>
    <meta charset="utf-8"/>
    <title>vulnerability</title>
    <link rel="stylesheet" type="text/css" href="/assets/css/main.css"/>
    <c:forEach var="css" items="${stylesheets}">
        <link rel="stylesheet" type="text/css" href="${css}?v=${version}"/>
    </c:forEach>
</head>
<body>
<header>
    Kocaeli university
</header>

<tiles:insertAttribute name="body"/>

<footer>
    Footer
</footer>
<c:forEach var="js" items="${scripts}">
    <script src="${js}?v=${version}" type="text/javascript"></script>
</c:forEach>
</body>
</html>
