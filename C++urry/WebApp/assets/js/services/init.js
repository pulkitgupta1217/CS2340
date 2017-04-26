window.onload = init();
function init() {
    var config = {
        apiKey: "AIzaSyDfYeM4BeryxToCYet_xfVjwcFz4oqaRtI",
        authDomain: "waternet-9ed81.firebaseapp.com",
        databaseURL: "https://waternet-9ed81.firebaseio.com",
        projectId: "webapp-17b1d",
        storageBucket: "waternet-9ed81.appspot.com"
    };
    firebase.initializeApp(config);
}