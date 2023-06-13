<!DOCTYPE html>
<html>
<head>
  <title>로그인</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="../../css/default.css">
</head>
<body>
<%@include file="/WEB-INF/jsp/header.jsp" %>
<main>
  <h3>로그인</h3>
  <form action="signsup" method="post">

      <input type="text" name="title" placeholder="제목" required
                 autofocus/>
      <input textarea name="content" name="password" placeholder="비밀번호"
                 required/>


    <p>
      <button type="submit">저장</button>
    </p>
  </form>
</main>
</body>
</html>