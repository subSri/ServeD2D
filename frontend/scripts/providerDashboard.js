var baseUrl = "http://localhost:8080/";
var url = baseUrl+"api/orders";
window.onload = function() {
    what();
    function what(){
       
        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/api/orders/provider',
            headers: {
                "Authorization": 'Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJFd2VsbCBDYWxsYWdoYW4iLCJpZCI6MiwiZXhwIjoxNjIxNTAyMjEzLCJpYXQiOjE2MjE0NDIyMTN9.X0doDvRrGO2A_eDngg3qrITdrpcDENF_q6qlWswowao'
              },
            success: function(data) {
                var out1 =  `<table class="table table-striped">
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
                var out2 =  out1;
                var out3 =  out1;
                console.log('success');
                console.log(data);
                $("#name").append(data.user_name);
                for (i = 0; i < data.info.length; i++) {
                    if( data.info[i].order.orderStatus=="0"){
                        console.log(data.info[i].order);
                        out1 = out1+`<tr>
                        <td>${data.info[i].order.orderId} </td>
                        <td>${data.info[i].order.serviceId} </td>
                        <td>${data.info[i].name_of_consumer}</td>
                        <td>Rs ${data.info[i].order.amount}</td>
                        <td>${data.info[i].address_of_consumer.lat}, ${data.info[i].address_of_consumer.longi}</td>
                        <td>${data.info[i].order.timestamp}</td>
                        <td> <button id="button1">
                        Accept </button></td>
                        </tr>`;
                    }
                    else if(data.info[i].order.orderStatus=="1")
                    {
                        console.log(data.info[i].order);
                        out2 = out2+`<tr>
                        <td>${data.info[i].order.orderId} </td>
                        <td>${data.info[i].order.serviceId} </td>
                        <td>${data.info[i].name_of_consumer}</td>
                        <td>Rs ${data.info[i].order.amount}</td>
                        <td>${data.info[i].address_of_consumer.lat}, ${data.info[i].address_of_consumer.longi}</td>
                        <td>${data.info[i].order.timestamp}</td>
                        <td> <button id="button1">
                        Message </button></td>
                        <td> <button id="button1">
                        Cancel </button></td>
                        </tr>`;
                    }
                    else if(data.info[i].order.orderStatus=="2")
                    {
                        console.log(data.info[i].order);
                        out3 = out3+`<tr>
                        <td>${data.info[i].order.orderId} </td>
                        <td>${data.info[i].order.serviceId} </td>
                        <td>${data.info[i].name_of_consumer}</td>
                        <td>Rs ${data.info[i].order.amount}</td>
                        <td>${data.info[i].address_of_consumer.lat}, ${data.info[i].address_of_consumer.longi}</td>
                        <td>${data.info[i].order.timestamp}</td>
                        </tr>`;
                    }
                 }
                 out1 = out1+`</tbody>
                 </table>`;

                 out2 = out2+`</tbody>
                 </table>`;

                 out3 = out3+`</tbody>
                 </table>`;
                 
                 $("#liveOrders").append(out1);

                 $("#currentOrders").append(out2);

                 $("#completedOrders").append(out3);

                
            }
        });
    };
}
