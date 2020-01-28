<template>
  <div>
    <div class="q-pa-md">
      <q-table :data="data" :columns="columns" row-key="name" :separator="separator">
        <template v-slot:no-data="{ icon, message, filter }">
          <div class="full-width row flex-center text-accent q-gutter-sm">
            <q-icon size="2em" name="sentiment_dissatisfied" />
            <span>Error 404. You have no friends!</span>
            <q-icon size="2em" :name="filter ? 'filter_b_and_w' : icon" />
          </div>
        </template>
      </q-table>
    </div>
  </div>
  <!--
    <h1>Contacts</h1>
    <div class="row">
      <div class="col-md-10"></div>
      <div class="col-md-2">
        <router-link :to="{ name: 'create' }" class="btn btn-primary">Create Contact</router-link>
      </div>
    </div>
    <br />

    <table class="table table-hover">
      <thead>
        <tr>
          <th width="10px">First Name</th>
          <th>Last Name</th>
          <th>Phone Number</th>
          <th>Email</th>
          <th>Birthday</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="contact in contacts" :key="contact._id">
          <td>{{ contact.firstName }}</td>
          <td>{{ contact.lastName }}</td>
          <td>{{ contact.phoneNumber }}</td>
          <td>{{ contact.email }}</td>
          <td>{{ contact.bday }}</td>
          <td>
            <router-link :to="{name: 'edit', params: { id: contact._id }}" class="btn btn-primary">Edit</router-link>
          </td>
          <td>
            <button class="btn btn-danger" @click.prevent="deleteContact(contact._id)">Delete</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>-->
</template>
 <script>
export default {
  data() {
    return {
      separator: "horizontal",
      data: [],
      columns: [
        { name: "firstName", label: "First Name", field: "firstName" },
        { name: "lastName", label: "Last Name", field: "lastName" },
        { name: "phoneNumber", label: "Phone Number", field: "phoneNumber" },
        { name: "email", label: "Email", field: "email" },
        { name: "bday", label: "Birthday", field: "bday" }
      ]
    };
  },
  created() {
    // function called once vue has been created
    let uri = "http://localhost:4000/contacts"; // make web service call
    this.axios.get(uri).then(response => {
      this.data = response.data; // grab contacts
      console.log(this.data);
    });
  }
};
</script>