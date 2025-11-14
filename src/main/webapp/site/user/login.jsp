<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h3>LOGIN</h3>

<c:if test="${not empty message}">
    <div class="alert alert-danger">${message}</div>
</c:if>

<form action="<c:url value='/login'/>" method="post">
    <div class="form-group">
        <label>Username?</label>
        <input type="text" class="form-control" name="username" value="${userId}">
    </div>
    <div class="form-group">
        <label>Password?</label>
        <input type="password" class="form-control" name="password">
    </div>
    <div class="form-check">
        <input type="checkbox" class="form-check-input" name="remember" id="rememberCheck">
        <label class="form-check-label" for="rememberCheck">Remember me?</label>
    </div>
    <button type="submit" class="btn btn-primary">Login</button>
</form>