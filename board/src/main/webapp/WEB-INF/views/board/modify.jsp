<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 수정하기</title>
</head>
<body>

	<h2>게시글 수정하기 (Update)</h2>
	
	<form action="/board/modify" method="post">
		<div>
			<label>번호</label>
			<input type="text" name="bno" value="${board.bno}" readonly>
		</div>
		<div>
			<label>제목</label>
			<input type="text" name="title" value="${board.title}" required>
		</div>
		<div>
			<label>내용</label>
			<textarea name="content" required>${board.content}</textarea>
		</div>
		<div>
			<label>작성자</label>
			<input type="text" name="writer" value="${board.writer}" required>
		</div>
		
		<button type="submit">수정완료</button>
		<button type="button" onclick="this.form.action='/board/remove'; this.form.submit();" style="background-color: #f44336; color: white;">삭제하기</button>
		<button type="button" onclick="location.href='/board/list'">취소</button>
		
	</form>

</body>
</html>