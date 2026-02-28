package top.blogapi.dto.response.comment;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.pagehelper.PageInfo;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentByBlogIdResponse {

    PageInfo<CommentNode> comments;
    CommentStats commentStats;

    public CommentByBlogIdResponse(PageInfo<CommentNode> comments,Integer totalComments){
        this.comments = comments;
        this.commentStats = new CommentStats();
        this.commentStats.totalComments = totalComments;
    }

    public CommentByBlogIdResponse(PageInfo<CommentNode> comments){
        this.comments = comments;
        this.commentStats = new CommentStats(0,0);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class CommentNode{
        Long id;
        String nickname;
        String content;
        String avatar;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createTime;
        Boolean adminComment;
        String reply;
        String website;
        Long threadRoot;
        List<CommentNode> replyComment;
    }
    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentStats {
        Integer totalComments;
        Integer uniqueCommenters ;
    }
}
