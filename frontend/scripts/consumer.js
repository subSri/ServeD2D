
headerParams = { "Authorization": "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJQaHlsbHlzIFNvd3RlcmUiLCJpZCI6MSwiZXhwIjoxNjIxNDk3MjE3LCJpYXQiOjE2MjE0MzcyMTd9.dC-HKtntF2kVLgr2Nxtbz9OZbgisplZ9Bx8nkz0RM04" }

$(document).ready(function () {
    checkForLoggedInStatus();
    $("#loading").hide();
    $("#welcome_head").append(`<h1>Welcome,${localStorage.getItem('name')}</h1>`);
});

