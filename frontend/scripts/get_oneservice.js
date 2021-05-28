$("#service_card").hide();
$("#error_card").hide();
$("#submit_message").hide();
var baseUrl = "http://localhost:8080/";

var url = baseUrl + "api/services/";

var par = new URLSearchParams(window.location.search);
orderid = par.get("serviceid");
url = url + orderid;

function placeOrder(service) {
  event.preventDefault();
  console.log("clicked booking");
}

$(document).ready(function () {
  token = localStorage.getItem('token');
  $.ajax({
    type: "GET",
    url: url,
    headers: {
      "Authorization": token
    },
    success: function (data) {
      console.log(data);
      $("#service_title").append(`<img class="mx-4" src=${data["service"].imageUrl} alt="No image" width="80" height="80"><b  style="text-transform:capitalize;">${data["service"].name}</b>`);
      $("#service_body").append(
        `<div class="row text-white">
                <div class="col-6 py-1">
                  <h5 class="  "  style="text-transform:capitalize;"><b>Category : </b>${data["service"].category}</h5>
                </div>
                <div class="col-6 py-1">
                  <h5 class="  "><b>Price :</b>Rs. ${data["service"].price}</h5>
                </div>
                <div class="col-6 py-1">
                  <h5 class="  "><b>Service Radius :</b> ${data["service"].serviceRadius} km</h5>
                </div>
                <div class="col-6 py-1">
                  <h5 class="  "><b>Number of Ratings :</b> ${data["service"].ratingCount}</h5>
                </div>
                <div class="col-6 py-1">
                  <h5 class=" "><b>Completed Orders : </b>${data["service"].completedOrders}</h5>
                </div>
                <div class="col-12 py-1">
                  <h5 class=" "><b>Description :</b> ${data["service"].description}</h5>
                </div>
                <div class="col-6 my-2">
                  <button class="btn btn-primary" onClick="openChat(${data["service"].providerId})"><h5 class="">Contact Provider</h5></button>
                </div>
                <div class="col-6 my-2">
                  <button class="btn btn-success" onClick="placeOrder()"><h5 class="">Book Now</h5></button>
                </div>
              </div>`
      );
      $("#loading").hide();
      $("#service_card").show();
      $.get(`./templates/review.html`)
        .then((content) => $('#review_section').html(content))
        .catch(console.error);

    },
    error: function () {
      $("#loading").hide();
      $("#error_card").show();
    }
  });

});

function openChat(id) {
  location.href = location.href.split("#")[0].split("?")[0] + "?userid=" + id + "#chat"
  loadView();
};




