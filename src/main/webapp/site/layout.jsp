<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Online Entertainment</title>
    <link rel="stylesheet" href="<c:url value='/css/style.css'/>">
    </head>
<body>
    <header>
        <%-- Include menu (header) --%>
        <jsp:include page="/common/header.jsp" />
    </header>

    <main class="container">
        <%-- Đây là nơi nội dung động (trang chủ, chi tiết...) sẽ được nạp --%>
        <jsp:include page="${view}" />
    </main>

    <footer>
        <%-- Include footer --%>
        <jsp:include page="/common/footer.jsp" />
    </footer>
    
    </body>
</html>