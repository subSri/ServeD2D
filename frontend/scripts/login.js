var baseUrl = 'http://localhost:8080/';

var url = baseUrl + 'api/login';

$(document).ready(function () {
  redirectFromLogin(); // Redirecting a page if user is logged in
  $('#loginBtn').click(function () {
    event.preventDefault();
    $.ajax({
      url: url,
      type: 'POST',
      data: JSON.stringify({
        email: $('#email').val(),
        password: $('#pswd').val(),
      }),
      contentType: 'application/json; charset=utf-8',
      dataType: 'json',
      success: function (data, status) {
        token = 'JWT ' + data.token;
        window.localStorage.setItem('token', token);
        window.localStorage.setItem('name', data.name);
        window.localStorage.setItem('provider', data.isProvider);
        redirectFromLogin();
      },
      error: function (jq, status, message) {
        alert('Invalid user credential');
      },
    });
  });
});

function redirectFromLogin() {
  if (window.localStorage.getItem('token')) {
    if (window.localStorage.getItem('provider') === 'true') {
      navigateTo('provider');
    } else {
      navigateTo('consumer');
    }
  }
}
