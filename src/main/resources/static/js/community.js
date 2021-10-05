
function post(){
    var questionId = $("#question_id").val();
    var comment = $("#comment_content").val();

    if(!comment.trim().length){
        alert("Cannot be empty");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parent_id": questionId,
            "comment": comment,
            "type": 1 //TODO: choose type
        }),
        success: function (response) {
            if(response.code==100){
                $("#comment_section").hide();
            }
            else if(response.code==1000){
                var accept = confirm(response.message);
                if(accept){
                    window.open("https://github.com/login/oauth/authorize?client_id=f638ad8ff2bd233d2ea6&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                    //window.localStorage.setItem("closable", true);
                }
            }
            else {
                alert(response.message);
            }

        },
        dataType: "json"
    });
}