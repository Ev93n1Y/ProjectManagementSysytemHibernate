<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:if test="${not empty projects}">
    <table style="text-align: center" border="1" width="40%">
        <thead>
            <tr>
                <td style="text-align: center"><b>id</b></td>
                <td style="text-align: center"><b>name</b></td>
                <td style="text-align: center"><b>company_id</b></td>
                <td style="text-align: center"><b>customer_id</b></td>
                <td style="text-align: center"><b>cost</b></td>
                <td style="text-align: center"><b>creation_date</b></td>
            </tr>
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
    </table><br>
</c:if>
<c:if test="${empty projects}">
    <p style="color:red">${message}</p>
</c:if>