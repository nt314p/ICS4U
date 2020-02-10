<template>
  <div>
    <div class="q-pa-md">
      <q-table
        :data="data"
        :columns="columns"
        row-key="name"
        :separator="separator"
        @row-click="onRowClick"
      >
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
      for (var i = 0; i < this.data.length; i++) {
        this.data[i].bday = this.data[i].bday.split("T")[0];
        this.data[i].phoneNumber = this.data[i].phoneNumber.replace(
          /(\d{3})(\d{3})(\d{4})/,
          "($1) $2-$3"
        );
      }
    });
  },
  methods: {
    onRowClick(e, row) {
      this.$router.push({ name: "view", params: { id: row._id } });
    }
  }
};
</script>