let lnb = {
    init : function() {
        this.activeMenu();
    },
    create : function() {

    }
    , activeMenu : function() {
        $('.nav-link').each(function(idx, item) {
                let itemUri = $(item).data("uri");
                if(itemUri != undefined && itemUri == window.location.pathname) {
                    $(item).addClass("active");
                    $(item).parent("ul").css("display", "block");
                    $(item).parent("li").parents("li").children("a").addClass("active");
                    $(item).parent("li").parents("li").addClass("menu-is-opening");
                    $(item).parent("li").parents("li").addClass("menu-open");
                }
        });
    }
}

$(document).ready(function() {
    lnb.init();
});