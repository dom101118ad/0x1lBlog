package top.blogapi.service.impl.orchestration;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.blogapi.dto.request.tag.CreateTagRequest;
import top.blogapi.dto.request.tag.TagQueryRequest;
import top.blogapi.dto.request.tag.UpdateTagRequest;
import top.blogapi.dto.response._page.TagListPageResponse;
import top.blogapi.dto.response.tag.TagIdGetBlogsResponse;
import top.blogapi.dto.response.tag.TagResponse;
import top.blogapi.mapper.TagMapper;
import top.blogapi.model.entity.Tag;
import top.blogapi.model.vo.BlogTagsInfo;
import top.blogapi.service.TagService;

import java.util.List;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class TagOrchestrator {
    TagService tagService;
    TagMapper tagMapper;

    public TagListPageResponse getTagListPage(TagQueryRequest tagQueryRequest) {
        PageInfo<TagResponse> pageInfo = tagService.getTagList(tagQueryRequest)
                .convert(tagMapper::tagToTagResponse);
        return new TagListPageResponse(pageInfo);
    }

    public List<Tag> getTagList(){
        return tagService.getTagList();
    }

    public void createTag(CreateTagRequest request){
        tagService.saveTag(request.getTagName(),request.getTagColor());
    }

    public void deleteTagById(Long tagId){
        tagService.deleteTagById(tagId);
    }

    public void updateTag(UpdateTagRequest request){
        tagService.updateTag(request.getTagName(), request.getTagColor(), request.getId());
    }

    @SuppressWarnings("resource")
    public TagIdGetBlogsResponse tagIdGetBlogsResponse(Long tagId,Integer pageNum, Integer pageSize){
        String orderBy = "is_top desc, create_time desc";
        PageHelper.startPage(pageNum,pageSize,orderBy);
        PageInfo<BlogTagsInfo>  blogTagsInfos =
                new PageInfo<>(tagService.getBlogInfoListByTagIdAndIsPublished(tagId));
        Tag tag = tagService.getTagById(tagId);
        return new TagIdGetBlogsResponse(
                tagMapper.toTagIdGetBlogsResponse_Tag(tag),
                blogTagsInfos.convert(tagMapper::toTagIdGetBlogsResponse)
        );
    }
}
