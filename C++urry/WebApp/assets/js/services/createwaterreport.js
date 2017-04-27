checkSignedIn();

function submitWaterReport() {
    var creator = Cookies.getJSON('user').uid;
    var lat = $("#lat").value;
    var lng = $("#lng").value;
    var waterCondition = $("#waterCondition").value;
    var waterType = $("#waterType").value;
    
    var newWaterReport = {
        creator: creator,
        dateTime: "2017-04-25 23:45:10",
        lat: lat,
        lng: lng,
        site: {
            lat: lat,
            lng: lng
        },
        reportID: 455,
        waterCondition: waterCondition,
        waterType: waterType
    }
    var newWaterReportKey = firebase.database().ref().child("reports").push().key;
    firebase.database().ref('reports/' + newWaterReportKey).set(newWaterReport).then(
        function () {
            alert("Water Report Submitted");
        });
}
