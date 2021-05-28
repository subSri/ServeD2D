var baseUrl = 'http://localhost:8080/';
var url = baseUrl + 'api/orders';
var tokens = "";
var arrayOfLiveOrders = [];
window.onload = function () {
  var baseUrl = 'http://localhost:8080/';
  var url = baseUrl + 'api/orders';
  var tokens = localStorage.getItem('token').split(' ');
  var arrayOfLiveOrders = [];
  $('#name').append(`Hi, ${localStorage.getItem('name')}`);
  console.log(tokens[1]);
  $("#loading").show();
  $.ajax({
    type: 'GET',
    url: url.concat('/provider'),
    headers: {
      Authorization: 'Bearer '.concat(tokens[1]),
    },
    success: function (data) {
      var out1 = `<table class="table table-striped">
                <thead>
                  <tr>
                    <th>Order id</th>
                    <th>service id</th>
                    <th>Placed by</th>
                    <th>amount</th>
                    <th>Order Address</th>
                    <th>placed at</th>
                  </tr>
                </thead>
                <tbody>`;
      var out2 = out1;
      var out3 = out1;
      console.log('success');
      console.log(data);
      $("#loading").hide();

      for (i = 0; i < data.info.length; i++) {
        if (data.info[i].order.orderStatus == '0') {
          // console.log(data.info[i].order.orderId);
          out1 =
            out1 +
            `<tr id = "${data.info[i].order.orderId}">
                        <td>${data.info[i].order.orderId} </td>
                        <td>${data.info[i].order.serviceId} </td>
                        <td>${data.info[i].name_of_consumer}</td>
                        <td>Rs ${data.info[i].order.amount}</td>
                        <td>${data.info[i].address_of_consumer.lat}, ${data.info[i].address_of_consumer.longi}</td>
                        <td>${data.info[i].order.timestamp}</td>
                        <td> <button class="btn btn-info" id="data.info[i].order.orderId" onclick="ajaxPostAccept(${data.info[i].order.orderId})">
                        Accept </button></td>
                        </tr>`;
          arrayOfLiveOrders.push(data.info[i].order.orderId);
        } else if (data.info[i].order.orderStatus == '1') {
          console.log(data.info[i].order);
          out2 =
            out2 +
            `<tr>
                        <td>${data.info[i].order.orderId} </td>
                        <td>${data.info[i].order.serviceId} </td>
                        <td>${data.info[i].name_of_consumer}</td>
                        <td>Rs ${data.info[i].order.amount}</td>
                        <td>${data.info[i].address_of_consumer.lat}, ${data.info[i].address_of_consumer.longi}</td>
                        <td>${data.info[i].order.timestamp}</td>
                        <td> <button class="btn btn-warning" id="data.info[i].order.orderId" onclick="ajaxPostCancel(${data.info[i].order.orderId})">
                        Cancel </button></td>
                        </tr>`;
        } else if (data.info[i].order.orderStatus == '2') {
          console.log(data.info[i].order);
          out3 =
            out3 +
            `<tr>
                        <td>${data.info[i].order.orderId} </td>
                        <td>${data.info[i].order.serviceId} </td>
                        <td>${data.info[i].name_of_consumer}</td>
                        <td>Rs ${data.info[i].order.amount}</td>
                        <td>${data.info[i].address_of_consumer.lat}, ${data.info[i].address_of_consumer.longi}</td>
                        <td>${data.info[i].order.timestamp}</td>
                        </tr>`;
        }
      }
      out1 =
        out1 +
        `</tbody>
                 </table>`;

      out2 =
        out2 +
        `</tbody>
                 </table>`;

      out3 =
        out3 +
        `</tbody>
                 </table>`;

      $('#liveOrders').append(out1);

      $('#currentOrders').append(out2);

      $('#completedOrders').append(out3);
    },
  });
};

function ajaxPostAccept(id) {
  var path = '/accept/'.concat(id.toString());
  $.ajax({
    type: 'POST',
    url: url.concat(path),
    headers: {
      Authorization: 'Bearer '.concat(tokens[1]),
    },
    data: {},
    success: function (result) {
      alert('You have accepted order :' + id.toString());
    },
    error: function (result) {
      alert('try again!');
    },
  });
}

function ajaxPostCancel(id) {
  var path = '/cancel/'.concat(id.toString());
  $.ajax({
    type: 'POST',
    url: url.concat(path),
    headers: {
      Authorization: 'Bearer '.concat(tokens[1]),
    },
    data: {},
    success: function (result) {
      alert('You have cancelled order :' + id.toString());
    },
    error: function (result) {
      alert('try again!');
    },
  });
}
