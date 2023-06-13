package kr.mjc.pds.web.springmvc;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.mjc.pds.web.dao.Limit;
import kr.mjc.pds.web.dao.Article;
import kr.mjc.pds.web.dao.ArticleDao;
import kr.mjc.pds.web.dao.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class ArticleControlerV1 {
    private final ArticleDao articleDao;

    @GetMapping("/article/articleList")
    public void articleList(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Limit limit =
                new Limit(req.getParameter("count"), req.getParameter("page"));
        req.setAttribute("limit", limit);
        List<Article> articleList = articleDao.listArticles(limit);
        req.setAttribute("articleList", articleList);
        req.getRequestDispatcher("/WEB-INF/jsp/article/articleList.jsp")
                .forward(req, resp);
    }


    @PostMapping("/article/addArticle")
    public void addArticle(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        HttpSession session =req.getSession();
        Article article = new Article();
        article.setTitle(req.getParameter("title"));
        article.setContent(req.getParameter("content"));
        article.setName(req.getParameter("name"));
        article.setUserId((Integer) session.getAttribute("me_userId"));
        article.setName((String) session.getAttribute("me_name"));
        articleDao.addArticle(article);
        resp.sendRedirect(
                req.getContextPath() + "/app/article/articleList");

    }

}
