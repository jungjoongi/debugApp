let common = {
    init : function() {
        this.create();
    },
    create : function () {
        this.setHeader();
    },
    setHeader : function() {
        /**
         * 공통 헤더값 세팅
         * 1. _CSRF 토큰값 세팅
         */
        $(document).ajaxSend(function(e, xhr, option) {
            let $token = $("#_csrf");
            let header = $token.data("token-header")
            xhr.setRequestHeader(header, $token.val());
        });
    }
}

$(document).ready(function() {
    common.init();
});





