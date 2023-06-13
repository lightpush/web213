<!DOCTYPE html>
<html>
<head>
    <title>포스트</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../../css/default.css">
    <style>
        input[type='number'] {width:50px;text-align:center;}
    </style>
</head>
<body>
<%@include file="/WEB-INF/jsp/header.jsp" %>
<main>
    <h3>게시글 목록</h3>
    <form action="./postList" method="get">
        <p>
            페이지 : <input type="number" name="page" min="1" value="${limit.page}"
                         required autofocus/> 행 : <input type="number" name="count" min="5"
                                                         step="5" value="${limit.count}" required/>
            <button type="submit">검색</button>
            <a href="./postForm">글쓰기</a>
        </p>
    </form>
    <table class="list">

        <c:forEach var="post" items="${postList}">
            <tr>
                <td>${post.postId}</td>
                <td><a
                        href="./post?postId=${post.postId}">${post.cdate}</a>
                </td>
                <td><a
                        href="../user/userInfo?userId=${post.userId}">${post.name}</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</main>
</body>
</html>