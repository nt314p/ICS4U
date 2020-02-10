import Vue from 'vue'
import App from './App.vue'
import ListComponent from './components/ListContacts.vue'
import SearchComponent from './components/SearchContact.vue'
import CreateComponent from './components/CreateContact.vue'
import EditComponent from './components/EditContact.vue'
import ViewComponent from './components/ViewContact.vue'

import './quasar'
//import 'bootstrap/dist/css/bootstrap.min.css'

import VueAxios from 'vue-axios';
import axios from 'axios';

Vue.use(VueAxios, axios);

import VueRouter from 'vue-router';
Vue.use(VueRouter);

Vue.config.productionTip = false

const routes = [
  {
      name: 'list',
      path: '/',
      component: ListComponent
  },
  {
      name: 'create',
      path: '/create',
      component: CreateComponent
  },
  {
      name: 'view',
      path: '/view/:id',
      component: ViewComponent
  },
  {
      name: 'edit',
      path: '/edit/:id',
      component: EditComponent
  },
  {
      name: 'search',
      path: '/search',
      component: SearchComponent
  }
];

const router = new VueRouter({ mode: 'history', routes: routes});

new Vue({
  router,
  render: h => h(App)

}).$mount('#app')