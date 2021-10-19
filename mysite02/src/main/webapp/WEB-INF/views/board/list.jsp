<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.request.contextPath }/board" method="post">
					<input type="text" id="kwd" name="kwd" value="test">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th style="text-align:left">제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>				
					<c:set var='count' value='${fn:length(all_board) }'/>
					<c:set var='page_count' value='${(count/10)+(1-((count/10)%1))%1 }'/> <!-- 무조건 올림 -->
					<c:set var='pageblock' value='${5 }'/>
					<c:forEach items='${boardlist }' var='vo' varStatus='status'>
						<tr>
							<td>${count-status.index }</td>

							<td style="text-align:left; padding-left:${20*vo.depth }px">
							<c:if test="${vo.depth != 0 }">
								<img src='${pageContext.servletContext.contextPath }/assets/images/reply.png' />
							</c:if>
							<a href="${pageContext.request.contextPath }/board?a=view&no=${vo.no }">${vo.title }</a></td>

							<c:forEach items='${userlist }' var='vo2'>
								<c:if test="${vo.user_no == vo2.no }">
									<td>${vo2.name }</td>	
								</c:if>
							</c:forEach> <!-- DB의 게시판테이블과 유저테이블의 no값을 비교후 조건에 맞는 name값 출력 -->
														
							<td>${vo.hit }</td> <!-- 클릭에 동적으로 조회수 증가하게 수정 -->
							<td>${vo.regdate }</td>
							<td><a href="" class="del">삭제</a></td>
						</tr>
					</c:forEach>
				</table>
				
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
					
						<c:if test="${ pageno != 1  }">
							<li><a href="${pageContext.request.contextPath }/board?pageno=${pageno-1 }">◀</a></li>
        				</c:if>
						
					<c:forEach var="i" begin="${((pageno-1)/pageblock)*pageblock+1 }" end="${((pageno-1)/pageblock)*pageblock+5 }"> <!-- 5단위로 올림해줌 -->
							<c:choose>
								<c:when test="${i > page_count }">
									<li>${i }</li>
								</c:when>
								<c:when test="${i == pageno }"> <!-- pageno = 현재 페이지 -->
									<li class="selected">${i }</li>
								</c:when>
								<c:otherwise>
									<li><a href="${pageContext.request.contextPath }/board?pageno=${i }">${i }</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>

						<c:if test="${ pageno != page_count  }">
							<li><a href="${pageContext.request.contextPath }/board?pageno=${pageno+1 }">▶</a></li>
        				</c:if>
        				
					</ul>
				</div>					
				<!-- pager 추가 -->
				
				<div class="bottom">
					<a href="${pageContext.request.contextPath }/board?a=write" id="new-book">글쓰기</a>
				</div>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>