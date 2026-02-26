package top.blogapi.mapper;

import com.github.pagehelper.Page;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import top.blogapi.dto.request.comment.SaveCommentReq;
import top.blogapi.dto.response.comment.CommentByBlogIdResponse;
import top.blogapi.model.entity.Comment;
import top.blogapi.model.vo.CommentTree;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "replyComment", ignore = true)
    CommentByBlogIdResponse.CommentNode toCommentNode(CommentTree commentTree);

    List<CommentByBlogIdResponse.CommentNode> toCommentNodeList(List<CommentTree> commentTrees);

}
