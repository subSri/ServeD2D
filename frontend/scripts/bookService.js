function bookingService(){
    console.log('booking a service....')
    var baseUrl='http://localhost:8080/api/';

    var bookServiceUrl=baseUrl+'/orders';

    // var token=window.localStorage.getItem('token');


    var token='eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJTYXVyYXYgU2luZ2giLCJpZCI6MjEsImV4cCI6MTYyMjE4MDM0MywiaWF0IjoxNjIyMTIwMzQzfQ.aN5qKwKRTUI66S9FEsc-QAfIOALzUgzu7SAbK59lGZA'

   var payload= {
        "orderId" : 24,
        "userId" : 2,
        "serviceId" : 23,
        "adressId" : 1,
        "timestamp" : "2013-01-20",
        "orderStatus" : 2,
        "amount" : 23.43
    }

    $.ajax({
        method: 'POST',
        data: JSON.stringify(payload),
        url: bookServiceUrl,
        headers: {
            'Authorization': 'JWT ' + token,
            'Content-Type': 'application/json',
        },
    })
        .then((resp) => {
            alert("Service Booked Successfully....")
        })
        .catch((err) => {
            alert(err.responseText);
        });
    
}