const mongoose = require('mongoose');
const Schema = mongoose.Schema;

// Define collection and schema for Contact
let Contact = new Schema({
  firstName: {
    type: String
  },
  lastName: {
    type: String
  },
  phoneNumber: {
    type: String
  },
  email: {
    type: String
  },
  bday: {
    type: Date
  }
},{
    collection: 'contacts'
});

module.exports = mongoose.model('Contact', Contact);