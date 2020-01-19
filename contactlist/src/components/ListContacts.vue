<template>
<div><q-tabs
        v-model="tab"
        dense
        align="justify"
        class="bg-primary text-white shadow-2"
        :breakpoint="0"
      >
        <q-tab name="mails" icon="mail" />
        <q-tab name="alarms" icon="alarm" />
      </q-tabs></div>
  <!--<div class="row justify-content-center">
    <div class="col-md-8">
      <div class="card card-default">
        <div class="card-header">List Component</div>

        <div class="card-body">I'm the List Component.</div>
      </div>
    </div>
  </div>-->
</template>
 <script>
export default {};
</script>