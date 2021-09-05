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

               if(target != undefined && target != "") {
                   if("IOS" == os) {
                       target = iosRoot + target;
                   }
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
                let appList = data.appList;
                let appListHtml = "";
                for(i in appList) {
                    appListHtml += '<tr>';
                    appListHtml += '<td>'+appList[i].os+'</td>';
                    appListHtml += '<td>'+appList[i].version+'</td>';
                    appListHtml += '<td><button type="button" data-target="'+appList[i].devPath+'" data-os="'+appList[i].os+'" class="btn btn-block btn-outline-warning btn-sm download-btn">개발</button></td>';
                    appListHtml += '<td><button type="button" data-target="'+appList[i].prePrdPath+'" data-os="'+appList[i].os+'" class="btn btn-block btn-outline-info btn-sm download-btn">디버그</button></td>';
                    appListHtml += '<td><button type="button" data-target="'+appList[i].prdPath+'" data-os="'+appList[i].os+'" class="btn btn-block btn-outline-danger btn-sm download-btn">운영</button></td>';
                    appListHtml += '</tr>';
                }
                $('#appListWarp').html(appListHtml);
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



