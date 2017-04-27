window.onload = populateAccountReports();

function populateAccountReports() {
    checkSignedIn();
    var rootRef = firebase.database().ref().child("users");
    rootRef.on("child_added", snap => {
        var key = snap.getKey();
        var address = snap.child("address").val();
        var banned = snap.child("banned").val();
        var email = snap.child("email").val();
        var name = snap.child("name").val();
        var phone = snap.child("phone").val();
        var userID = snap.child("userID").val();
        var userType = snap.child("userType").val();
        var username = snap.child("username").val();
        $("#waterList").append("<tr><td>" + key + "</td><td>" + address + "</td><td>" + banned + "</td><td>" 
            + email + "</td><td>" + name + "</td><td>" + phone + "</td><td>" + userID + "</td><td>" 
            + userType + "</td><td>" + username + "</td></tr>");
    });
};