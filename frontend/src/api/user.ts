import request from '../utils/request'

export interface SysUser {
  id?: number
  account: string
  mobile: string
  password: string
  nickName: string
  createTime?: number
  updateTime?: number
}

export interface PageInfo<T> {
  list: T[]
  total: number
  pageNum: number
  pageSize: number
  pages: number
}

export const getUserById = (id: number) => {
  return request.get<SysUser>(`/sys/user/${id}`)
}

export const getUserPage = (params: {
  pageNum: number
  pageSize: number
  account?: string
  mobile?: string
}) => {
  return request.get<PageInfo<SysUser>>('/sys/user/page', { params })
}

export const saveUser = (data: SysUser) => {
  return request.post<boolean>('/sys/user', data)
}

export const updateUser = (data: SysUser) => {
  return request.put<boolean>('/sys/user', data)
}

export const deleteUser = (id: number) => {
  return request.delete<boolean>(`/sys/user/${id}`)
}
