/********************
 * Login Script
 ********************/
let login = {
    execute : function() {
        $.ajax({
            url:"/login",
            type :  "POST",
            dataType : "json",
            data : {
                email : $("#email").val(),
                password : $("#password").val(),
                _csrf : $("#_csrf").val()
            },
            beforeSend : function(xhr) {
                //이거 안하면 403 error
                //데이터를 전송하기 전에 헤더에 csrf값을 설정한다
                let $token = $("#_csrf");
                xhr.setRequestHeader($token.data("token-name"), $token.val());
            },
            success : function(response) {
                if(response.code == "200"){
                    // 정상 처리 된 경우
                    window.location = response.item.url;	//이전페이지로 돌아가기
                } else {
                    alert(response.message);
                }
            },
            error : function(a,b,c){
                console.log(a,b,c);
            }
        })
    }
}
    $("#login-button").click(function() {
        login.execute();
    });



