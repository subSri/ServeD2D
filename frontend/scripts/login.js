var baseUrl = 'http://localhost:8080/';

var url = baseUrl + 'api/login';

$(document).ready(function () {
  $('#loginBtn').click(function () {
    event.preventDefault();
    console.log($('#email').val());
    $.post(
      url,
      {
        email: $('#email').val(),
        password: $('#pswd').val(),
      },
      function (data, status) {
        alert('Data: ' + data + '\nStatus: ' + status);
        token = 'JWT ' + data;
        window.localStorage.setItem('token', token);
      }
    );
  });
});
