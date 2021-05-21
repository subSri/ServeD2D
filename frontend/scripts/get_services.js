var baseUrl = "http://localhost:8080/";

var url = baseUrl+"api/services?category="+getParameter('category');

$(document).ready(function(){
        $.get(url, function(data){
            // Display the returned data in browser
            console.log(data)
            $.each( data.services, function( service ) {
                $("#serviceBlockId").append(
                    `<div>
                        <div class="card">
                            <h5 class="card-title"  style="padding:1%;height:100%;width:100%">
                                <div style="padding:1%;float: left;height:200px;width:15%">
                                    <img src=${data.services[service]['imageUrl']} alt="No image" width="100" height="100">
                                </div>
                                <div style="padding:1%;;width:80%">
                                    <h3>${data.services[service]['name']}</h4><br> 
                                    ${data.services[service]['description']}<br> 
                                    Price- ${data.services[service]['price']}<br> 
                                    Ratings- ${data.services[service]['ratings']}<br> 
                                    <div style="margin:15px;">
                                        <button id="message_btn" class="btn btn-success">Message</button>
                                        <button id="book_btn" class="btn btn-success">Book</button>    
                                    </div>
                                </div>
                                

                            </h5>
                        </div>
                    </div>`
                );
            });
        });
});