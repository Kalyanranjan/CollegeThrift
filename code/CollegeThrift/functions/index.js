const functions = require('firebase-functions');

const request = require('request-promise');

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
		body: listingData,
		json: true
	};

	return request(elasticSearchRequest).then(response => {
		console.log("ElasticSearch response", response);
		return 0;
	});
});  