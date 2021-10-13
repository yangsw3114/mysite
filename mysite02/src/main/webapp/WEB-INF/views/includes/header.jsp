<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page import="com.douzone.mysite.vo.UserVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
		<div id="header">
			<h1>MySite</h1>
			<ul>
				<c:choose>
					<c:when test="${empty authUser }">
						<li><a href="<%=request.getContextPath() %>/user?a=Loginform">로그인</a><li>
						<li><a href="<%=request.getContextPath() %>/user?a=joinform">회원가입</a><li>
					</c:when>
					<c:otherwise>
						<li><a href="<%=request.getContextPath() %>/user?a=updateform">회원정보수정</a><li>
						<li><a href="<%=request.getContextPath() %>/user?a=Logout">로그아웃</a><li>
						<li>${sessionScope.authUser.name }님 안녕하세요 ^^;</li>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>