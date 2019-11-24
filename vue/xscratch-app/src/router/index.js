import Vue from 'vue'
import Router from 'vue-router'
// import HelloWorld from '@/components/HelloWorld'
import VideosPage from '@/pages/VideosPage'
import AdminPage from '@/pages/AdminPage'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Videos',
      component: VideosPage
    },
    {
      path: '/',
      name: 'AdminP',
      component: AdminPage
    }
  ]
})
