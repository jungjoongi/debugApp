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
            location.href="/admin/app/mykt/form";
        });
    },
    goModify : function() {
        $(document).on('click', '#modify-button' , function(){
            let id = $(this).data("id");
            location.href="/admin/app/mykt/form?id=" + id;
        });
    },
    goDelete : function() {
        $(document).on('click', '#delete-button' , function(){
            let id = $(this).data("id");
            let data = {
                id : id
            }
            $.ajax({
                url: "/admin/app/mykt/delete",
                type: "POST",
                data: data,
                success: function(data) {
                    console.log(data)
                    if(data.result == "SUCCESS") {
                        alert("삭제완료");
                        location.reload();
                    }
                },
                error: function (request, status, error){
                    alert("삭제실패");
                    form.isProgress = false;
                    console.log(error)
                }
            });
        });
    }
}
$(document).ready(function() {
    list.init();
});


