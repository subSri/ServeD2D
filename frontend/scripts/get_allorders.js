var baseUrl = "http://localhost:8080/";

var url = baseUrl+"api/orders";

// var par=new URLSearchParams(window.location.search);
// console.log(par.get("userid"));


$(document).ready(function(){
    token=localStorage.getItem('token');
    $.ajax({
        type: "GET",
        url: url,
        headers: {
            "Authorization": token
        },
        success: function(data){

            if(data["orders"].length>0){
                for (i=0; i<data["orders"].length; i++){
                    
                    switch(data["orders"][i].status){
                        case "REQUESTED":
                            status_color="text-primary";
                            break;
                        case "CONFIRMED":
                            status_color="text-info";
                            break;
                        case "COMPLETED":
                            status_color="text-success";
                            break;
                        case "CANCELLED":
                            status_color="text-danger";
                            break;
                        case "REJECTED":
                            status_color="text-danger";
                            break;

                    }
                    $("#order_list").append(
                                    `<div class="row py-2 order" onClick="openOrder(${data["orders"][i].order_id})" style="cursor: pointer;">
                                    <div class="col-1 text-center mx-auto"><img class="" src="media/images/icons/order.svg" alt="" width="60"></div>
                                    <div class="col-11 mx-auto">
                                      <div class="row mx-0 px-0">
                                        <div class="col-12" style="padding: 0;margin: 0;">
                                          <h3 class="text-light font-monospace "><b>Order ID # ${data["orders"][i].order_id}</b></h3>
                                        </div>
                                        <div class="col-6" style="padding: 0;margin: 0;">
                                          <h5 class=" ">Service : ${data["orders"][i].service_name}</h5>
                                        </div>
                                        <div class="col-6" style="padding: 0;margin: 0;">
                                          <h5 class="">Date : ${data["orders"][i].timestamp}</h5>
                                        </div>
                                        <div class="col-6" style="padding: 0;margin: 0;">
                                          <h5 class="">Order Amount : Rs. ${data["orders"][i].amount}</h5>
                                        </div>
                                        <div class="col-6" style="padding: 0;margin: 0;">
                                          <h5 class="${status_color}">Status : ${data["orders"][i].status}</h5>
                                        </div>
                                      </div>
                                    </div>
                                  </div><hr>`
                                );
                }
                $(".order").hover(function(){
                    $(this).css("background-color", "#1a404a");
                    }, function(){
                    $(this).css("background-color", "#002b36");
                  });
                
            }
            else{
                $("#order_list").append(
                    `<div class="row py-1 text-center">
                        <h3>No Orders Placed Yet.</h3>
                    </div>`
                );
            }  
            $("#loading").hide();
            
        },
        error: function(){
            $("#order_list").append(
                `<div class="row py-1 text-center">
                    <h3 >Error Getting Order List.</h3>
                </div>`
            );
            $("#loading").hide();
        }
    });
    
    
});

function openOrder(id){
    location.href= location.href.split("#")[0].split("?")[0]+"?orderid="+id+"#order" 
    loadView();
};

