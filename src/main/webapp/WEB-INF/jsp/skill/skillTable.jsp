<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:if test="${not empty skills}">
    <table style="text-align: center" border="1" width="20%">
        <thead>
            <tr>
                <td style="text-align: center"><b>id</b></td>
                <td style="text-align: center"><b>department</b></td>
                <td style="text-align: center"><b>level</b></td>
            </tr>
        </thead>
            <tbody>
                <c:forEach var = "skill" items="${skills}">
                    <tr>
                        <td style="text-align: center">
                            <c:out value="${skill.id}"/>
                        </td>
                        <td style="text-align: center">
                            <c:out value="${skill.department}"/>
                        </td>
                        <td style="text-align: center">
                            <c:out value="${skill.level}"/>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
    </table><br>
</c:if>
<c:if test="${not empty message}">
    <p style="color:red">${message}</p>
</c:if>