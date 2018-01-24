const admin = require('firebase-admin');
var bodyParser = require('body-parser')
const serviceAccount = require('./tpfinal-ca1fa-firebase-adminsdk-q85mo-5b2e3ed68c.json');
const express = require('express');
const server = express();
const topic = "residentes";

//Inicializa server y firebase-admin
server.use( bodyParser.json() );       // to support JSON-encoded bodies
//server.use(bodyParser.urlencoded({ extended: true }));
admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: 'https://tpfinal-ca1fa.firebaseio.com'
});


server.post('/', function(req, res) {
	console.log("Message received: " + req.body.message);
	
	//Envia mensaje recibido en la request al topic de residentes
  const payload = {
		notification: {
			title: "Nueva Guardia",
			body: req.body.message
		  }
	};


admin.messaging().sendToTopic(topic, payload)
  .then(function(response) {
    // See the MessagingTopicResponse reference documentation for the
    // contents of response.
    console.log("Successfully sent message:", response);
  })
  .catch(function(error) {
    console.log("Error sending message:", error);
  });
  
  
  
  res.send('OK!');
});

//Crea server
server.listen(3225, function() {
  console.log('Listening on port 3225...');
});

