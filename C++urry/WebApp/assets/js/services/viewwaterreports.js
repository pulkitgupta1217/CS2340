window.onload = populateWaterReports();

function populateWaterReports() {
	checkSignedIn();
    var rootRef = firebase.database().ref().child("reports");
    rootRef.on("child_added", snap => {
        var key = snap.getKey();
        var creator = snap.child("creator").val();
        var dateTime = snap.child("dateTime").val();
        var lat = snap.child("lat").val();
        var lng = snap.child("lng").val();
        var reportID = snap.child("reportID").val();
        var waterCondition = snap.child("waterCondition").val();
        var waterType = snap.child("waterType").val();
        $("#waterList").append("<tr><td>" + key + "</td><td>" + creator + "</td><td>" + dateTime + "</td><td>" + lat 
            + "</td><td>" + lng + "</td><td>" + reportID + "</td><td>" + waterCondition + "</td><td>" + waterType 
            + "</td></tr>");
    });
};
