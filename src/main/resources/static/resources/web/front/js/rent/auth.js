/********************
 * Login Script
 ********************/
let login = {
    init : function() {
      this.create();
    },
    create : function() {
        $(document).on("click", "#login-button", function() {
            login.execute();
        });
    },
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
/*            beforeSend : function(xhr) {
                //이거 안하면 403 error
                //데이터를 전송하기 전에 헤더에 csrf값을 설정한다
                let $token = $("#_csrf");
                xhr.setRequestHeader($token.data("token-name"), $token.val());
            },*/
                success : function(response) {
                console.log(response);
                if(response.code == "SUCCESS"){
                    location.href = response.item.url;	//이전페이지로 돌아가기
                } else {
                    alert(response.message);
                }
            },
            error : function(a,b,c){
                console.log(a,b,c);
            }
        });
    }
}

let logout = {
    init : function() {
        this.create();
    },
    create : function() {
        $(document).on("click", "#logout-button", function() {
            logout.execute();
        });
    },
    execute : function() {
        $.ajax({
            url: "/logout",
            type: "POST",
            dataType: "json",
            success: function (response) {
                location.href = "/";
            },
            error: function (a, b, c) {
                console.log(a, b, c);
            }
        });
    }
}

$(document).ready(function() {
    login.init();
    logout.init();
});
