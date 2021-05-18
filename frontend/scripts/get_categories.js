var baseUrl = "http://localhost:8080/";

var url = baseUrl+"api/services/categories"

$(document).ready(function(){
        
  

    $("#loading").show();

    $.get(url)
    .done(function(data){
        // Display the returned data in browser

        $("#server_msg").html("Services We Offer");

        $.each( data.categories, function( cat ) {
            $("#categories").append(
                `<div class="col-sm-12 col-md-3 mb-3">
                    <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">${data.categories[cat]} </h5>
                        <a href="#" class="btn btn-success">Explore</a>
                    </div>
                    </div>
                </div>`
            );

            
        });
        $("#loading").hide();
    })
    .fail(function(){

        $("#server_msg").html("Alas! Server Down !!");

        $("#categories").append(
            `<img src="../media/images/icons/serverDown.svg" height="60%" class="mt-5">`
        );
        $("#loading").hide();

    });
       
           
            
    
       
});