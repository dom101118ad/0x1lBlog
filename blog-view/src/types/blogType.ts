import type {Category} from "@/types/categoryType";
import type {Tag} from "@/types/tagType";

export interface BlogInfo {
    id: number;
    title: string;
    description: string;
    createTime: string;
    views: number;
    words: number;
    readTime: number;
    top: boolean;
    category: Category;
    tags: Tag[];
}