var common = {
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
    },
    uploadUi : function(percent) {

        let ui = '<div class="progress-wrap">';
        ui += '<div class="upload">'
        ui += '<div class="text">';
        ui +=       '<strong><span>파일을 업로드중입니다.</span> </strong>';
        ui +=                     '<div>';
        ui +=                         '<small>%</small>';
        ui +=                     '</div>';
        ui += '</div>';
        ui += '<div class="percent">';
        ui +=       '<span></span>';
        ui +=   '<div>';
        ui +=       '<svg preserveAspectRatio="none" viewBox="0 0 600 12">';
        ui +=           '<path d="M0,1 L200,1 C300,1 300,11 400,11 L600,11" stroke="currentColor" fill="none"></path>';
        ui +=       '</svg>';
        ui +=         '</div>';
        ui += '</div>';
        ui += '</div>';
        ui += '</div> ';

        if($('.progress-wrap').length == 0) {
            $('body').append(ui);
        }
        percent = Math.ceil(percent);
        const upload = $('.upload');
        if(percent >= 100) {
            upload[0].style.setProperty('--percent', 100);
            this.uploadComplete();
        } else {
            upload[0].style.setProperty('--percent', percent);
        }
    },
    uploadComplete : function () {
        let ui = "";
        ui += '<strong><span>서버에 파일을 저장중입니다.</span>';
        ui += '    <span>';
        ui += '        <svg class="spinner" width="2vw" height="2vw" viewBox="0 0 66 66" xmlns="http://www.w3.org/2000/svg">';
        ui += '            <circle class="path" fill="none" stroke-width="6" stroke-linecap="round" cx="33" cy="33"r="30"></circle>';
        ui += '        </svg>';
        ui += '    </span>';
        ui += '</strong>';
        $(".upload .text").html(ui);
    }
}

$(document).ready(function() {
    common.init();
});



