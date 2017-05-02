<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h2>Command Injection</h2>

<form:form method="POST" action="/commandInjection">
    <input type="text" name="host" placeholder="ping address"/>

    <button type="submit">Ping</button>
</form:form>
<p>${result}</p>