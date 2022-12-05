<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
    <body>
        <c:import url="${contextPath}/WEB-INF/jsp/skill/findSkillForm.jsp"/>
        <c:import url="${contextPath}/WEB-INF/jsp/skill/skillTable.jsp"/>
    </body>
</html>