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
        <h3> Skill developers relation </h3><hr>
        <form action = "/skills">
            <label for = "name"> skill id: </label>
            <input type = "number" name = "skill_id" min = "1" required/>
            <input type = "submit" name = "developer" value = "find" style="width:130px"><br><br>
        </form>
        <c:import url="${contextPath}/WEB-INF/jsp/developer/developerTable.jsp"/>
        <c:if test="${skill_id > 0}">
            <form action = "/skills" method = "post">
                <label for = "name"> developer id: </label>
                <input type = "hidden" name = "skill_id" value = ${skill_id} />
                <input type = "number" name = "developer_id" min = "1" required style="margin-left: 13px;"/><br>
                <input type = "submit" name = "developer" value = "add" style="width:130px">
                <input type = "submit" name = "developer" value = "delete" style="width:130px"><br><br>
            </form>
        </c:if>
    </body>
</html>