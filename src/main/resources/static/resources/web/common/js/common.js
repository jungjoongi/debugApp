var common = {
    init : function() {
        this.create();
    },
    create : function () {
        this.setHeader();
    },
    setHeader : function() {
        $(document).ajaxSend(function(e, xhr, option) {
            let $token = $("#_csrf");
            xhr.setRequestHeader($token.data("token-name"), $token.val());
        });
    }
}

$(document).ready(function() {
    common.init();
});



