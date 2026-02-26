export interface SaveCommentReq{
    content: string,
    nickname: string,
    email: string,
    notice: boolean,
    blogId: number,
    website?: string,
    parentCommentId?: string
}