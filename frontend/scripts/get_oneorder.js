$("#order_card").hide();
$("#error_card").hide();
$("#submit_message").hide();
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
                  <h5 class=" font-monospace "  style="text-transform:capitalize;">Service : ${data["order"].service_name}</h5>
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
            $("#review").append(
              `<form onsubmit="event.preventDefault();return submitRating(${data["order"].user_id},${data["order"].service_id});"id="review_form"> 
                <div class="row">
                <div class="col-12 form-group">
                  <textarea class="form-control" id="comment" name="comment" placeholder="Rate your experience with this Service" rows="3"></textarea>  
                </div>
                <div class="col-10 my-1 form-group inline-block">
                  <div class="row">
                    <div class="col-2 text-end my-auto">
                      <h5 class="text-light">Rating</h5>
                    </div>
                    <div class="col-10 text-start">
                      <input type="radio" class="btn-check" name="rating" value="1" id="rating1">
                    <label class="btn btn-warning rounded border-1" for="rating1">1</label>
                    <input type="radio" class="btn-check" name="rating" value="2" id="rating2">
                    <label class="btn btn-warning rounded border-1" for="rating2">2</label>
                    <input type="radio" class="btn-check" name="rating" value="3" id="rating3">
                    <label class="btn btn-primary rounded border-1 " for="rating3">3</label>
                    <input type="radio" class="btn-check" name="rating" value="4" id="rating4">
                    <label class="btn btn-success rounded border-1 " for="rating4">4</label>
                    <input type="radio" class="btn-check" name="rating" value="5" id="rating5">
                    <label class="btn btn-success rounded border-1 " for="rating5">5</label>
                    </div>
                  </div>
                </div>
                <div class="col-2 my-1  text-end">
                <button type="submit" class="btn btn-success">Submit</button>  
                </div>
              </div>
              </form>`
              );
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

function submitRating(userid,serviceid){
  var review_url=baseUrl+"api/reviews"
  var form=document.getElementById("review_form")
  var rating=form.elements["rating"].value
  var comment=form.elements["comment"].value
  token=localStorage.getItem('token');
  $.ajax({
    url: review_url,
    type: 'POST',
    headers: {
      "Authorization": token
    },
    data: JSON.stringify({
      userId : userid,
      serviceId : serviceid,
      rating : rating,
      comment : comment,
    }),
    contentType: 'application/json; charset=utf-8',
    dataType: 'json',
    success: function (data) {
      $("#review").hide();
      $("#submit_message").show();
    },
    error: function () {
    },
  });
  return false;
};


