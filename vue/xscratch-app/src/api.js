import Vue from 'vue'
import axios from 'axios'

const client = axios.create({
  baseURL: 'https://fv9h3awn2g.execute-api.us-east-2.amazonaws.com/beta'
})

export default {
  async execute (method, resource, data) {
    return client({
      method: method,
      url: resource,
      data: data
    }).then(req => {
      return req
    }).catch((error) => {
      new Vue().$bvToast.toast(resource + ' did not load', {
        title: 'Error',
        variant: 'danger',
        solid: true,
        autoHideDelay: 2000,
        isStatus: true,
        noCloseButton: true,
        toaster: 'b-toaster-top-right'
      })
      return error.response
    })
  },

  async getRemoteVideos () {
    var tlpList = await this.getTLPs()
    var remoteVideoList = []
    for (let index = 0; index < tlpList.length; index++) {
      const response = await this.getTLPVideos(tlpList[index])
      if (response !== 'bad tlp') {
        response.data.segments.forEach(seg => {
          let formattedSeg = {
            characters: [
              seg.character
            ],
            dialogue: seg.text,
            url: seg.url,
            name: 'no name'
          }
          remoteVideoList.push(formattedSeg)
        })
      } else {
        console.log('bad tlp')
      }
    }
    return remoteVideoList
  },

  async getTLPVideos (tlp) {
    tlp.base = tlp.url.substring(0, tlp.url.indexOf('?'))
    tlp.key = tlp.url.substring(tlp.url.indexOf('=') + 1)
    if (tlp.base === '' || tlp.key === '') return 'bad tlp'
    let config = {
      headers: {
        'x-api-key': tlp.key
      }
    }
    return axios.get(tlp.base, config).then(res => {
      return res
    }).catch((error) => {
      console.log(error)
    })
  },

  async getVideos () {
    const response = await this.execute('get', '/videos')
    const remoteArray = await this.getRemoteVideos()
    var videoArray = JSON.parse(response.data.body).videos
    remoteArray.forEach(el => {
      videoArray.push(el)
    })
    return videoArray
  },

  async searchVideos (searchString) {
    const response = await this.execute('get', '/videos?filter=' + searchString)
    return JSON.parse(response.data.body).videos
  },

  async getPlaylists () {
    const response = await this.execute('get', '/playlists')
    return JSON.parse(response.data.body).playlists
  },

  async getPlaylist (id) {
    const response = await this.execute('get', '/playlists/' + id)
    return JSON.parse(response.data.body)
  },

  async createPlaylist (title) {
    const body = {
      'name': title
    }
    const response = await this.execute('post', '/playlists', body)
    return JSON.parse(response.data.body)
  },

  async createVideo (videoBody) {
    const response = await this.execute('post', '/videos', videoBody)
    return JSON.parse(response.data.body)
  },

  async deletePlaylist (id) {
    const response = await this.execute('post', '/playlists/delete', id)
    return JSON.parse(response.data.body)
  },

  async deleteVideo (id) {
    const body = {
      'id': id
    }
    const response = await this.execute('post', '/videos/delete', body)
    return JSON.parse(response.data.body).videos
  },

  async registerTLP (url) {
    const body = {
      'url': url
    }
    const response = await this.execute('post', '/tlp', body)
    return JSON.parse(response.data.body)
  },

  async getTLPs () {
    const response = await this.execute('get', '/tlp')
    return JSON.parse(response.data.body).listOfTLP
  },
  async deleteTLP (id) {
    const response = await this.execute('post', '/tlp/delete', id)
    return JSON.parse(response.data.body)
  },
  async appendVideo (playlistID, videoID) {
    const body = {
      'id': videoID
    }
    const response = await this.execute('post', '/playlists/' + playlistID + '/video', body)
    return JSON.parse(response.data.body)
  },

  async removeVideo (playlistID, videoID) {
    const body = {
      'id': videoID
    }
    const response = await this.execute('post', '/playlists/' + playlistID + '/video/delete', body)
    return JSON.parse(response.data.body)
  },

  async getCharacters () {
    // Empty body
    // const body = {
    // }

    // const response = await this.execute('get', '/characters', body)

    const mockResponse = {
      'characters': ['Spock', 'Kirk', 'Conner', 'Emmett', 'Nour', 'Vlad', 'Other Character']
    }

    // TODO change with actual response
    return mockResponse.characters
  }

}
