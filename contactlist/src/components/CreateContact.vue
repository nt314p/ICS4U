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
          clearable
          filled
          v-model="contact.firstName"
          label="First Name"
          lazy-rules
          :rules="[ val => val && val.length > 0 || 'Please enter a name']"
        />

        <q-input
          clearable
          filled
          v-model="contact.lastName"
          label="Last Name"
          lazy-rules
          :rules="[]"
        />

        <q-input
          clearable
          filled
          v-model="contact.phoneNumber"
          label="Phone Number"
          type="tel"
          mask="phone"
          unmasked-value
          lazy-rules
          :rules="[]"
        />

        <q-input
          ref="email"
          clearable
          filled
          v-model="contact.email"
          label="Email"
          type="email"
          lazy-rules
          :rules="[this.emailRule]"
          @clear="() => $refs.email.resetValidation()"
        />

        <q-input clearable filled v-model="contact.bday" label="Birthday" mask="date" :rules="[]">
          <template v-slot:append>
            <q-icon name="event" class="cursor-pointer">
              <q-popup-proxy ref="qDateProxy" transition-show="scale" transition-hide="scale">
                <q-date v-model="contact.bday" @input="() => $refs.qDateProxy.hide()" />
              </q-popup-proxy>
            </q-icon>
          </template>
        </q-input>

        <div>
          <q-btn label="Submit" type="submit" color="primary" />
          <q-btn label="Reset" type="reset" color="primary" flat class="q-ml-sm" />
        </div>
      </q-form>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      emailPattern: /^(?=[a-zA-Z0-9@._%+-]{6,254}$)[a-zA-Z0-9._%+-]{1,64}@(?:[a-zA-Z0-9-]{1,63}\.){1,8}[a-zA-Z]{2,63}$/,
      emailRule: val =>
        this.emailPattern.test(val) ||
        val == null ||
        val.length == 0 ||
        "Please enter a valid email address",
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