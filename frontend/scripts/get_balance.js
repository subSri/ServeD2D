token = window.localStorage.getItem('token');
headerParams = { Authorization: token };

function backFromWallet() {
  if (window.localStorage.getItem('token')) {
    if (window.localStorage.getItem('provider') === 'true') {
      navigateTo('provider');
    } else {
      navigateTo('consumer');
    }
  }
}

$(function () {
  $('#loading').show();
  $('#name').append(localStorage.getItem('name'));
  $.ajax({
    method: 'GET',
    url: 'http://localhost:8080/api/wallet/balance',
    headers: headerParams,
    success: function (data) {
      $('span.total-balance').text(data.balance);
    },
  });
  /*
    function getInputNumb(idName){
        const amount = document.getElementById(idName).value;
        const amountNumber = parseFloat(amount);
        return amountNumber;
    }

    const deposit_btn = document.getElementById('deposit-btn');
    deposit_btn.addEventListener('click', function(){

        const depositAmount = getInputNumb("amount");

        $.ajax({
            method: 'POST',
            url:  "http://localhost:8080/api/wallet/balance/add",
            headers: {"Authorization":"Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJQaHlsbHlzIFNvd3RlcmUiLCJpZCI6MSwiZXhwIjoxNjIxMzg0Njk0LCJpYXQiOjE2MjEzMjQ2OTR9.4GKOhGuKjLX1MFLbHP9maqM1Y8yMTryCrUbWdZEf0-o",
        "Amount":depositAmount},
            success:function(data){
                $("span.total-balance").text(data.balance);
            }
        });
        document.getElementById('amount').value = "";

    })

    const withdraw_btn = document.getElementById('withdraw-btn');
         withdraw_btn.addEventListener('click', function(){
            const withdrawNumb = getInputNumb("amount");
            $.ajax({
                method: 'POST',
                url:  "http://localhost:8080/api/wallet/balance/withdraw",
                headers: {"Authorization":"Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJQaHlsbHlzIFNvd3RlcmUiLCJpZCI6MSwiZXhwIjoxNjIxMzg0Njk0LCJpYXQiOjE2MjEzMjQ2OTR9.4GKOhGuKjLX1MFLbHP9maqM1Y8yMTryCrUbWdZEf0-o",
                "Amount":withdrawNumb},
                success:function(data){
                    $("span.total-balance").text(data.balance);
                }
            });
            document.getElementById('amount').value = "";
        })
    */

  $.ajax({
    method: 'GET',
    url: 'http://localhost:8080/api/orders',
    headers: headerParams,
    success: function (data) {
      var count = 5;
      var output = '<h3> Last 5 Transactions</h3><br><br>';

      for (var i = data.orders.length - 1; i >= 0; i--) {
        if (count <= 0) break;
        var card = data.orders[i];
        var type = 'debit';
        if (card.amount > 0) type = 'credit';

        var price = '0' + card.amount.toString();
        console.log(i, price, typeof price);
        output += "<div class='transaction-item " + type + "'>";
        output += "<div class='transaction-item_details'>";
        output += '<h3>' + card.timestamp + '</h3>';
        output += '</div>';
        output +=
          "<div class='transaction-item_amount'><span>₹ &nbsp;</span><p class='amount'>" +
          price +
          '</p></div>';
        output += '</div>';
        document.querySelector('.transactions').innerHTML = output;
        count--;
      }
    },
  });
});
