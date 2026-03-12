import {type ApiResponse, request} from '@/plugins/axios2'
import type {Category} from "@/types/categoryType";

export function fGetCategoryList(): Promise<ApiResponse<Category[]>> {
    return request({
        url: 'categories',
        method: 'GET'
    })
}