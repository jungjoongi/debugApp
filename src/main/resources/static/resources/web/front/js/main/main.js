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
            const $that = $(this);
            let target = $that.data("target");
            let os = $that.data("os");
            let originFileName = $that.data("originFileName");
            let webRoot = location.href.substring(0, location.href.lastIndexOf("/"));;
            let iosRoot = "itms-services://?action=download-manifest&url="+webRoot;

            if(target != undefined && target != "") {
                target = "/web/mykt/download/"+target;
                if("IOS" == os) {
                    target = iosRoot + target;
                    location.href = target;
                } else {
                    form.saveAs(target, originFileName);
                }
                alert("파일 다운로드를 시작합니다.");
                $that.removeClass("download-btn", setTimeout(function() {
                    $that.addClass("download-btn");
                }, 30000));
            } else {
                alert("파일이 손상되었습니다. 관리자에게 문의하세요");
            }
        });
    },
    saveAs : function(target, originFileName) {
        let link = document.createElement('a');
        document.body.appendChild(link);
        link.download = originFileName;
        link.href = target;
        link.click();
        document.body.removeChild(link);
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
                    appListHtml += '<td>';
                    if("" != appList[i].devPath) {
                        appListHtml += '<button type="button" data-target="'+appList[i].devPath+'" data-os="'+appList[i].os+'" data-origin-file-name="'+appList[i].devOriginFileName+'" class="btn btn-block btn-outline-warning btn-sm download-btn">개발</button>';
                    }
                    appListHtml += '</td>';
                    appListHtml += '<td>';
                    if("" != appList[i].prePrdPath) {
                        appListHtml += '<button type="button" data-target="'+appList[i].prePrdPath+'" data-os="'+appList[i].os+'" data-origin-file-name="'+appList[i].prePrdOriginFileName+'" class="btn btn-block btn-outline-info btn-sm download-btn">디버그</button>';
                    }
                    appListHtml += '</td>';
                    appListHtml += '<td>';
                    if("" != appList[i].prdPath) {
                        appListHtml += '<button type="button" data-target="'+appList[i].prdPath+'" data-os="'+appList[i].os+'" data-origin-file-name="'+appList[i].prdOriginFileName+'" class="btn btn-block btn-outline-danger btn-sm download-btn">운영</button>';
                    }
                    appListHtml += '</td>';
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



