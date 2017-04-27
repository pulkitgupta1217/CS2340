checkSignedIn();

function submitWaterReport() {
    var creator = Cookies.getJSON('user').email;
    creator = creator.substring(0, creator.indexOf("@"));
    var dateTime = new Date().getTime();
    var lat = parseInt(document.getElementById("lat").value);
    var lng = parseInt(document.getElementById("lng").value);
    var reportID = Math.floor(Math.random() * 10000);
    var waterCondition = document.getElementById("waterCondition").value;
    var waterType = document.getElementById("waterType").value;
    var newWaterReport = {
        creator: creator,
        dateTime: dateTime,
        lat: lat,
        lng: lng,
        site: {
            lat: lat,
            lng: lng
        },
        reportID: reportID,
        waterCondition: waterCondition,
        waterType: waterType
    }
    var newWaterReportKey = firebase.database().ref().child("reports").push().key;
    firebase.database().ref('reports/' + newWaterReportKey).set(newWaterReport).then(
        function () {
            alert("Water Report Submitted");
        });
}
