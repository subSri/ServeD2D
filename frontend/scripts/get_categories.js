var baseUrl = "http://localhost:8080/";

var url = baseUrl + "api/services/categories"

$(document).ready(function () {
    $('#getCat').click(function () {
        event.preventDefault();
        $("#loading").show();

        $.get(url)
            .done(function (data) {
                // Display the returned data in browser

                $("#server_msg").html("Services We Offer");

                $.each(data.categories, function (cat) {
                    $("#categories").append(
                        `<div class="col-sm-12 col-md-3 mb-3">
                    <div class="card">
                    <div class="card-body text-center"  style="text-transform:capitalize;">
                        <h5 class="card-title">${data.categories[cat]} </h5>
                        <button class="btn btn-success" onclick="openCategory('${data.categories[cat]}')">Explore</button>
                    </div>
                    </div>
                </div>`
                    );


                });
                $("#loading").hide();
            })
            .fail(function () {

                $("#server_msg").html("Alas! Server Down !!");

                $("#loading").hide();

            });
    });
});

function openCategory(category) {
    location.href = location.href.split("#")[0].split("?")[0] + "?category=" + category + "#servicelist"
    loadView();
};