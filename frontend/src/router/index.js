import Vue from 'vue'
import Router from 'vue-router'
import MyPlayer from '../components/MyPlayer'

Vue.use(Router)

export default new Router({
  routes: [
    {path:'/',component:MyPlayer}
  ]
})
