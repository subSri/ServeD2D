
headerParams={"Authorization":"Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJQaHlsbHlzIFNvd3RlcmUiLCJpZCI6MSwiZXhwIjoxNjIxNDk3MjE3LCJpYXQiOjE2MjE0MzcyMTd9.dC-HKtntF2kVLgr2Nxtbz9OZbgisplZ9Bx8nkz0RM04"}

$(document).ready(function () {
    

    $("#loading").hide();

    $("#orderButton").click(function (e) {
        $.ajax({
            method: 'GET',
            url:  "http://localhost:8080/api/orders",
            headers: headerParams,
            success:function(data){
                var output = "<h3>Hello Madhur</h3>";
                console.log("Hello");
                document.body = output;
            }
        });
    });

    $("#walletButton").click(function (e) {
        $.ajax({
            method: 'GET',
            url:  "http://localhost:8080/api/wallet/balance",
            headers: headerParams,
            success:function(data){
                var output = "<h3>Hello Madhur</h3>";
                console.log("Hello");
                document.body = output;
            }
        });
    });

    $("#addressesButton").click(function (e) {
        $.ajax({
            method: 'GET',
            url:  "http://localhost:8080/api/address",
            headers: headerParams,
            success:function(data){
                var output = "<h3>Hello Madhur</h3>";
                console.log("Hello");
                document.body = output;
            }

            
        });
    });

    $("#chatButton").click(function (e) {
        $.ajax({
            method: 'GET',
            url:  "http://localhost:8080/api/chats/all",
            headers: headerParams,
            success:function(data){
                var output = "<h3>Hello Madhur</h3>";
                console.log("Hello");
                document.body = output;
            }
        });
    });

    $("#reviewsButton").click(function (e) {
        $("#loading").show();
        $.ajax({
            method: 'GET',
            url:  "http://localhost:8080/api/reviews",
            headers: headerParams,
            success:function(data){
                
                console.log(data);
                $.each( data.reviews, function( rev ) {
                    $("#resultArea").append(
                        `<div class="col-lg-2 col-md-4 col-sm-4 col-xs-12">
                            <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">ReviewID : ${this.reviewId} </h5>
                                <h5 class="card-title">Rating : ${this.rating} </h5>
                                <p class="card-title">${this.comment} </p>
                            </div>
                            </div>
                        </div>`
                    );
        
                    
                });
                $("#loading").hide();
                $("resultPara").html = data;
                
            }
        });
    });
});
  
