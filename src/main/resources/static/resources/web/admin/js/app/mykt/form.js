let form = {
    init : function() {

    },
    create : function () {
        this.submit();
        this.getFileName();
    },
    isProgress : false,
    submit : function() {
        $(document).on('click', '#submit' , function(){
            if(!isProgress) {
                form.isProgress = true;
                if(this.validation()) {
                        let formData = $('#app-from').serialize() // serialize 사용
                        $.ajax({
                            url: "/admin/app/mykt/insert",
                            enctype: 'multipart/form-data',
                            type: "POST",
                            cache: false,
                            data: formData, // data에 바로 serialze한 데이터를 넣는다.
                            success: function(data){
                                form.isProgress = false;
                                console.log(data)
                            },
                            error: function (request, status, error){
                                form.isProgress = false;
                                console.log(error)
                            }
                        })

                }
            }
        });
    },
    validation : function() {
        let fileInput = $("#exampleInputFile").val();
        if("" == fileInput) {
            alert("파일을 업로드 해주세요");
            $("#exampleInputFile").focus();
            return false;
        }

    }
    ,
    getFileName : function() {
        $("#exampleInputFile").change(function(e){
            $("#exampleInputFile-label").html(e.target.files[0].name);
        });
    }
}
$(document).ready(function() {
    form.init();
    form.create();
});



