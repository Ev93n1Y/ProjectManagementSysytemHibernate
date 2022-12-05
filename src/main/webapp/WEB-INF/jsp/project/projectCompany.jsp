<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    </head>
    <body>
        <c:import url="${contextPath}/WEB-INF/jsp/navigation.jsp"/>
        <h3> Project company relation </h3><hr>
        <form action = "/projects">
            <label for = "name"> project id: </label>
            <input type = "number" name = "project_id" min = "1" required/>
            <input type = "submit" name = "company" value = "find" style="width:130px"><br><br>
        </form>
        <c:import url="${contextPath}/WEB-INF/jsp/company/companyTable.jsp"/>
        <c:if test="${project_id > 0}">
            <form action = "/projects" method = "post">
                <label for = "name"> company id: </label>
                <input type = "hidden" name = "project_id" value = ${project_id} />
                <input type = "number" name = "company_id" min = "1" required style="margin-left: 13px;"/>
                <input type = "submit" name = "company" value = "add/change" style="width:130px"><br><br>
            </form>
        </c:if>
    </body>
</html>