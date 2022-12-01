<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:if test="${not empty companies}">
    <table style="text-align: center" border="1" width="20%">
        <thead>
                <tr>
                    <td style="text-align: center"><b>id</b></td>
                    <td style="text-align: center"><b>name</b></td><b>
                    <td style="text-align: center"><b>location</b></td><b>
                </tr>
        </thead>
            <tbody>
                <c:forEach var = "company" items="${companies}">
                    <tr>
                        <td style="text-align: center;">
                            <c:out value="${company.id}"/>
                        </td>
                        <td style="text-align: center">
                            <c:out value="${company.name}"/>
                        </td>
                        <td style="text-align: center">
                            <c:out value="${company.location}"/>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
    </table><br>
</c:if>
<c:if test="${not empty message}">
    <p style="color:red">${message}</p>
</c:if>