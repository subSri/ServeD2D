var baseUrl = 'http://localhost:8080/';

var url = baseUrl + 'api/register';

$(document).ready(function () {
  $('#signupBtn').click(function () {
    event.preventDefault();
    var isProvider = '1';
    var radioValue = $("input[name='optionsRadios']:checked").val();
    if (radioValue === 'Consumer') {
      isProvider = '0';
    }
    $.ajax({
      url: url,
      type: 'POST',
      data: JSON.stringify({
        name: $('#name').val(),
        email: $('#email').val(),
        password: $('#pswd').val(),
        walletBalance: '100',
        isProvider: isProvider,
      }),
      contentType: 'application/json; charset=utf-8',
      dataType: 'json',
      success: function (data, status) {
        navigateTo('login');
      },
      error: function (jq, status, message) {
        alert('Email Already in Use');
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
