
var keycloak = Keycloak('keycloak.json');


var loadData = function () {
    document.getElementById('subject').innerHTML = keycloak.subject;
    if (keycloak.idToken) {
        document.getElementById('profileType').innerHTML = 'IDToken';
        document.getElementById('username').innerHTML = keycloak.idTokenParsed.preferred_username;
        document.getElementById('email').innerHTML = keycloak.idTokenParsed.email;
        document.getElementById('name').innerHTML = keycloak.idTokenParsed.name;
        document.getElementById('givenName').innerHTML = keycloak.idTokenParsed.given_name;
        document.getElementById('familyName').innerHTML = keycloak.idTokenParsed.family_name;
    } else {
        keycloak.loadUserProfile(function() {
            document.getElementById('profileType').innerHTML = 'Account Service';
            document.getElementById('username').innerHTML = keycloak.profile.username;
            document.getElementById('email').innerHTML = keycloak.profile.email;
            document.getElementById('name').innerHTML = keycloak.profile.firstName + ' ' + keycloak.profile.lastName;
            document.getElementById('givenName').innerHTML = keycloak.profile.firstName;
            document.getElementById('familyName').innerHTML = keycloak.profile.lastName;
        }, function() {
            document.getElementById('profileType').innerHTML = 'Failed to retrieve user details. Please enable claims or account role';
        });
    }
};

var loadFailure = function () {
    document.getElementById('customers').innerHTML = '<b>Failed to load data. Check console log</b>';
};

var reloadData = function () {
    keycloak.updateToken(10)
    .success(loadData)
    .error(function() {
        document.getElementById('customers').innerHTML = '<b>Failed to load data. User is logged out.</b>';
    });
};

var openAccountPage = function(){
   window.open(keycloak.createAccountUrl(),"_blank");
};

function greet(role){
    var options = {
        headers: {
            "Authorization": "Bearer " + keycloak.token
        }
    };

    var onSuccess = txt => document.getElementById('subject').innerHTML = txt;
    var onError = err => document.getElementById('subject').innerHTML = err;

    fetch(`http://localhost:9999/api/${role}/greet`, options).then(res => res.text().then(onSuccess)).catch(onError);
}

keycloak.init({ onLoad: 'login-required' }).success(reloadData);
