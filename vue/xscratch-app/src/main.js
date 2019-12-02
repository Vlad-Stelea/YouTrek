// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import { library } from '@fortawesome/fontawesome-svg-core'
import { faCoffee, faTrash, faPlus, faTimes, faMinusCircle, faGlobe, faPlayCircle, faPauseCircle, faFastForward, faFastBackward, faRedoAlt } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.config.productionTip = false
library.add(faCoffee, faTrash, faPlus, faTimes, faMinusCircle, faGlobe, faPlayCircle, faPauseCircle, faFastForward, faRedoAlt, faFastBackward)

Vue.component('font-awesome-icon', FontAwesomeIcon)

Vue.use(BootstrapVue)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
