var newUsername = document.getElementById('newUser');
var newPassword = document.getElementById('newPass');
var registerSubmit = document.getElementById('newReg');
var userType = document.getElementById('newUserType');

function registerClick() {
    var user = newUsername.value;
    var pass = newPassword.value;
    var userType = newUserType.value;
    if (user.indexOf("@") <= 0) {
                    user += "@water.net";
    }

    firebase.auth().createUserWithEmailAndPassword(user, pass).catch(function(error) {
    // Handle Errors here.
        var errorCode = error.code;
        var errorMessage = error.message;
        if (errorCode == 'auth/email-already-in-use') {
                        alert('Email already in use.');
                } else {
                        alert('Successfully registered.');
            }
    }).then(function() {
        window.location.href = "index.html";
    });
    return false;

}

