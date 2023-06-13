<!DOCTYPE html>
<html>
<head>
  <title>게시글쓰기</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="../../css/default.css">
</head>
<body>
<%@include file="/WEB-INF/jsp/header.jsp" %>
<main>
  <h3>로그인</h3>
  <form action="addPost" method="post">
    <ul class="list">
      <li><input type="email" name="email" placeholder="이메일" required
                 autofocus/></li>
      <li><input type="password" name="password" placeholder="비밀번호"
                 required/>
      </li>
    </ul>
    <p>
      <button type="submit">로그인</button>
    </p>
  </form>
  <c:if test="${param.mode=='FAILURE'}">
    <p class="warn">로그인 실패입니다.</p>
  </c:if>
</main>
</body>
</html>