/********************
 * Login Script
 ********************/
let register = {
    init : function() {
      this.create();
    },
    create : function() {
        $(document).on("click", "#submit-button", function() {
            register.execute();
        });

        $("#password-retry, #password, #email").keyup(function() {
            register.validation();
        });

    },
    execute : function() {

        $.ajax({
            url:"/api/v1/user/save",
            type :  "POST",
            dataType : "json",
            data : $("#register-form").serialize(),
/*            beforeSend : function(xhr) {
                //이거 안하면 403 error
                //데이터를 전송하기 전에 헤더에 csrf값을 설정한다
                let $token = $("#_csrf");
                xhr.setRequestHeader($token.data("token-name"), $token.val());
            },*/
            success : function(response) {
                console.log(response);
                if(response.code == "SUCCESS"){
                    location.href = "/";
                } else {
                    alert(response.responseCommonDto.message);
                }
            },
            error : function(a,b,c){
                console.log(a,b,c);
            }
        });
    },
    validation : function() {
        let password = $("#password").val();
        let passwordRetry = $("#password-retry").val();
        let email = $("#email").val();
        let isEmailPass = false;
        let isPasswordPass = false;
        let regex = new RegExp('[a-z0-9]+@weolbu.com$');

        if(!regex.test(email)) {
            $("#email").addClass("is-invalid");
        } else {
            $("#email").removeClass("is-invalid");
            isEmailPass = true;
        }

        if("SUCCESS" != register.passwordRules()) {
            $("#password-retry").removeClass("is-valid");
            $("#password-retry").addClass("is-invalid");
            $("#validationServerUsernameFeedback").html(register.passwordRules());
            isPasswordPass = false;
            return false;
        }

        if(password == passwordRetry) {
            $("#password-retry").removeClass("is-invalid");
            $("#password-retry").addClass("is-valid");
            isPasswordPass = true;
        } else {
            $("#password-retry").removeClass("is-valid");
            $("#password-retry").addClass("is-invalid");
            $("#validationServerUsernameFeedback").html("패스워드가 일치하지 않습니다.");
            isPasswordPass = false;
        }

        if(isPasswordPass && isEmailPass) {
            $("#submit-button").removeClass("disabled")
        } else {
            $("#submit-button").addClass("disabled")
        }

    },
    passwordRules : function () {
        let pw = $("#password").val();
        let num = pw.search(/[0-9]/g);
        let eng = pw.search(/[a-z]/ig);
        let spe = pw.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);

        if(pw.length < 8 || pw.length > 20){
            return "8자리 ~ 20자리 이내로 입력해주세요.";
        }else if(pw.search(/\s/) != -1){
            return "비밀번호는 공백 없이 입력해주세요.";
        }else if(num < 0 || eng < 0 || spe < 0 ){
            return "영문,숫자, 특수문자를 혼합하여 입력해주세요.";
        }else {
            return "SUCCESS";
        }

    }
}


$(document).ready(function() {
    register.init();
});
