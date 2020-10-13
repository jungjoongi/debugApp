let form = {
    init : function() {

    },
    create : function () {
        this.submit();
    },
    isProgress : false,
    submit : function() {
        $(document).on('click', '#submit' , function(){
            if(!isProgress) {
                form.isProgress = true;
                if(this.validation()) {
                    $("#app-from").submit();
                    form.isProgress = false;
                }
            }
        });
    },
    validation : function() {


    }
}
$(document).ready(function() {
    list.init();
    list.create();
});


