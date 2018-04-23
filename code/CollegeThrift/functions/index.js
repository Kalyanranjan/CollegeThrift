const functions = require('firebase-functions');

const request = require('request-promise');

let admin = require('firebase-admin');

admin.initializeApp({
	credential: admin.credential.applicationDefault(),
	databaseURL: "https://collegethrift-base.firebaseio.com"
});

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });

exports.indexListingsToElastic = functions.database.ref("/listings/{listing_id}").onWrite(event => {
	let listingData = event.data.val();
	let listing_id = event.params.listing_id;

	console.log('Indexing Listing', listingData);

	let elasticSearchConfig = functions.config().elasticsearch;
	let elasticSearchUrl = elasticSearchConfig.url + 'listings/listing/' + listing_id;
	let elasticSearchMethod = listingData ? 'POST':'DELETE';


	let elasticSearchRequest = {
		url: elasticSearchUrl,
		auth: {
			username: elasticSearchConfig.username,
			password: elasticSearchConfig.password
		},
		method: elasticSearchMethod,
		body: listingData,
		json: true
	};

	return request(elasticSearchRequest).then(response => {
		console.log("ElasticSearch response", response);
		return 0;
	});
});

exports.sendNotification = functions.database.ref('/messages/{convId}/{messageId}').onWrite(event => {
	
	//get the user id of the person who sent the message
	const senderId = event.data.child('messageSenderUid').val();
	console.log("senderId: ", senderId);

	//get the userId of the person receiving the notification because we need to get their token
	const receiverId = event.data.child('messageReceiverUid').val();
	console.log("receiverId: ", receiverId);
	
	//get the message
	const message = event.data.child('messageText').val();
	console.log("message: ", message);
	
	//get the message id. We'll be sending this in the payload
	const messageId = event.params.messageId;
	console.log("messageId: ", messageId);
	
	//query the users node and get the name of the user who sent the message
	return admin.database().ref("/users/" + senderId).once('value').then(snap => {
		const senderName = snap.child("fullname").val();
		console.log("senderName: ", senderName);

		const senderEmail = snap.child("email").val();
		console.log("senderEmail: ", senderEmail);
		
		//get the token of the user receiving the message
		return admin.database().ref("/users/" + receiverId).once('value').then(snap => {
			const token = snap.child("userFCMToken").val();
			console.log("token: ", token);
			
			//we have everything we need
			//Build the message payload and send the message
			console.log("Construction the notification message.");
			const payload = {
				data: {
					data_type: "direct_message",
					title: "New Message from " + senderName + " <"+senderEmail+">",
					message: message,
					message_id: messageId,
				}	
			};
			
			return admin.messaging().sendToDevice(token, payload)
						.then(function(response) {
							console.log("Successfully sent message:", response);
						  	return 0;
						  })
						  .catch(function(error) {
							console.log("Error sending message:", error);
						  });
		});
	});
});
