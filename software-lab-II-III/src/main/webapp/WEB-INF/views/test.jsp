<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<html>
<head>
        <title>Spring MVC Form Handling</title>
</head>

<body>
<h2>User Information</h2>
<form:form method = "POST" modelAttribute="user" action = "/login">
        <table>
                <tr>
                        <label>Name</label>
                        <td><form:input path ="username" /></td>
                        <label>-Password</label>
                        <td><form:input path ="password" /></td>
                </tr>


                <tr>
                        <td colspan = "2">
                                <input type = "submit" value = "Submit"/>
                        </td>

                </tr>
        </table>
</form:form>
</body>

</html>