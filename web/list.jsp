<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>list 页面</h1>
Welcome：<shiro:principal></shiro:principal><br/>
<shiro:hasRole name="admin">
    <a href="admin.jsp">Admin page</a><br/>
</shiro:hasRole>

<shiro:hasRole name="user">
    <a href="user.jsp">User page</a><br/>
</shiro:hasRole>

<a href="/shiro/testShiroAnnotation">Test ShiroAnnotation</a><br/>

<a href="shiro/logout">Logout</a>
</body>
</html>
