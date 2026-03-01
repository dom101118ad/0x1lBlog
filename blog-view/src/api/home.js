import axios from '@/plugins/axios.js'

export function getBlogList(pageNum){
    return axios({
        url: 'blogs',
        method: 'GET',
        params: {
            pageNum
        }
    })
}