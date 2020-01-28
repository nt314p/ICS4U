<template>
  <div>
    <div class="q-pa-md" style="max-width: 500px">
      <q-form
        @submit="addContact"
        @reset="onReset"
        class="q-gutter-md"
        autocorrect="off"
        autocapitalize="off"
        autocomplete="off"
        spellcheck="false"
      >
        <q-input
          filled
          v-model="contact.firstName"
          label="First Name"
          lazy-rules
          :rules="[ true||(val => val && val.length > 0)|| 'Please enter a name']"
        >
          <template v-slot:append>
            <q-icon name="close" @click="contact.firstName = ''" class="cursor-pointer" />
          </template>
        </q-input>

        <q-input filled v-model="contact.lastName" label="Last Name" lazy-rules :rules="[]">
          <template v-slot:append>
            <q-icon name="close" @click="contact.lastName = ''" class="cursor-pointer" />
          </template>
        </q-input>

        <q-input filled v-model="contact.bday" mask="date" :rules="[]">
          <template v-slot:append>
            <q-icon name="event" class="cursor-pointer">
              <q-popup-proxy ref="qDateProxy" transition-show="scale" transition-hide="scale">
                <q-date v-model="contact.bday" @input="() => $refs.qDateProxy.hide()" />
              </q-popup-proxy>
            </q-icon>
            <q-icon name="close" @click="contact.bday = ''" class="cursor-pointer" />
          </template>
        </q-input>

        <div>
          <q-btn label="Submit" type="submit" color="primary" />
          <q-btn label="Reset" type="reset" color="primary" flat class="q-ml-sm" />
        </div>
      </q-form>
    </div>
    <!--
    <h1>Create A Post</h1>
    <form @submit.prevent="addContact">
      <div class="row">
        <div class="col-md-6">
          <div class="form-group">
            <label>Post Title:</label>
            <input type="text" class="form-control" v-model="contact.firstName" />
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-md-6">
          <div class="form-group">
            <label>Post Author:</label>
            <input type="text" class="form-control" v-model="contact.lastName" />
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-md-6">
          <div class="form-group">
            <label>Post Body:</label>
            <textarea class="form-control" v-model="contact.email" rows="5"></textarea>
          </div>
        </div>
      </div>
      <br />
      <div class="for]m-group">
        <button class="btn btn-primary">Create</button>
      </div>
    </form>-->
  </div>
</template>

<script>
export default {
  data() {
    return {
      date: {},
      contact: {}
    };
  },
  methods: {
    addContact() {
      let uri = "http://localhost:4000/contacts/add"; // we've mapped urls to web services
      this.axios.post(uri, this.contact).then(() => {
        // so the "/add" runs the store function in router
        this.$router.push({ name: "list" });
      });
    },
    onReset() {
      this.contact = {};
    }
  }
};
</script>