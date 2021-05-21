var baseUrl = 'http://localhost:8080/';

var url = baseUrl + 'api/login';

var consumerUrl =
  'http://127.0.0.1:5500/frontend/templates/customerDashboard.html';
var providerUrl =
  'http://127.0.0.1:5500/frontend/templates/providerDashboard.html';

$(document).ready(function () {
  $('#loginBtn').click(function () {
    event.preventDefault();
    console.log($('#email').val());
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
        console.log(data);
        token = 'JWT ' + data.token;
        window.localStorage.setItem('token', token);
        console.log(token);
        if (data.isProvider) {
          window.location.assign(providerUrl);
        }
      },
      error: function (jq, status, message) {
        alert('Invalid user credential');
      },
    });
  });
});
