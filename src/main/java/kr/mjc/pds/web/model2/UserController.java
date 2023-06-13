package kr.mjc.pds.web.model2;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.mjc.pds.web.dao.Limit;
import kr.mjc.pds.web.dao.User;
import kr.mjc.pds.web.dao.UserDao;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class UserController {

  private final UserDao userDao;

  /**
   * just forward
   */
  public void mapDefault(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.getRequestDispatcher("/WEB-INF/jsp%s.jsp".formatted(req.getPathInfo()))
        .forward(req, resp);
  }

  /**
   * 회원목록
   */
  public void userList(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    Limit limit =
        new Limit(req.getParameter("count"), req.getParameter("page"));
    req.setAttribute("limit", limit);
    List<User> userList = userDao.listUsers(limit);
    req.setAttribute("userList", userList);
    req.getRequestDispatcher("/WEB-INF/jsp/user/userList.jsp")
        .forward(req, resp);
  }

  /**
   * 회원가입
   */
  public void signup(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    User user = new User();
    user.setEmail(req.getParameter("email"));
    user.setPassword(req.getParameter("password"));
    user.setName(req.getParameter("name"));

    try {
      userDao.addUser(user);
      // 등록 성공
      signin(req, resp);
    } catch (DataAccessException e) { // 등록 실패
      log.error(e.getCause().toString());
      resp.sendRedirect(
          req.getContextPath() + "/mvc/user/signupForm?mode=FAILURE");
    }
  }

  /**
   * 로그인
   */
  public void signin(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    String email = req.getParameter("email");
    String password = req.getParameter("password");
    try {
      User user = userDao.login(email, password);
      // 로그인 성공
      HttpSession session = req.getSession();
      session.setAttribute("me_userId", user.getUserId());
      session.setAttribute("me_name", user.getName());
      session.setAttribute("me_email", user.getEmail());
      resp.sendRedirect(req.getContextPath() + "/mvc/user/userList");
    } catch (DataAccessException e) {
      // 로그인 실패
      resp.sendRedirect(
          req.getContextPath() + "/mvc/user/signinForm?mode=FAILURE");
    }
  }

  /**
   * 비밀번호변경
   */
  public void updatePassword(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    int userId = (int) req.getSession().getAttribute("me_userId");
    String currentPassword = req.getParameter("currentPassword");
    String newPassword = req.getParameter("newPassword");

    int updatedRows =
        userDao.updatePassword(userId, currentPassword, newPassword);
    if (updatedRows >= 1) // 업데이트 성공
      resp.sendRedirect(req.getContextPath() + "/mvc/user/myInfo");
    else  // 업데이트 실패
      resp.sendRedirect(
          req.getContextPath() + "/mvc/user/passwordEdit?mode=FAILURE");
  }

  /**
   * 로그아웃
   */
  public void signout(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    req.getSession().invalidate();
    resp.sendRedirect(req.getContextPath() + "/mvc/user/userList");
  }
}
