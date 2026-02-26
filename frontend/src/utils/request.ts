import axios from 'axios'
import type { AxiosInstance, InternalAxiosRequestConfig, AxiosResponse } from 'axios'

const request: AxiosInstance = axios.create({
  baseURL: 'http://localhost:8080',
  timeout: 5000
})

request.interceptors.response.use(
  (response: AxiosResponse) => {
    return response.data
  },
  error => {
    console.error('请求错误:', error)
    return Promise.reject(error)
  }
)

export default request
