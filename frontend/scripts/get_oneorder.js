$("#order_card").hide();
$("#error_card").hide();
var baseUrl = "http://localhost:8080/";

var url = baseUrl+"api/orders/";

 var par=new URLSearchParams(window.location.search);
 orderid=par.get("orderid");
 url=url+orderid;

$(document).ready(function(){
    token=localStorage.getItem('token');
    $.ajax({
        type: "GET",
        url: url,
        headers: {
            "Authorization": token
        },
        success: function(data){
            $("#order_title").append(`<b>Order ID # ${data["order"].order_id}</b>`);
            $("#order_body").append(
                `<div class="row text-white">
                <div class="col-6 py-1">
                  <h5 class=" font-monospace ">Service : ${data["order"].service_name}</h5>
                </div>
                <div class="col-6 py-1">
                  <h5 class=" font-monospace ">Order Date : ${data["order"].timestamp}</h5>
                </div>
                <div class="col-6 py-1">
                  <h5 class=" font-monospace ">Total Amount :Rs. ${data["order"].amount}</h5>
                </div>
                <div class="col-6 py-1">
                  <h5 class=" font-monospace ">Status : ${data["order"].status}</h5>
                </div>
                <div class="col-6 py-1">
                  <h5 class=" font-monospace ">Latitude : ${data["order"].latitude}</h5>
                </div>
                <div class="col-6 py-1">
                  <h5 class=" font-monospace ">Longitude : ${data["order"].longitude}</h5>
                </div>
                <div class="col-6 py-1">
                  <h5 class=" font-monospace ">Provider : ${data["order"].provider_name}</h5>
                </div>
                <div class="col-6 py-1">
                  <button class="btn btn-success" onClick="openChat(${data["order"].provider_id})"><h5 class=" font-monospace ">Contact Provider</h5></button>
                </div>
              </div>`
                );
            $("#loading").hide();
            $("#order_card").show();
        },
        error: function(){
            $("#loading").hide();
            $("#error_card").show();
        }
    });
    
});

function openChat(id){
    location.href= location.href.split("#")[0].split("?")[0]+"?userid="+id+"#chat" 
    loadView();
};


