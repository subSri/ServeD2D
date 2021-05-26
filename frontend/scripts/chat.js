var base_url = "http://localhost:8080";
var msg;

$("form").on("submit", function(event) {
    event.preventDefault();

    msg = $(this).serializeArray()[0]["value"];

    $.ajax({
        url: base_url + "/api/chats",
        type: "POST",
        contentType: "application/json; charset=utf-8",
        headers: {
            Authorization: "JWT " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJTYXVyYXYgU2luZ2giLCJpZCI6MjEsImV4cCI6MTYyMjA1NTI2NywiaWF0IjoxNjIxOTk1MjY3fQ.25DF4npWKQzf9F2aYT-bPAnQ_7uY2ShHb5AwXCwcpKU"
        },
        data: JSON.stringify({
            "senderId": "21",
            "receiverId": "22",
            "content": msg,
            "timestamp": new Date(),
        }),
        dataType: "json",
        success: function (data, statusCode, jqXHR) {
            console.log(data);
        },
        error: function (jqXHR, statusCode, errorThrown) {

        }
    });
});

setInterval(function() {

    var chatContainer = $(".chat-container");
    var content;
    var receiveBody;
    var sendBody;

    $.ajax({
        url: base_url + "/api/chats",
        type: "GET",
        headers: {
            Authorization: "JWT " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJTYXVyYXYgU2luZ2giLCJpZCI6MjEsImV4cCI6MTYyMjA1NTI2NywiaWF0IjoxNjIxOTk1MjY3fQ.25DF4npWKQzf9F2aYT-bPAnQ_7uY2ShHb5AwXCwcpKU"
        },
        contentType: "application/json",
        data: {
            "id": "22"
        },
        dataType: "json",
        success: function (data, statusCode, jqXHR) {
            chatContainer.empty();

            data.messages.forEach((elem) => {
                content = elem["content"];
                receiveBody = `<div style="align-self: flex-start; max-width: 75%">
                <div class="card text-white bg-dark border-success mb-3" style="display: inline-block">
                    <div class="card-body">
                        <div class="card-text">${ content }</div>
                    </div>
                </div>
                </div>`;
                sendBody = `<div style="align-self: flex-end; max-width: 75%;">
                <div class="card bg-light mb-3" style="display: inline-block">
                    <div class="card-body">
                        <div class="card-text">${ content }</div>
                    </div>
                </div>
                </div>`;

                if (elem["senderId"] === data["user_id"]) {
                    chatContainer.append(sendBody);
                } else {
                    chatContainer.append(receiveBody);
                }
            });
        },
    });
}, 1000);