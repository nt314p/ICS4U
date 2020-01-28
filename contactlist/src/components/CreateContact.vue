<template>
  <div>
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
    </form>
  </div>
</template>

 <script>
export default {
  data() {
    return {
      contact: {}
    };
  },
  methods: {
    addContact(){
    let uri = 'http://localhost:4000/contacts/add'; // we've mapped urls to web services
    this.axios.post(uri, this.contact).then(() => { // so the "/add" runs the store function in router
       this.$router.push({name: 'list'});
    });
}
  }
};
</script>