<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 상세보기</title>
</head>
<body>

	<h2>게시글 상세보기 (Read)</h2>
	
	<div>
		<label>번호</label>
		<input type="text" value="${board.bno}" readonly>
	</div>
	<div>
		<label>제목</label>
		<input type="text" value="${board.title}" readonly>
	</div>
	<div>
		<label>내용</label>
		<textarea readonly>${board.content}</textarea>
	</div>
	<div>
		<label>작성자</label>
		<input type="text" value="${board.writer}" readonly>
	</div>
	
	<button onclick="location.href='/board/modify?bno=${board.bno}'">수정하기</button>
	<button onclick="location.href='/board/list'">목록으로</button>

</body>
</html>