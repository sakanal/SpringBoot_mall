import axios from 'axios'
import cookie from "js-cookie";

const service = axios.create({
  baseURL: 'http://localhost:8080',
  timeout: 5000
})

service.interceptors.request.use(
  config => {
    if (cookie.get('token')) {
      config.headers['token'] = cookie.get('token');
    }
    return config
  },
  err => {
    return Promise.reject(err);
  })

export default service
