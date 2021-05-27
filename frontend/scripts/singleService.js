$(document).ready(function(){

    var baseUrl='http://localhost:8080/api/';

    var price;
    
    var user='USER';




    $(window).on('scroll',function(){
        var scroll=$(window).scrollTop();
        // console.log(scroll);
        if(scroll>=50){
            $(".sticky").addClass("stickyadd");
        }
        else{
            $(".sticky").removeClass("stickyadd");
        }
    })


      // var token=
      var userOut=`<h3>${user}</h3>`;
      $('#user').html(userOut);
      

      var productsUrl=baseUrl+'services?category=consectetuer';

      $.ajax(productsUrl, {
        method: 'GET',
        data: {
            timestamp: Date.now(),
        },
        headers: {
            Authorization: '',      
        },
    })
        .then(function (service) {
            
            console.log(service);
            var obj=service.services;
            console.log(obj)
           
             var out = '<div class="row">';

            obj.forEach(function (service) {
                console.log(service)
                
                price=service.price;
                var priceOut=`<p>₹ ${price}</p>`;
                $('#rate').html(priceOut);
                var status;

                if(service.isApproved){
                    status='Approved';
                }else{
                    status='Not Approved';
                }
                out+=`<div class="col-lg-12 f-p">
                <div class="card">

                <div class="car-body">
                <h5 class="card-title service-bottom">${service.category}</h5>
                <br>
                <p class="card-text">D2D : ${status} | Ratings : ${service.ratings}⭐</p>
                <br>
                <p class="card-text">${service.description}</p>
                <p class="card-text">Provider : ${service.name} | Successful Orders: ${service.completedOrders}</p>
                <p class="card-text">₹ ${service.price}</p>
                <a href="#"  class="btn btn-dark book-now f-bt" onClick="bookingService()">BOOK NOW</a>
                </div>
                </div>
                </div>`;
            });
                out += `<div class="col-4 card">`

            out += '</div>';

            $('#view').html(out);
        })
        .catch(function (err) {
            console.error(err);
        });


        

    var typed=new Typed(".element",{
        strings:["Iqbal 4.7⭐","Ramesh 4.9⭐","John 4.8⭐"],
        smartBackspace:true,
        typeSpeed:100,
        backSpeed:100,
        loop:true,
        loopCount:Infinity,
        startDelay:1000
    })

})



