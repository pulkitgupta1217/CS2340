function validateForm (form) {
    enteredPassword = form.password.value;
    enteredUsername = form.username.value;
    firebase.auth().signInWithEmailAndPassword(enteredUsername  + "@water.net", enteredPassword).catch(function(error) {
    // Handle Errors here.
    var errorCode = error.code;
    var errorMessage = error.message;
    // [START_EXCLUDE]
    if (errorCode === 'auth/wrong-password') {
        alert('Wrong password.');
    } else {
        alert(errorMessage);
    }
    console.log(error);
    document.getElementById('quickstart-sign-in').disabled = false;
    // [END_EXCLUDE]
    }).then(function() {
        addCookie(enteredUsername);
        window.location.href = "dashboard.html";
    });
        return false;
    }

function addCookie(obj) {
    Cookies.set('user', obj, {path: "/", expires: 30});
    // $.cookie("visits", 10);
    // alert($.cookie("visits"));
}