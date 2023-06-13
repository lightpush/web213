package kr.mjc.pds.web.finals;

import jakarta.servlet.http.HttpServletRequest;
import kr.mjc.pds.web.HttpUtils;
import kr.mjc.pds.web.finals.Post;
import kr.mjc.pds.web.finals.PostDao;
import kr.mjc.pds.web.dao.Limit;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.server.ResponseStatusException;

/**
 * Servlet API를 사용하지 않는 컨트롤러
 */
@Controller
@AllArgsConstructor
public class PostControllerV2 {
  private static final String CURRENT_POST_LIST = "CURRENT_POST_LIST";
  private final PostDao postDao;

  @GetMapping("/article/articleList")
  public void articleList(HttpServletRequest req, Limit limit, Model model) {
    // 현재 목록을 세션에 저장
    req.getSession().setAttribute(CURRENT_POST_LIST,
        HttpUtils.getRequestURLWithQueryString(req));

    req.setAttribute("postList", postDao.listPosts(limit));
  }

  @GetMapping("post/postForm")
  public void mapDefault() {
  }

  @PostMapping("/post/addPost")
  public String addArticle(Post post,
      @SessionAttribute("me_userId") int userId,
      @SessionAttribute("me_name") String name) {
    post.setUserId(userId);
    post.setName(name);
    postDao.addPost(post);
    return "redirect:/app/post/postList";
  }

  @GetMapping("/post/post")
  public void post(int postId, Model model) {
    model.addAttribute("post",postDao.getPost(postId));
  }

  @GetMapping("/post/postEdit")
  public void postEdit(int postId,
      @SessionAttribute("me_userId") int userId, Model model) {
    Post post = getUserPost(postId, userId);
    model.addAttribute("post", post);
  }

  @PostMapping("/post/updatePost")
  public String updateArticle(Post post,
      @SessionAttribute("me_userId") int userId) {
    getUserPost(post.getPostId(), userId);
    post.setUserId(userId);
    postDao.updatePost(post);
    return "redirect:/app/post/post?postId=" + post.getPostId();
  }



  /**
   * 게시글의 권한 체크
   *
   * @throws ResponseStatusException 권한이 없을 경우
   */
  private Post getUserPost(int postId, int userId) {
    try {
      return postDao.getUserPost(postId, userId);
    } catch (DataAccessException e) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
  }
}
