import axios from '@/plugins/axios'
import type {SaveCommentReq} from "@/types/commentType";

export function getCommentListByQuery(query: any) {
    return axios({
        url: 'comment-tree',
        method: 'GET',
        params: {
            ...query
        }
    })
}

export function saveComment(req: SaveCommentReq){
    return axios({
        url: 'comment',
        method: 'POST',
        data: req,
    })
}