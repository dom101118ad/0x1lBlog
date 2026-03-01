import axios from '@/plugins/axios.js'

export function getArchives(){
    return axios({
        url: 'archives',
        method: 'GET'
    })
}