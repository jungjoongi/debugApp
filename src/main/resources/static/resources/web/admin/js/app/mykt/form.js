let form = {
    init : function() {
        form.create();
        form.modifyCheck();
    },
    create : function () {
        form.getFileName();
        form.submit();
        form.osChange();
    },
    isProgress : false,
    ALLOW_EXTENTION : ["apk", "plist", "ipa"],
    submit : function() {
         $(document).on("click", "#submit", function() {
            let isModify = $("#isModify").val();
            let url = "/admin/app/mykt/save";
            if(isModify == 'true') {
                url = "/admin/app/mykt/update";
            }

            if(!form.isProgress) {
                form.isProgress = true;

                if(form.validation()) {
                    let formData = $('#app-from')[0];
                    let data = new FormData(formData);


                    $.ajax({
                        url: url,
                        type: "POST",
                        enctype: 'multipart/form-data',
                        processData : false,
                        contentType : false,
                        cache: false,
                        data: data,
                        xhr: function(){
                            let xhr = $.ajaxSettings.xhr();
                            xhr.upload.onprogress = function(e){
                                let per = e.loaded * 100 / e.total;
                                common.uploadUi(per);
                            };
                            return xhr;
                        },
                        success: function(data) {
                            form.isProgress = false;
                            if(data.result == "SUCCESS") {
                                location.href="/admin/app/mykt/list";
                            }
                        },
                        error: function (request, status, error){
                            form.isProgress = false;
                            console.log(error)
                        }
                    });

                } else {
                    form.isProgress = false;
                }
            }
        });
    },
    validation : function() {

        let fileInput = $("#files").val();
        let fileInput2 = $("#select-os").val() == "IOS" ? $("#files2").val() : "pass";
        let returnFlag = true;
        form.versionSetting();

        if("" == fileInput || "" == fileInput2) {
            alert("파일을 업로드 해주세요");
            fileInput == "" ? $("#files").focus() : $("#files2").focus();
            returnFlag = false;
        }

        if(!form.extentionCheck()) {
            alert("확장자를 확인해주세요\n'apk', 'plist', 'ipa' 파일만 업로드 가능합니다.");
            returnFlag = false;
        }

        return returnFlag;


    },
    versionSetting : function() {

        let version = "" + $("#version1").val() + $("#version2").val() + $("#version3").val();
        $("#version").val(version);

    },
    getFileName : function() {
        $("#files1").change(function(obj){
            $("#files-label1").html(obj.target.files[0].name);
        });
        $("#files2").change(function(obj){
            $("#files-label2").html(obj.target.files[0].name);
        });
    },
    osChange : function() {
        $(document).on("change", "#select-os", function() {

            if("IOS" == $(this).val()) {
                $(".for-ios").show();
                $(".for-ios").find("input").attr("disabled", false)
            } else {
                $(".for-ios").hide();
                $(".for-ios").find("input").attr("disabled", true)
            }
        });
        $("#select-os").change();
    },extentionCheck : function() {

        let file1Extention = $("#files1").val().split(".").pop().toLowerCase();
        let isFilePass = false;

        for(let i in form.ALLOW_EXTENTION) {
            if(file1Extention == form.ALLOW_EXTENTION[i]) {
                isFilePass = true;
            }
        }


        return isFilePass;

    },
    modifyCheck : function() {
        let isModify = $("#isModify").val();
        let modifyOs = $("#modifyOs").val();
        let modifyVersion = $("#modifyVersion").val();
        let modifyComment = $("#modifyComment").val();
        let modifyEnv = $("#modifyEnv").val();
        let modifyId = $("#modifyId").val();
        if(isModify != 'true') {
            return false;
        }

        $("#select-os").val(modifyOs);
        $("#version1").val(modifyVersion.substr(0,1));
        $("#version2").val(modifyVersion.substr(1,1));
        $("#version3").val(modifyVersion.substr(2,2));
        $("#env").val(modifyEnv);
        $("#comment").val(modifyComment);







    }
}
$(document).ready(function() {
    form.init();
});




