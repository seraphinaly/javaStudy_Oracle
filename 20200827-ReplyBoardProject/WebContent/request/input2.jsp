<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<center>
		<h1>POST방식으로 데이터 전송(=>output2.jsp)</h1>
		<!-- <form method=post>, AJAX -->
		<form method=post action="output2.jsp">
		<table border=1 width=350>
			<tr>
				<td width=30% align=right>이름</td>
				<td width=70% align=left>
					<input type=text name=name size=15>
				</td>			
			</tr>
			<tr>
				<td width=30% align=right>성별</td>
				<td width=70% align=left>
					<input type=radio value="남자" name=sex checked>남자
					<input type=radio value="여자" name=sex>여자
				</td>			
			</tr>
			<tr>
				<td width=30% align=right>지역</td>
				<td width=70% align=left>
					<select name=loc>
						<option>경기</option>
						<option>서울</option>
						<option>부산</option>
						<option>인천</option>
						<option>강원</option>
						<!-- 값을 넘겨야될때는 <option value="seoul"> 이런식으로 줌 -->
					</select>
				</td>			
			</tr>
			<tr>
				<td width=30% align=right>취미</td>
				<td width=70% align=left>
					<input type="checkbox" value="등산" name=hobby>등산
					<input type="checkbox" value="낚시" name=hobby>낚시
					<input type="checkbox" value="게임" name=hobby>게임
					<input type="checkbox" value="독서" name=hobby>독서
					<input type="checkbox" value="여행" name=hobby>여행
				</td>			
			</tr>
			<tr>
				<td width=30% align=right>소개</td>
				<td width=70% align=left>
					<textarea rows="7" cols="30" name=content></textarea>
				</td>			
			</tr>
			<tr>
				<td colspan=2 align=center>
				<button>전송</button>
				<!-- 데이터 전송버튼
				 1. <input type=submit value=전송>
				 2. <button>전송</button>
				 3. <input type=image value=전송> -->
				</td>		
			</tr>
		</table>
		</form>	
	</center>
</body>
</html>