package top.blogapi.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import top.blogapi.dto.request.comment.SaveCommentReq;
import top.blogapi.dto.response.comment.CommentByBlogIdResponse;
import top.blogapi.model.vo.Result;
import top.blogapi.service.impl.orchestration.CommentOrchestrator;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class CommentController {
    CommentOrchestrator commentOrchestrator;


    @GetMapping("/comment-tree")
    public Result<CommentByBlogIdResponse> commentTree(@RequestParam Integer page,
                                                      @RequestParam(defaultValue = "") Long blogId,
                                                      @RequestParam(defaultValue = "1") Integer pageNum,
                                                      @RequestParam(defaultValue = "10") Integer pageSize){

        if (!commentOrchestrator.judgeCommentEnabled(page, blogId)) {
            return Result.create(403, "Chức năng bình luận đã bị tắt");
        }
        return Result.ok("Yêu cầu thành công!",commentOrchestrator.listCommentByBlogId(pageNum, pageSize, blogId, page));
    }

    @PostMapping("/comment")
    public Result<?> createComment (@RequestBody SaveCommentReq req,
                                    HttpServletRequest request) throws Exception {
        commentOrchestrator.saveComment(req,request);
        return Result.ok("Đã viết bình luận");
    }
}
