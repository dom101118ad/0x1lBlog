import axios from '@/plugins/axios.js'

export function getBlogById(id){
    return axios({
        url: 'blog',
        method: 'GET',
        params: {
            id
        }
    })
}