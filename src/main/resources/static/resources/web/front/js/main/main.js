let form = {
    init : function() {
        form.create();
    },
    create : function () {
        this.submit();
        this.makeUI();
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
    },
    makeUI : function() {
        $.ajax({
            type: "get",
            url: "/getAppList",
            dataType: "json",
            success: function (data, status, xhr) {
                console.log(data);
                console.log(status);
                console.log(xhr);
            },
            error: function (xhr, status, e) {
                console.log("error", e);
                console.log("status", status);
            }
        });
    }
}
$(document).ready(function() {
    form.init();
});



