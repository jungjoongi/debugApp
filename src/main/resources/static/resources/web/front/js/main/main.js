let form = {
    init : function() {
        form.create();
    },
    create : function () {
        this.makeUI();
    },
    makeUI : function() {
        $.ajax({
            type: "get",
            url: "/getCartList",
            dataType: "json",
            success: function (data, status, xhr) {
                let res = data.res;
                console.log(res);
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



