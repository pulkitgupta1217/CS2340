window.onload = manageTabs();

function manageTabs() {
	checkSignedIn();
	// var x = Cookies.get('user');
	// alert(typeof(x));
	// var str = x.uid;
	// alert(str);
	var ref = firebase.database().ref('users/' + Cookies.getJSON('user').uid);
	var uType;
	ref.once("value").then(function(snapshot) {
    	uType = snapshot.child("userType").val(); // "last"
		if (uType == null) {
			uType = "USER";
		}
		hide(uType);
  	});
}

function hide(uType) {
	//user: view water report


	//admin: view everything
	//manager: not view accounts
	//
	if (uType == "USER") {
		hideTab("viewGraph");
		hideTab("viewAccounts");
		hideTab("createPurityReport");
		hideTab("viewPurityReports");
		hideTab("viewSecurityLog");
	} else if (uType == "WORKER") {
		hideTab("viewAccounts");
		hideTab("viewGraph");
		hideTab("viewSecurityLog");
	} else if (uType == "MANAGER") {
		hideTab("viewAccounts");
		hideTab("viewSecurityLog");
	}
}

function hideTab(tabID) {
	document.getElementById(tabID).setAttribute("style", "display: none;");
}