package kr.mjc.pds.web.finals;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.mjc.pds.web.dao.Article;
import kr.mjc.pds.web.dao.ArticleDao;
import kr.mjc.pds.web.dao.Limit;
import kr.mjc.pds.web.finals.Post;
import kr.mjc.pds.web.finals.PostDao;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class PostControlerV1 {
    private final PostDao postDao;

    @GetMapping("/post/postList")
    public void articleList(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Limit limit =
                new Limit(req.getParameter("count"), req.getParameter("page"));
        req.setAttribute("limit", limit);
        List<Post> postList = postDao.listPosts(limit);
        req.setAttribute("postList", postList);
        req.getRequestDispatcher("/WEB-INF/jsp/post/postList.jsp")
                .forward(req, resp);
    }


    @PostMapping("/post/addPost")
    public void addPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        HttpSession session =req.getSession();
        Post post = new Post();
        post.setPostId((Integer) session.getAttribute("me_postId"));
        post.setContent(req.getParameter("content"));
        post.setName(req.getParameter("name"));
        post.setUserId((Integer) session.getAttribute("me_userId"));
        post.setName((String) session.getAttribute("me_name"));
        postDao.addPost(post);
        resp.sendRedirect(
                req.getContextPath() + "/app/post/postList");

    }

}
