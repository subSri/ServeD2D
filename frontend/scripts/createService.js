var baseUrl = 'http://localhost:8080/';

var url = baseUrl + 'api/services';

$(document).ready(function () {
  $('#createSvc').click(function () {

    $.ajax({
      url: url,
      type: 'POST',
      headers: {
        Authorization: window.localStorage.getItem('token')
      },
      data: JSON.stringify({
        addressId: 2,
        isApproved: true,
        category: $('#category').val(),
        description: $('#description').val(),
        imageUrl: "http://dummyimage.com/122x100.png/ff4444/ffffff",
        serviceRadius: 10.0,
        price: $('#price').val(),
        ratingCount: 0,
        completedOrders: 0,
        name: $('#name').val(),
      }),
      contentType: 'application/json; charset=utf-8',
      dataType: 'json',
      success: function (data, status) {
        alert("Service created");
        navigateTo('provider');
      },
      error: function (jq, status, message) {
        alert('Service creation failed !');
      },
    });
  });
});
