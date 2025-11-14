<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav class="header-nav">
    
    <a class="navbar-brand" href="<c:url value='/index'/>">
        ONLINE ENTERTAINMENT
    </a>

    <ul class="nav-links">
        
        <c:if test="${not empty sessionScope.user}">
            <li class="nav-item">
                <a href="<c:url value='/favorites'/>">MY FAVORITES</a>
            </li>
        </c:if>

        <li class="nav-item">
            <a href="#">MY ACCOUNT</a>
            
            <div class="dropdown-menu">
                
                <c:if test="${empty sessionScope.user}">
                    <a class="dropdown-item" href="<c:url value='/login'/>">Login</a>
                    <a class="dropdown-item" href="<c:url value='/register'/>">Registration</a>
                    <a class="dropdown-item" href="<c:url value='/forgot-password'/>">Forgot Password</a>
                </c:if>
                
                <c:if test="${not empty sessionScope.user}">
                    <a class="dropdown-item" href="<c:url value='/edit-profile'/>">Edit Profile</a>
                    <a class="dropdown-item" href="<c:url value='/change-password'/>">Change Password</a>
                    <a class="dropdown-item" href="<c:url value='/logout'/>">Logoff</a>
                </c:if>
                
            </div>
        </li>
    </ul>
</nav>