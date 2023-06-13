package kr.mjc.pds.web.finals;

import lombok.Data;

@Data
public class Post {
  int postId;

  String content;
  int userId;
  String name;
  String cdate;

  @Override
  public String toString() {
    return String.format(
        "\nArticle{postId=%d, content=%s, userId=%d, name=%s, cdate=%s}",
        postId, content, userId, name, cdate);
  }
}