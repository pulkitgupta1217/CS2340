checkSignedIn();

function submitWaterReport() {
    alert("43235");
    var newWaterReport = {
        creator: "Dan",
        dateTime: "2017-04-25 23:45:10",
        lat: 5,
        lng: 5,
        site: {
            lat: 5,
            lng: 5
        },
        reportID: 455,
        waterCondition: "TREATABLE_MUDDY",
        waterType: "WELL"
    }
    alert("json created");
    var newWaterReportKey = firebase.database().ref().child("reports").push().key;
    alert("added");
    firebase.database().ref('reports/' + newWaterReportKey).set(newWaterReport).then(
        function () {
            alert("Water Report Submitted");
        });
}
