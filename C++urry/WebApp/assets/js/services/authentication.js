function validateForm (form) {
                enteredPassword = form.password.value;
                enteredUsername = form.username.value;
                // if (enteredUsername == "brendon" && enteredPassword == "has a massive cok") {
                //     window.location.href = "dashboard.html";
                // } else {
                //     alert ("Wrong password");
                // }
                
                if (enteredUsername.indexOf("@") <= 0) {
                    enteredUsername += "@water.net";
                }
                alert(enteredUsername);
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


                    if (user) {
                      addCookie(user);
                    } else {
                      // No user is signed in.
                    }
                    
                    window.location.href = "dashboard.html";

                    
                }
                );
                return false;
            }

            function addCookie(obj) {
                Cookies.set('user', obj, {path: "/", expires: 30});
                // $.cookie("visits", 10);
                // alert($.cookie("visits"));
            }