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
                        alert(errorMessage);
            }
    }).then(function() {
        firebase.auth().signInWithEmailAndPassword(user , pass).catch(function(error) {
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
                    var user1 = firebase.auth().currentUser;
                    valid = user1 != null;
                    if (valid) {
                      addCookie(user1, user, pass);
                    } else {
                      // failure
                    }
                    var key = Cookies.getJSON('user').uid
                    var data = {
                        "username": user,
                        "email": Cookies.getJSON('user').email,
                        "banned": false,
                        "address": "NO ADDRESS",
                        "phone": "PHONE",
                        "username": Cookies.getJSON('user').email,
                        "name": "newName"
                    };
                    alert(data);
                    firebase.database().ref('users/' + key).set(data).then(
                        function() {
                            lit();
                        });
                    
                });
    });
    

        

                    

}

function addCookie(obj, email, pword) {
    Cookies.set('user', obj, {path: "/", expires: 30});
    Cookies.set('email', email);
    Cookies.set('password', pword);
    alert("set");
}

function lit() {

        window.location.href = "viewwaterreports.html";
}



