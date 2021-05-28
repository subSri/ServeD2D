var baseUrl = "http://localhost:8080/";

var url = baseUrl+"api/services?category="+getParameter('category');

$(document).ready(function(){
        $.get(url, function(data){
            // Display the returned data in browser
            console.log(data)
            $.each( data.services, function( service ) {
                $("#serviceBlockId").append(
                    `<div style="margin:1%">
                        <div class="card">
                            <h5 class="card-title"  style="padding:1%;height:100%;width:100%">
                                <div>
                                    <div style="float:left;height:200px;width:150px;">
                                        <img src=${data.services[service]['imageUrl']} alt="No image" width="120" height="120">
                                    </div>
                                    <div style="height:200px">
                                        <h3>${data.services[service]['name']}</h4>
                                        <hr style="height:2px;">
                                        ${data.services[service]['description']}<br> 
                                        Price- ${data.services[service]['price']}<br> 
                                        Ratings- ${data.services[service]['ratings']}<br> 
                                    </div>
                                </div>
                                <div>
                                    <button id="message_btn" class="btn btn-success" style="margin-left:30px;width:100px">Message</button>
                                    <button id="book_btn" class="btn btn-success" onclick="openService('${data.services[service]['serviceId']}')" style="margin-left:30px;width:100px">Book</button>
                                </div>
                            </h5>
                        </div>
                    </div>`
                );
            });
        });
});

function openService(id){
    location.href= location.href.split("#")[0].split("?")[0]+"?serviceid="+id+"#service" 
    loadView();
};