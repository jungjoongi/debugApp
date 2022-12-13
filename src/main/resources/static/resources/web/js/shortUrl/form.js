let form = {
    init : function() {
        form.create();
    },
    create : function () {
        form.submit();
        form.copy();
    },
    isProgress : false,
    submit : function() {
         $(document).on("click", "#submit", function() {
            let url = "/shortUrl/save";
            const formData = $("#form").serialize();
            if(!form.isProgress) {
                form.isProgress = true;

                if(form.validation()) {

                    $.ajax({
                        url: url,
                        type: "POST",
                        data: formData,
                        /** 파일업로드 UI
                        xhr: function(){
                            let xhr = $.ajaxSettings.xhr();
                            xhr.upload.onprogress = function(e){
                                let per = e.loaded * 100 / e.total;
                                console.log(per);
                                common.uploadUi(per);
                            };
                            return xhr;
                        },
                         ****/
                        success: function(data) {
                            form.isProgress = false;
                            if(data.result == "SUCCESS") {
                                const shortUrl = "https://link.weolbu.com/"+ data.res.shortUrl;
                                $("#shortUrl").val(shortUrl);
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
    copy : function() {
        $(document).on("click", "#copyBtn", function() {
            form.copyBtn();
        });
    },
    validation : function() {

        let url = $("#url").val();
        return url.length > 0 ? true : false;
    },
    toastAlert : function(text) {
        const Toast = Swal.mixin({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 3000,
            timerProgressBar: true,
            didOpen: (toast) => {
                toast.addEventListener('mouseenter', Swal.stopTimer)
                toast.addEventListener('mouseleave', Swal.resumeTimer)
            },
            showClass: {
                popup: 'animate__animated animate__slideInRight'
            },
            hideClass: {
                popup: 'animate__animated animate__slideOutRight'
            }
        })
        Toast.fire({
            icon: 'success',
            title: text
        })
    },
    copyBtn : function() {
        const text = $("#shortUrl").val();
        window.navigator.clipboard.writeText(text).then(() => {
            // 복사가 완료되면 이 부분이 호출된다.
            form.toastAlert("복사완료");
        });
    }
}

$(document).ready(function() {
    form.init();
});




