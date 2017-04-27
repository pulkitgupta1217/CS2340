checkSignedIn();

function submitPurityReport() {
    var creator = Cookies.getJSON('user').email;
    creator = creator.substring(0, creator.indexOf("@"));
    var dateTime = new Date().getTime();
    var lat = parseInt(document.getElementById("lat").value);
    var lng = parseInt(document.getElementById("lng").value);
    var contaminant = parseInt(document.getElementById("contaminant").value);
    var virus = parseInt(document.getElementById("virus").value);
    var reportID = Math.floor(Math.random() * 10000);
    var overallCondition = document.getElementById("waterCondition").value;
    var newWaterReport = {
        contaminant: {
            ppm: contaminant
        },
        creator: creator,
        dateTime: dateTime,
        lat: lat,
        lng: lng,
        overallCondition: overallCondition,
        purityReportID: reportID,
        site: {
            lat: lat,
            lng: lng
        },
        virus: {
            ppm: virus
        }
    }
    var newWaterReportKey = firebase.database().ref().child("purity_reports").push().key;
    firebase.database().ref('purity_reports/' + newWaterReportKey).set(newWaterReport).then(
        function () {
            alert("Purity Report Submitted");
        });
}