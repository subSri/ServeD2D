var baseUrl = "http://localhost:8080/";

var url = baseUrl+"api/chats/all";

// var par=new URLSearchParams(window.location.search);
// console.log(par.get("userid"));


$(document).ready(function(){
    token=localStorage.getItem('token');
    $.ajax({
        type: "GET",
        url: url,
        headers: {
            "Authorization": token
        },
        success: function(data){
            console.log();
            if(Object.keys(data["messages"]).length>0){
                for(userid in data["messages"]){
                    //console.log(data["messages"][userid]);
                    $("#message_list").append(
                        `<div class="row py-2 chat" onclick="openChat(${data["messages"][userid].user_id})"  style="cursor: pointer;">
                        <div class="col-2 text-center mx-auto"><img class="" src="media/images/icons/default_profile.svg" alt="" width="60"></div>
                        <div class="col-10 mx-auto">
                            <div class="row">
                                <div class="col-8">
                                    <h2>${data["messages"][userid].name}</h2>
                                </div>
                                <div class="col-4  text-end">
                                    <h5>${data["messages"][userid].timestamp}</h5>
                                </div>
                            </div>
                            <div class="row">
                                <h5>${data["messages"][userid].content}</h5>
                            </div>
                        </div>
                      </div>
                        `
                        );
                }
                $(".chat").hover(function(){
                    $(this).css("background-color", "#1a404a");
                    }, function(){
                    $(this).css("background-color", "#002b36");
                  });
                
            }
            else{
                $("#message_list").append(
                    `<div class="row py-1 text-center">
                        <h3>No Messages Yet.</h3>
                    </div>`
                );
            }
            $("#loading").hide();
            
        },
        error: function(){
            $("#message_list").append(
                `<div class="row py-1 text-center">
                    <h3>Error Getting Messages.</h3>
                </div>`
            );
            $("#loading").hide();
        }
    });
    
    
});

function openChat(id){
    location.href= location.href.split("#")[0].split("?")[0]+"?userid="+id+"#Chat" 
    loadView();
};

