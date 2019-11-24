import Vue from 'vue'
import Router from 'vue-router'
// import HelloWorld from '@/components/HelloWorld'
import VideosPage from '@/pages/VideosPage'
import PlaylistPage from '@/pages/PlaylistPage'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'videos',
      component: VideosPage
    },
    {
      path: '/playlists/:playlistID',
      name: 'playlist',
      component: PlaylistPage,
      meta: {
        requiresAuth: true
      }
    }
  ]
})
