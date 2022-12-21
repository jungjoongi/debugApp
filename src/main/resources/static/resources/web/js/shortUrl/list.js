let list = {
    init : function() {
        list.create();
    },
    create : function () {
        list.goForm();
        list.goModify();
        list.goDelete();
    },
    goForm : function() {
        $(document).on('click', '#form-btn' , function(){
            location.href="/shortUrl/form";
        });
    },
    goModify : function() {
        $(document).on('click', '#modify-button' , function(){
            let id = $(this).data("id");
            location.href="/shortUrl/form/" + id;
        });
    },
    goDelete : function() {
        $(document).on('click', '#delete-button' , function(){
            let id = $(this).data("id");
            $.ajax({
                url: "/api/v1/shortUrl/"+id,
                type: "DELETE",
                success: function(data) {
                    if(data.code == "SUCCESS") {
                        common.toastAlert("삭제완료", 1500);
                        setTimeout(function() {
                            location.reload();
                        }, 1500);

                    }
                },
                error: function (request, status, error){
                    alert(request.responseJSON.message);
                }
            });
        });
    }
}
$(document).ready(function() {
    list.init();
});


