let form = {
    init : function() {

    },
    create : function () {
        this.submit();
        this.getFileName();
    },
    isProgress : false,
    submit : function() {
        $(document).on('click', '#submit' , function(e){
            e.preventDefault();

            if(!form.isProgress) {
                form.isProgress = true;
                if(form.validation()) {
                    let form = $('#app-from')[0];
                    let data = new FormData(form)

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
        form.versionSetting();

        if("" == fileInput) {

            alert("파일을 업로드 해주세요");
            $("#files").focus();
            return false;
        } else {
            return true;
        }


    },
    versionSetting : function() {

        var version = "" + $("#version1").val() + $("#version2").val() + $("#version3").val();
        $("#version").val(version);

    }
    ,
    getFileName : function() {
        $("#files").change(function(obj){
            $("#files-label").html(obj.target.files[0].name);
        });
    }
}
$(document).ready(function() {
    form.init();
    form.create();
});



