function validateForm (form) {
                enteredPassword = form.password.value;
                enteredUsername = form.username.value;
                
                if (enteredUsername.indexOf("@") <= 0) {
                    enteredUsername += "@water.net";
                }
                firebase.auth().signInWithEmailAndPassword(enteredUsername , enteredPassword).catch(function(error) {
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
                    var user = firebase.auth().currentUser;
                    valid = user != null;

                    if (valid) {
                      addCookie(user);
                    } else {
                      // No user is signed in.
                    }
                    
                    window.location.href = "viewwaterreports.html";

                    
                }
                );
                return false;
            }

            function addCookie(obj) {
                Cookies.set('user', obj, {path: "/", expires: 30});
            }

            function isNotBanned(user) {
                var uid = user.uid;

                var ref = firebase.database().ref("users/" + uid);
                alert(ref);
                ref.once("value")
                  .then(function(snapshot) {
                    var banned = snapshot.child("banned").val(); // "last"
                    // alert(banned);
                    // var banned = snapshot.toJson().banned;
                    // var banned = 1 == snapshot.getInt("banned");
                    alert(banned);
                  });
                if (banned == false) {
                    return true;
                }

                return false;
            }


            function signout() {
              Cookies.remove("user", {path: '/'});
            }