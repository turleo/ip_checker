const functions = require('firebase-functions');
const util = require('util');

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
exports.getIP = functions.https.onRequest((request, response) => {
    const ipAddress = request.headers['x-forwarded-for'] || request.connection.remoteAddress;
    const message = util.format("%s", ipAddress);
    response.set('Access-Control-Allow-Origin', '*');
    response.send(message);
});
