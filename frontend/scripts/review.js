var baseUrl = "http://localhost:8080/";

var url = baseUrl+"api/reviews/";
var par=new URLSearchParams(window.location.search);
serviceid=par.get("serviceid");
url=url+serviceid;

$(document).ready(function(){
    token=localStorage.getItem('token');
    $.ajax({
        type: "GET",
        url: url,
        headers: {
            "Authorization": token
        },
        success: function(data){
            console.log(data);
            var avg_rating=0;
            if(data["review"].length>0){
                for (i=0;i<data["review"].length;i++){
                    avg_rating+=parseInt(data["review"][i].rating);
                    $("#review_list").append(
                        `<div class="row">
                        <div class="col-1 text-center mx-auto"><img class="" src="media/images/icons/default_profile.svg" alt="" width="45"></div>
                        <div class="col-9 text-start text-light"><h4>${data["review"][i].name}</h4></div>
                        <div class="col-2 text-center text-success">
                        <div class=" h2 d-inline"><strong>${data["review"][i].rating}</strong></div><div class="h5 d-inline text-success"> / 5</div>
                        </div>
                        
                        <div class="col-1"></div>
                        <div class="col-11 text-start">
                          <p>${data["review"][i].comment }</p>
                        </div>
                      </div><hr>`
                        );
                }
                console.log(avg_rating);
                avg_rating=avg_rating/data["review"].length;
                avg_rating=avg_rating.toFixed(1);
                $("#avg_rating").append(`<div class="d-inline h4 ">Average Rating: <div class="h2 d-inline text-info"><strong>${avg_rating}</strong></div><div class="h5 d-inline text-info"> /5</div></div>`);
            }
            else{
                $("#review_list").append(
                    `<div class="row py-1 text-center">
                        <h3>No Reviews Yet.</h3>
                    </div>`
                    );
            }
            
            $("#rloading").hide();
        },
        error: function(){
            $("#review_list").append(
                `<div class="row py-1 text-center">
                    <h3>Error retrieving reviews.</h3>
                </div>`
                );
            $("#rloading").hide();
        }
    });
    
    
});
