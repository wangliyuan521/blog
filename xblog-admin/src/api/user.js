import request from '@/utils/request'

export function fetchUserList(query) {
  return request({
    url: '/user/list',
    params: query
  })
}

export function addUser(data) {
  return request({
    url: '/user/add',
    method: 'post',
    data
  })
}

export function updateUser(data) {
  return request({
    url: '/user/update',
    method: 'put',
    data
  })
}

export function deleteUser(id) {
  return request({
    url: '/user/delete',
    method: 'delete',
    params: { id }
  })
}
