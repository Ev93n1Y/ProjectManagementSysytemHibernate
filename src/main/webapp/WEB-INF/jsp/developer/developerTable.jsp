<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:if test="${not empty developers}">
    <table style="text-align: center" border="1" width="20%">
        <thead>
            <tr>
                <td style="text-align: center"><b>id</b></td>
                <td style="text-align: center"><b>name</b></td>
                <td style="text-align: center"><b>age</b></td>
                <td style="text-align: center"><b>gender</b></td>
                <td style="text-align: center"><b>salary</b></td>
            </tr>
        </thead>
        <tbody>
            <c:forEach var = "developer" items="${developers}">
                <tr>
                    <td style="text-align: center">
                        <c:out value="${developer.id}"/>
                    </td>
                    <td style="text-align: center">
                        <c:out value="${developer.name}"/>
                    </td>
                    <td style="text-align: center">
                        <c:out value="${developer.age}"/>
                    </td>
                    <td style="text-align: center">
                        <c:out value="${developer.gender}"/>
                    </td>
                    <td style="text-align: center">
                        <c:out value="${developer.salary}"/>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table><br>
</c:if>
<c:if test="${not empty message}">
    <p style="color:red">${message}</p>
</c:if>