let form = {
    init : function() {

    },
    create : function () {
        this.submit();
        this.getFileName();
    },
    submit : function() {
        $(document).on("click", ".download-btn", function(){
               let target = $(this).data("target");
               if(target != undefined && target != "") {
                   location.href = target;
               } else {
                   alert("파일이 손상되었습니다. 관리자에게 문의하세요");
               }
        });
    }
}
$(document).ready(function() {
    form.init();
    form.create();
});



