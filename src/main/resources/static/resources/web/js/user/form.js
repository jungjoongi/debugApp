let form = {
    init : function() {
        this.create();
        form.validation();
    },
    create : function() {
        $(document).on("click", "#submit-button", function() {
            form.execute();
        });

        $("#password-retry, #password, #email").keyup(function() {
            form.validation();
        });

        form.makeSelectBox();
        form.submitBtn();

    },
    execute : function() {

        let type = $("#userId").val() == undefined ? "POST" : "PUT";
        let url = $("#userId").val() == undefined ? "/api/v1/user/save" : "/api/v1/user/update";
        $.ajax({
            url: url,
            type : type,
            dataType : "json",
            data : $("#register-form").serialize(),
            success : function(response) {
                console.log(response);
                if(response.code == "SUCCESS"){
                    location.href = "/user/list";
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

        if("SUCCESS" != form.passwordRules()) {
            $("#password-retry").removeClass("is-valid");
            $("#password-retry").addClass("is-invalid");
            $("#validationServerUsernameFeedback").html(form.passwordRules());
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
    makeSelectBox : function() {
        let option = [
            {value: "GUEST", title:"GUEST", selected : ""},
            {value: "MANAGER", title:"MANAGER", selected : ""},
            {value: "ADMIN", title:"ADMIN", selected : ""},
            {value: "BLOCK", title:"이용정지", selected : ""}
        ];
        const selectedDate = $("#option").html().trim();
        option.forEach(e => {
            if(e.value == selectedDate) {
                e.selected = "selected";
            }
        })
        let template = "{{#list}}<option value='{{value}}' {{selected}}>{{title}}</option>{{/list}}";
        let optionTemplate = Mustache.render(template, {"list":option} );
        $('#role').html(optionTemplate);
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

    },
    submitBtn : function() {
        $("#submit-button").html($("#userId").val() == undefined ? "등록하기" : "수정하기");
    }
}


$(document).ready(function() {
    form.init();
});
