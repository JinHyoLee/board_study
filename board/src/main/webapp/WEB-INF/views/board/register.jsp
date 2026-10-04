<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기</title>
<style>
	.form-group { margin-bottom: 15px; }
	label { display: block; margin-bottom: 5px; font-weight: bold; }
	input[type="text"], textarea { width: 100%; padding: 8px; box-sizing: border-box; }
	textarea { height: 200px; }
	button { padding: 10px 20px; background-color: #4CAF50; color: white; border: none; cursor: pointer; }
</style>
</head>
<body>

	<h2>게시글 작성 (Create)</h2>
	
	<form action="/board/register" method="post">
		<div class="form-group">
			<label>제목</label>
			<input type="text" name="title" placeholder="제목을 입력하세요" required>
		</div>
		
		<div class="form-group">
			<label>내용</label>
			<textarea name="content" placeholder="내용을 입력하세요" required></textarea>
		</div>
		
		<div class="form-group">
			<label>작성자</label>
			<input type="text" name="writer" placeholder="작성자를 입력하세요" required>
		</div>
		
		<button type="submit">등록하기</button>
	</form>

</body>
</html>