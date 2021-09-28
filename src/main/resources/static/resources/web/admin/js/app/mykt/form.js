let form = {
    init : function() {
        form.create();
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

            if(!form.isProgress) {
                form.isProgress = true;
                if(form.validation()) {
                    let formData = $('#app-from')[0];
                    let data = new FormData(formData)

                    $.ajax({
                        url: "/admin/app/mykt/save",
                        type: "POST",
                        enctype: 'multipart/form-data',
                        processData : false,
                        contentType : false,
                        cache: false,
                        data: data,
                        success: function(data) {
                            form.isProgress = false;
                            console.log(data)
                            if(data.result == "Y") {
                                location.href="/admin/app/mykt/list";
                            }
                        },
                        error: function (request, status, error){
                            form.isProgress = false;
                            console.log(error)
                        }
                    })

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

        var version = "" + $("#version1").val() + $("#version2").val() + $("#version3").val();
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
        let file2Extention = "";
        let isFile1Pass = false;
        let isFile2Pass = false;

        for(let i in form.ALLOW_EXTENTION) {
            if(file1Extention == form.ALLOW_EXTENTION[i]) {
                isFile1Pass = true;
            }
        }
        if($("#select-os").val() == "IOS") {
            file2Extention = $("#files2").val().split(".").pop().toLowerCase();
            for(let i in form.ALLOW_EXTENTION) {
                if(file1Extention == form.ALLOW_EXTENTION[i]) {
                    isFile2Pass = true;
                }
            }
        } else {
            isFile2Pass = true;
        }

        return isFile1Pass && isFile2Pass ? true : false;

    }
}
$(document).ready(function() {
    form.init();
});



