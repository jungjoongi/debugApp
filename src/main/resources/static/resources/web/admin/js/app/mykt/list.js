let list = {
    init : function() {

    },
    create : function () {
        this.goForm();
    },
    goForm : function() {
        $(document).on('click', '#form-btn' , function(){
            location.href="/admin/app/mykt/form";
        });
    }
}
$(document).ready(function() {
    list.init();
    list.create();
});


