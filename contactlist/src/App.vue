<template>
  <div>
    <div class="q-gutter-y-md">
      <q-tabs dense align="justify" class="bg-primary text-white shadow-2" :breakpoint="0">
        <q-route-tab name="list" icon="list" to="/" exact />
        <q-route-tab name="edit" icon="create" to="/edit" exact />
        <q-route-tab name="search" icon="search" to="/search" exact />
        <q-route-tab name="create" icon="add_box" to="/create" exact />
        <q-route-tab name="view" icon="account_box" to="/view" exact />
      </q-tabs>
    </div>
    <q-layout>
      <q-page-container>
        <router-view />
      </q-page-container>
    </q-layout>
  </div>
</template>

<script>
export default {
  data() {
    return {};
  }
};
</script>