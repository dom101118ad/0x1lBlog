import {request, type ApiResponse} from '@/plugins/axios2'
import type {Tag, TagIdGetBlogsResponse} from "@/types/tagType";



export const getTags = (): Promise<ApiResponse<Tag[]>> =>
    request({
        url: 'tags',
        method: 'get'
    })


export const getBlogListByTagId =
    (tagId: number, pageNum: number, pageSize: number): Promise<ApiResponse<TagIdGetBlogsResponse>> =>
    request({
        url: 'tag',
        method: 'GET',
        params: {
            tagId,
            pageNum,
            pageSize
        }
    })


