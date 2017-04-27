window.onload = populatePurityReports();

function populatePurityReports() {
    checkSignedIn();
    var rootRef = firebase.database().ref().child("purity_reports");
    rootRef.on("child_added", snap => {
        var key = snap.getKey();
        var creator = snap.child("creator").val();
        var dateTime = snap.child("dateTime").val();
        var lat = snap.child("lat").val();
        var lng = snap.child("lng").val();
        var purityReportID = snap.child("purityReportID").val();
        var overallCondition = snap.child("overallCondition").val();
        var contaminantPPM = snap.child("contaminant").child("ppm").val();
        var virusPPM = snap.child("virus").child("ppm").val();
        $("#waterList").append("<tr><td>" + key + "</td><td>" + creator + "</td><td>" + dateTime + "</td><td>" + lat 
            + "</td><td>" + lng + "</td><td>" + purityReportID + "</td><td>" + overallCondition + "</td><td>" 
            + contaminantPPM + "</td><td>" + virusPPM + "</td></tr>");
    });
};
