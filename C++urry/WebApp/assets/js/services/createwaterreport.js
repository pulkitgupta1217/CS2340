checkSignedIn();

function submitWaterReport() {
    var lat = $("#lat").value;
    var lng = $("#lng").value;
    var waterCondition = $("#waterCondition").value;
    var waterType = $("#waterType").value;

    alert("Water Report Submitted");
    var path = firebase.database().ref().child("reports").push().key;
    var rootRef = firebase.database().ref();
    rootRef.child("reports").child(path).child("lat").set(lat);
    rootRef.child("reports").child(path).child("lng").set(lng);
    rootRef.child("reports").child(path).child("lat").set(lat);
}
