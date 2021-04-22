let form = {
    init : function() {

    },
    create : function () {
        this.submit();
    },
    submit : function() {
        $(document).on("click", ".download-btn", function(e){
                e.preventDefault();
                let target = $(this).data("target");
                let os = $(this).data("os");
                let webRoot = location.href;
                let iosRoot = "itms-services://?action=download-manifest&url="+webRoot;

               if("IOS" == os) {
                   target = iosRoot + target;
               }

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



