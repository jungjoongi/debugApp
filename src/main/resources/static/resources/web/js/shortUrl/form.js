let form = {
    init : function() {
        form.create();
    },
    create : function () {
        form.submit();
        form.goList();
        form.copy();
        form.makeUI();
    },
    isProgress : false,
    isUpdate : false,
    submit : function() {
         $(document).on("click", "#submit", function() {
             let id = $("#shortUrlId").val() == undefined ? "" : $("#shortUrlId").val();
             form.isUpdate = $("#shortUrlId").val() == undefined ? false : true;
             const url = "/api/v1/shortUrl/save/"+id;
             const formData = $("#form").serialize();
             if(!form.isProgress) {
                form.isProgress = true;

                if(form.validation()) {

                    let type = $("#shortUrlId").val() == undefined ? "POST" : "PUT";

                    $.ajax({
                        url: url,
                        type: type,
                        data: formData,
                        success: function(data) {
                            form.isProgress = false;
                            if(data.code == "SUCCESS") {
                                const shortUrl = data.resData.shortUrl;
                                $("#shortUrl").val(shortUrl);
                                if(form.isUpdate) {
                                    common.toastAlert("수정완료", 3000);
                                } else {
                                    common.toastAlert("생성완료", 3000);
                                    $("#submit").attr("disabled", true)
                                }
                            }
                        },
                        error: function (request, status, error){
                            form.isProgress = false;
                            console.log(error)
                        }
                    });

                } else {
                    form.isProgress = false;
                    alert("url을 입력해주세요");
                    $("#url").focus();
                }
            }
        });
    },
    goList : function() {
        $(document).on("click", "#goList", function() {
            location.href="/shortUrl/list"
        });
    },
    copy : function() {
        $(document).on("click", "#copyBtn", function() {
            form.copyBtn();
        });
    },
    validation : function() {

        const url = $("#url").val();
        return url.length > 0 ? true : false;
    },
    copyBtn : function() {
        const text = $("#shortUrl").val();
        window.navigator.clipboard.writeText(text).then(() => {
            // 복사가 완료되면 이 부분이 호출된다.
            common.toastAlert("복사완료", 3000);
        });
    },
    makeUI : function() {
        form.makeSelectBox();
        form.checkbox();
        form.submitBtn();
    },
    makeSelectBox : function() {
        let option = [
            {value: "UNKNOWN", title:"미선택시 UNKNOWN", selected : ""},
            {value: "INSTAGRAM", title:"인스타그램", selected : ""},
            {value: "NAVER_CAFE", title:"네이버카페", selected : ""},
            {value: "NAVER_BLOG", title:"네이버블로그", selected : ""},
            {value: "YOUTUBE", title:"유튜브", selected : ""},
            {value: "KAKAO", title:"카카오", selected : ""}
        ];
        const selectedDate = $("#option").html().trim();
        option.forEach(e => {
            if(e.value == selectedDate) {
                e.selected = "selected";
            }
        })
        let template = "{{#list}}<option value='{{value}}' {{selected}}>{{title}}</option>{{/list}}";
        let optionTemplate = Mustache.render(template, {"list":option} );
        $('#platform').html(optionTemplate);
    },
    checkbox : function() {
        const isChecked = $("#paidCheck").html().trim() == "Y" ? true : false;
        $('#paidYn').prop('checked', isChecked);
    },
    submitBtn : function() {
        $("#submit").html($("#shortUrlId").val() == undefined ? "생성하기" : "수정하기");
    }
}


$(document).ready(function() {
    form.init();
});




