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
        <h3> Company projects relation </h3><hr>
        <form action = "/companies">
            <label for = "name"> company id: </label>
            <input type = "number" id = "id" name = "id" min = "1" required/>
            <input type = "submit" name = "project" value = "find" style="width:130px"><br><br>
        </form>
        <table style="text-align: center" border="1" width="40%">
            <thead>
                <c:if test="${not empty message}">
                    <p style="color:red">${message}</p>
                </c:if>
                <c:if test="${not empty projects}">
                    <h5> Company id = ${company_id} projects: </h5>
                    <tr>
                        <td style="text-align: center"><b>id</b></td>
                        <td style="text-align: center"><b>name</b></td>
                        <td style="text-align: center"><b>company_id</b></td>
                        <td style="text-align: center"><b>customer_id</b></td>
                        <td style="text-align: center"><b>cost</b></td>
                        <td style="text-align: center"><b>creation_date</b></td>
                    </tr>
                </c:if>
            </thead>
            <tbody>
                <c:forEach var = "project" items="${projects}">
                    <tr>
                        <td style="text-align: center">
                            <c:out value="${project.id}"/>
                        </td>
                        <td style="text-align: center">
                            <c:out value="${project.name}"/>
                        </td>
                        <td style="text-align: center">
                            <c:out value="${project.company.id}"/>
                        </td>
                        <td style="text-align: center">
                            <c:out value="${project.customer.id}"/>
                        </td>
                        <td style="text-align: center">
                            <c:out value="${project.cost}"/>
                        </td>
                        <td style="text-align: center">
                            <c:out value="${project.creation_date}"/>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>