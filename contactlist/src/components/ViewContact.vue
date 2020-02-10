<template>
  <!-- https://quasar.dev/vue-components/list-and-list-items -->
  <div class="q-pa-md" style="max-width: 500px">
    <q-list bordered separator class="rounded-borders shadow-3">
      <q-item class="bg-primary text-white rounded-borders">
        <q-item-section >
        <q-toolbar-title>{{contact.firstName + " " + contact.lastName}}</q-toolbar-title>
        </q-item-section>
      </q-item>

      <q-item>
        <q-item-section>
          <q-item-label caption>Phone</q-item-label>
          <q-item-label>{{contact.phoneNumber}}</q-item-label>
        </q-item-section>
      </q-item>

      <q-item>
        <q-item-section>
          <q-item-label caption>Email</q-item-label>
          <q-item-label>{{contact.email}}</q-item-label>
        </q-item-section>
      </q-item>

      <q-item>
        <q-item-section>
          <q-item-label caption>Birthday</q-item-label>
          <q-item-label>{{contact.bday}}</q-item-label>
        </q-item-section>
      </q-item>
    </q-list>
  </div>
</template>
<script>
export default {
  data() {
    return {
      contact: {}
    };
  },
  created() {
    let uri = `http://localhost:4000/contacts/view/${this.$route.params.id}`;
    this.axios.get(uri).then(response => {
      this.contact = response.data;
      this.contact.bday = this.contact.bday.split("T")[0];
      this.contact.phoneNumber = this.contact.phoneNumber.replace(
        /(\d{3})(\d{3})(\d{4})/,
        "($1) $2-$3"
      );
    });
  }
};
</script>
