/*
$.get(serverRoot + "/json/auth/islogin", {}, user => {
    var user = decodeURIComponent(user);

if (user == 'n') {
    swal({
        type: 'error',
        title: '비정상적인 접근입니다.',
        text: '이전페이지로 돌아갑니다.',
        showConfirmButton: false,
        timer: 1000
    })
    setTimeout(function () {
        history.back();
    }, 1000)
}
})
*/
var mapArr = new Array();
var mapData = null;
var addrValue = null;
var addrValue2 = null;
var placeValue = null;
var xValue = null;
var yValue = null;


// 마커를 담을 배열입니다
var markers = [];

function addrClick(val) {
    console.log($(val).parent().children()[0])
    addrValue = val;
    addrValue2 = val;
    placeValue = $(val).parent().children()[0]
    xValue = $(val).parent().children()[2]
    yValue = $(val).parent().children()[3]
    return;
}
var mapContainer = document.getElementById('editor-map'), // 지도를 표시할 div
    mapOption = {
        center: new daum.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };

// 지도를 생성합니다
var map = new daum.maps.Map(mapContainer, mapOption);

// 장소 검색 객체를 생성합니다
var ps = new daum.maps.services.Places();

// 검색 결과 목록이나 마커를 클릭했을 때 장소명을 표출할 인포윈도우를 생성합니다
var infowindow = new daum.maps.InfoWindow({
    zIndex: 1
});

// 키워드로 장소를 검색합니다
searchPlaces();

// 키워드 검색을 요청하는 함수입니다
function searchPlaces() {

    var keyword = document.getElementById('keyword').value;

    if (!keyword.replace(/^\s+|\s+$/g, '')) {
        swal({
            position: 'top-end',
            type: 'success',
            title: '입력오류',
            text: '키워드를 입력해주세요',
            showConfirmButton: false,
            timer: 1500
        })
        return false;
    }

    // 장소검색 객체를 통해 키워드로 장소검색을 요청합니다
    ps.keywordSearch("제주도 " + keyword, placesSearchCB);

}

// 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
function placesSearchCB(data, status, pagination) {
    if (status === daum.maps.services.Status.OK) {

        // 정상적으로 검색이 완료됐으면
        // 검색 목록과 마커를 표출합니다
        displayPlaces(data);
        mapData = data;



        // 지도데이터
        for (let i = 0; i < data.length; i++) {
            mapArr[i] = $(data[i])[0]
            console.log($(data[i])[0])
        }
        $('.item').click(function () {
            console.log($(data[$(this).index()])[0])
            addrValue.value = $(data[$(this).index()])[0].address_name
            placeValue.value = $(data[$(this).index()])[0].place_name
            xValue.value = $(data[$(this).index()])[0].x
            yValue.value = $(data[$(this).index()])[0].y
            swal(
                "'" + $(data[$(this).index()])[0].place_name + "'",
                '리스트에 추가하였습니다.',
                'success'
            )
            xyname()
            $(".close").trigger('click')
        })

        // $(data).click(function () {
        //     console.log(this)
        // })

        // 페이지 번호를 표출합니다
        displayPagination(pagination);

    } else if (status === daum.maps.services.Status.ZERO_RESULT) {

        swal({
            position: 'top-end',
            type: 'success',
            title: '검색오류',
            text: '검색결과가 존재하지 않습니다.',
            showConfirmButton: false,
            timer: 1500
        })
        return;

    } else if (status === daum.maps.services.Status.ERROR) {

        swal({
            position: 'top-end',
            type: 'success',
            title: '검색오류',
            text: '검색결과중 오류가 발생하였습니다.',
            showConfirmButton: false,
            timer: 1500
        })
        return;

    }
}



// 검색 결과 목록과 마커를 표출하는 함수입니다
function displayPlaces(places) {

    var listEl = document.getElementById('placesList'),
        menuEl = document.getElementById('menu_wrap'),
        fragment = document.createDocumentFragment(),
        bounds = new daum.maps.LatLngBounds(),
        listStr = '';

    // 검색 결과 목록에 추가된 항목들을 제거합니다
    removeAllChildNods(listEl);

    // 지도에 표시되고 있는 마커를 제거합니다
    removeMarker();

    for (var i = 0; i < places.length; i++) {

        // 마커를 생성하고 지도에 표시합니다
        var placePosition = new daum.maps.LatLng(places[i].y, places[i].x),
            marker = addMarker(placePosition, i),
            itemEl = getListItem(i, places[i]); // 검색 결과 항목 Element를 생성합니다

        // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
        // LatLngBounds 객체에 좌표를 추가합니다
        bounds.extend(placePosition);

        // 마커와 검색결과 항목에 mouseover 했을때
        // 해당 장소에 인포윈도우에 장소명을 표시합니다
        // mouseout 했을 때는 인포윈도우를 닫습니다
        (function (marker, title) {
            daum.maps.event.addListener(marker, 'mouseover', function () {
                displayInfowindow(marker, title);
            });

            daum.maps.event.addListener(marker, 'mouseout', function () {
                infowindow.close();
            });

            itemEl.onmouseover = function () {
                displayInfowindow(marker, title);
            };

            itemEl.onmouseout = function () {
                infowindow.close();
            };
        })(marker, places[i].place_name);

        fragment.appendChild(itemEl);
    }

    // 검색결과 항목들을 검색결과 목록 Elemnet에 추가합니다
    listEl.appendChild(fragment);
    menuEl.scrollTop = 0;

    // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
    map.setBounds(bounds);
}

// 검색결과 항목을 Element로 반환하는 함수입니다
function getListItem(index, places) {

    var el = document.createElement('li'),
        itemStr = '<span class="markerbg marker_' + (index + 1) + '"></span>' +
            '<div class="info">' +
            '   <h5>' + places.place_name + '</h5>';

    if (places.road_address_name) {
        itemStr += '    <span>' + places.road_address_name + '</span>' +
            '   <span class="jibun gray">' + places.address_name + '</span>';
    } else {
        itemStr += '    <span>' + places.address_name + '</span>';
    }

    itemStr += '  <span class="tel">' + places.phone + '</span>' +
        '</div>';

    el.innerHTML = itemStr;
    el.className = 'item';

    return el;
}

// 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
function addMarker(position, idx, title) {
    var imageSrc = 'http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png', // 마커 이미지 url, 스프라이트 이미지를 씁니다
        imageSize = new daum.maps.Size(36, 37), // 마커 이미지의 크기
        imgOptions = {
            spriteSize: new daum.maps.Size(36, 691), // 스프라이트 이미지의 크기
            spriteOrigin: new daum.maps.Point(0, (idx * 46) + 10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
            offset: new daum.maps.Point(13, 37) // 마커 좌표에 일치시킬 이미지 내에서의 좌표
        },
        markerImage = new daum.maps.MarkerImage(imageSrc, imageSize, imgOptions),
        marker = new daum.maps.Marker({
            position: position, // 마커의 위치
            image: markerImage
        });

    marker.setMap(map); // 지도 위에 마커를 표출합니다
    markers.push(marker); // 배열에 생성된 마커를 추가합니다

    return marker;
}

// 지도 위에 표시되고 있는 마커를 모두 제거합니다
function removeMarker() {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
    markers = [];
}

// 검색결과 목록 하단에 페이지번호를 표시는 함수입니다
function displayPagination(pagination) {
    var paginationEl = document.getElementById('pagination'),
        fragment = document.createDocumentFragment(),
        i;

    // 기존에 추가된 페이지번호를 삭제합니다
    while (paginationEl.hasChildNodes()) {
        paginationEl.removeChild(paginationEl.lastChild);
    }

    for (i = 1; i <= pagination.last; i++) {
        var el = document.createElement('a');
        el.href = "#";
        el.innerHTML = i;

        if (i === pagination.current) {
            el.className = 'on';
        } else {
            el.onclick = (function (i) {
                return function () {
                    pagination.gotoPage(i);
                }
            })(i);
        }

        fragment.appendChild(el);
    }
    paginationEl.appendChild(fragment);
}

// 검색결과 목록 또는 마커를 클릭했을 때 호출되는 함수입니다
// 인포윈도우에 장소명을 표시합니다
function displayInfowindow(marker, title) {
    var content = '<div style="padding:5px;z-index:1;">' + title + '</div>';

    infowindow.setContent(content);
    infowindow.open(map, marker);
}

// 검색결과 목록의 자식 Element를 제거하는 함수입니다
function removeAllChildNods(el) {
    while (el.hasChildNodes()) {
        el.removeChild(el.lastChild);
    }
}


function relayout() {

    // 지도를 표시하는 div 크기를 변경한 이후 지도가 정상적으로 표출되지 않을 수도 있습니다
    // 크기를 변경한 이후에는 반드시  map.relayout 함수를 호출해야 합니다
    // window의 resize 이벤트에 의한 크기변경은 map.relayout 함수가 자동으로 호출됩니다
    map.relayout();
}
$("#map_modal").on('shown.bs.modal', function (e) {
    e.preventDefault();
    searchPlaces();
    relayout();
});
//////////////////////////////////////////////
//////////////////////////////           지도2
//////////////////////////////////////////////
var mapContainer2 = document.getElementById('editor-map'), // 지도를 표시할 div
    mapOption = {
        center: new daum.maps.LatLng(33.359040372937415, 126.53430503608087), // 지도의 중심좌표
        level: 10 // 지도의 확대 레벨
    };

var map2 = new daum.maps.Map(mapContainer2, mapOption); // 지도를 생성합니다

// 마커를 표시할 위치와 title 객체 배열입니다
// var addrValue = null;
// var placeValue = null;
// var xValue = null;
// var yValue = null;

var positions = [];
var marker2 = null;
var markerImage = null;
var imageSize = null;
var imageSrc = null;
var markers2 = []
linePath = []

function xyname() {
    for (let z = 0; z < positions.length; z++) {
        if (positions[z].addr == addrValue2) {
            positions.splice(z, 1)
            removeMarkers()
            break;
        }
    }

    function removeMarkers() {
        for (let e = 0; e < markers2.length; e++) {
            console.log(markers2[e])
            markers2[e].setMap(null);
        }
        markers2 = [];
    }


    positions.push({
        title: placeValue.value,
        latlng: new daum.maps.LatLng(yValue.value, xValue.value),
        addr: addrValue
    })
    imageSrc = "http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";


    for (var i = 0; i < positions.length; i++) {
        console.log("호출")

        // 마커 이미지의 이미지 크기 입니다
        imageSize = new daum.maps.Size(24, 35);

        // 마커 이미지를 생성합니다
        markerImage = new daum.maps.MarkerImage(imageSrc, imageSize);

        // 마커를 생성합니다
        marker2 = new daum.maps.Marker({
            position: positions[i].latlng, // 마커를 표시할 위치
            title: positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
            image: markerImage // 마커 이미지

        });
        // linePath.push(new daum.maps.LatLng(positions[i].latlng.jb, positions[i].latlng.ib))
        marker2.setMap(map2)
        markers2.push(marker2)
    }
    // var polyline = new daum.maps.Polyline({
    //     path: linePath, // 선을 구성하는 좌표배열 입니다
    //     strokeWeight: 5, // 선의 두께 입니다
    //     strokeColor: '#FFAE00', // 선의 색깔입니다
    //     strokeOpacity: 0.7, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
    //     strokeStyle: 'solid' // 선의 스타일입니다
    // });
    // polyline.setMap(map2);

    console.log(positions)

    for (let i = 0; i < markers2.length; i++) {
        console.log("bbb")
    }


    // 지도에 표시할 선을 생성합니다
    // var polyline = new daum.maps.Polyline({
    //     path: linePath, // 선을 구성하는 좌표배열 입니다
    //     strokeWeight: 5, // 선의 두께 입니다
    //     strokeColor: '#FFAE00', // 선의 색깔입니다
    //     strokeOpacity: 0.7, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
    //     strokeStyle: 'solid' // 선의 스타일입니다
    // });

    // 지도에 선을 표시합니다
    // polyline.setMap(map);



}

function markerRemove(e) {
    console.log(e)

    for (var i = 0; i < positions.length; i++) {
        console.log("-", positions[i].latlng.ib)
        if (positions[i].latlng.ib == e) {
            console.log("삭제")
            positions.splice(i, 1)
            for (var i2 = 0; i2 < markers2.length; i2++) {
                markers2[i2].setMap(null)
            }
            markers2 = []
            break;
        }
    }


    for (var i = 0; i < positions.length; i++) {

        // 마커 이미지의 이미지 크기 입니다
        imageSize = new daum.maps.Size(24, 35);

        // 마커 이미지를 생성합니다
        markerImage = new daum.maps.MarkerImage(imageSrc, imageSize);

        // 마커를 생성합니다
        marker2 = new daum.maps.Marker({
            position: positions[i].latlng, // 마커를 표시할 위치
            title: positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
            image: markerImage // 마커 이미지
        });
        marker2.setMap(map2)
        markers2.push(marker2)
    }

}

$(function () {
    $('.text_count1').each(function () {
        // count 정보 및 count 정보와 관련된 textarea/input 요소를 찾아내서 변수에 저장한다.
        var $count = $('.count1', this);
        var $input = $('#post_text_form_1')
        // .text()가 문자열을 반환하기에 이 문자를 숫자로 만들기 위해 1을 곱한다.
        var maximumCount = $count.text() * 1;
        // update 함수는 keyup, paste, input 이벤트에서 호출한다.
        var update = function () {
            var before = $count.text() * 1;
            var now = maximumCount - $input.val().length;
            // 사용자가 입력한 값이 제한 값을 초과하는지를 검사한다.
            if (now < 0) {
                var str = $input.val();
                swal({
                    type: 'error',
                    title: '입력초과',
                    text: '글자 입력수를 초과하였습니다.',
                    showConfirmButton: false,
                    timer: 1500
                })
                $input.val(str.substr(0, maximumCount));
                now = 0;
            }
            // 필요한 경우 DOM을 수정한다.
            if (before != now) {
                $count.text(now);
            }
        };
        // input, keyup, paste 이벤트와 update 함수를 바인드한다
        $input.bind('input keyup paste', function () {
            setTimeout(update, 0)
        });
        update();
    });
});

// 캘린더
var calCopy = null;
$(document).ready(function () {
    $("#total_cost").text($("#total_cost_value").val())
});


$(function () {
    $(".calendar").datepicker({
        dateFormat: 'yy/mm/dd',
        firstDay: 1
    });

    $(document).on('click', '.date-picker .input', function (e) {
        var $me = $(this),
            $parent = $me.parents('.date-picker');
        $parent.toggleClass('open');
    });



    $(".calendar").on("change", function () {

        var $me = $(this),
            $selected = $me.val(),
            $parent = $me.parents('.date-picker');
        $parent.find('.date_text').siblings('span').html($selected);
        $parent.toggleClass('open');
        var str = '';
        var str2 = '';
        for (var i = 0; i < $('#sdt').length; i++) {
            str += $('#sdt').eq(i).html();
        }
        for (var i = 0; i < $('#edt').length; i++) {
            str2 += $('#edt').eq(i).html();
        }
        var dateArray = str.split("/");
        var dateArray2 = str2.split("/");
        var dateObj = new Date(dateArray[0], Number(dateArray[1]) - 1, dateArray[2]);
        var dateObj2 = new Date(dateArray2[0], Number(dateArray2[1]) - 1, dateArray2[2]);
        var betweenDay = (dateObj2.getTime()) / 1000 / 60 / 60 / 24 - dateObj.getTime() /
            1000 /
            60 / 60 / 24;
        var $edt = $('#edt')
        var $sdt = $("#sdt")
        if (betweenDay || betweenDay == 0) {
            if (betweenDay < 0) {
                swal({
                    type: 'error',
                    title: '입력실패',
                    text: '출발일과 도착일을 확인해주세요',
                })
                document.getElementById('sdt').innerHTML = "";
                document.getElementById('edt').innerHTML = "";
                document.getElementById('cal_day').innerHTML = "0";
                $('.cont_select_box').children().remove()
                return;
            }
            document.getElementById('cal_day').innerHTML = betweenDay + 1;
            /********************************************
             ↓↓↓ 일차별 셀렉트박스 생성 ↓↓↓↓
             *********************************************/
            selectAdd()

            function selectAdd() {
                var contSelectBox = $('.cont_select_box')
                if ((contSelectBox.children().length < 1) || betweenDay + 1 > 0) {
                    console.log(betweenDay)
                    contSelectBox.children().remove()
                    contSelectBox.append("<option value=''>일차를 선택해 주세요</option>")
                    for (let i = 1; i < betweenDay + 2; i++) {
                        contSelectBox.append(
                            "<option value='" + i + "'>" + i + "일차</option>"
                        )
                    }
                    calCopy = $('.cont_select_box').clone();

                }
            }


            // $('.cont_select_box').append(
            //         "<option value='1'>1일차</option>" +
            //         "<option value='2'>2일차</option>" +
            //         "<option value='3'>3일차</option>"
            // )


        }
    });
});

function chkValue() {
    // 공통입력폼내의 모든 입력오브젝트
    var titleObjs = $("#title");
    var inputObjs = $(".post_wrap input");
    var selectObjs = $(".post_wrap select");
    var textObjs = $(".post_wrap textarea");
    var tagBox = $('#tag_box').children().length
    // 미입력여부(경우에 따라 사용)
    var bEmpty = true;
    var focus;

    titleObjs.each(function (index) {
        if ($(this).val() == '') {
            focus = $(this);
            bEmpty = false;

            swal({
                type: 'error',
                title: $(this).attr('title') + "는 필수 입력사항입니다.",
                showConfirmButton: false,
                customClass: 'animated tada',
                animation: false,
                timer: 1000
            })
            setTimeout(function () {
                focus.focus();
            }, 1100)
            return false;
        }
    });
    if (!bEmpty) return;

    // 각 오브젝트에 대해 입력체크
    inputObjs.each(function (index) {
        if ($(this).val() == '') {
            focus = $(this);
            bEmpty = false;

            swal({
                type: 'error',
                title: $(this).attr('title') + "는 필수 입력사항입니다.",
                showConfirmButton: false,
                customClass: 'animated tada',
                animation: false,
                timer: 1000
            })
            setTimeout(function () {
                focus.focus();
            }, 1100)
            return false;
        }
    });
    if (!bEmpty) return;

    textObjs.each(function (index) {
        if ($(this).val() == '') {
            focus = $(this);
            bEmpty = false;

            swal({
                type: 'error',
                title: $(this).attr('title') + "는 필수 입력사항입니다.",
                showConfirmButton: false,
                customClass: 'animated tada',
                animation: false,
                timer: 1000
            })

            setTimeout(function () {
                focus.focus();
            }, 1100)

            // 여기서는 each문을 탈출
            return false;
        }
    });

    // 필수입력사항에 누락이 있으면 진행금지
    if (!bEmpty) return;

    selectObjs.each(function (index) {
        if ($(this).val() == '') {
            focus = $(this);
            bEmpty = false;

            swal({
                type: 'error',
                title: $(this).attr('title') + "는 필수 입력사항입니다.",
                showConfirmButton: false,
                customClass: 'animated tada',
                animation: false,
                timer: 1000
            })
            setTimeout(function () {
                focus.focus();
            }, 1100)

            // 여기서는 each문을 탈출
            return false;
        }
    });
    if (!bEmpty) return;

    if (tagBox < 1) {
        swal({
            type: 'error',
            title: $('#tag_box').attr('title') + "은 필수입력사항입니다.",
            showConfirmButton: false,
            customClass: 'animated tada',
            animation: false,
            timer: 1000
        })
        setTimeout(function () {
            $('#input').focus();
        }, 1100)
        bEmpty = false;
    }
    if (!bEmpty) return;

    submits();
}
async function submits() {
    /**********************************************
     발행스크립트
     ***********************************************/
    var contentNo;
    var userId;

    $.get(serverRoot + "/json/auth/islogin", {}, user => {
        userId = decodeURIComponent(user.id);
    var user = decodeURIComponent(user);
    if (user == 'n') {
        swal({
            type: 'error',
            title: '비정상적인 접근입니다.',
            text: '이전페이지로 돌아갑니다.',
            showConfirmButton: false,
            timer: 1500
        })
        history.back();

    }
})


    const {
        value: day
    } = await swal({
        title: '잠깐! 누구와 함께 다녀왔나요?',
        input: 'select',
        inputOptions: {
            '1': '혼자',
            '2': '부모님',
            '3': '아이',
            '4': '커플',
            '5': '친구',
            '6': '단체'
        },
        showCancelButton: true,
        inputValidator: (value) => {

    }
})
    if (day) {
        var a = 1;

        var param = {
            title: $("#title").val(),
            id: userId
        };
        $.ajax({
            type: "POST",
            url: serverRoot + "/json/content/add",
            data: param,
            success: function (data) {

                contentNo = data;

                travelLogInsert();
                // location.href = "planner_view.html?"+ data;
            },
            error: function (data) {
                swal({
                    type: 'error',
                    title: '입력실패',
                    text: '데이터 입력을 실패하였습니다.',
                    showConfirmButton: false,
                    timer: 1500
                })
            }

        });



        function travelLogInsert() {
            var travelLog = {
                tlno: contentNo,
                partner: day,
                preference: a,
                startDate: $('#sdt')[0].innerText.replace(/[^(0-9)]/gi, "-"),
                endDate: $('#edt')[0].innerText.replace(/[^(0-9)]/gi, "-")
            };

            $.ajax({
                type: "POST",
                url: serverRoot + "/json/travelLog/add",
                data: travelLog,
                success: function (data) {

                    travelLogContentInsert()


                },
                error: function (data) {
                    swal({
                        type: 'error',
                        title: '입력실패',
                        text: '데이터 입력을 실패하였습니다.',
                        showConfirmButton: false,
                        timer: 1500
                    })
                }

            });
        }



        function travelLogContentInsert() {
            for (let i = 1; i < $('.post_wrap').length + 1; i++) {
                console.log($('#post_title_' + i).val())
                var travelLogContent = null;
                travelLogContent = {
                    tlno: contentNo,
                    place: $('#post_title_' + i).val(),
                    address: $('#post_address_' + i).val(),
                    photo: $('#main_img_' + i).val(),
                    review: $('#post_text_form_' + i).val(),
                    latd: $('#post_x_' + i).val(),
                    lotd: $('#post_y_' + i).val(),
                    day: $("#selectid_" + i + " option:selected").val()
                };
                console.log("<<<<-----------------")
                console.log(travelLogContent)

                $.ajax({
                    type: "POST",
                    url: serverRoot + "/json/travelLogContent/add",
                    data: travelLogContent,
                    success: function (data) {

                    },
                    error: function (data) {
                        swal({
                            type: 'error',
                            title: '입력실패',
                            text: '데이터 입력을 실패하였습니다.',
                            showConfirmButton: false,
                            timer: 1500
                        })
                        break;
                    }
                });
            }
            hashTagInsert()
        }

        function hashTagInsert() {
            for (let i = 0; i < $("#tag_box > li").length; i++) {

                var travelHashTag = {
                    cno: contentNo,
                    content: $("#tag_box > li")[i].innerText.slice(0, -1)
                };

                $.ajax({
                    type: "POST",
                    url: serverRoot + "/json/hashTag/add",
                    data: travelHashTag,
                    success: function (data) {

                        location.href = "travelog_view.html?" + contentNo;
                    },
                    error: function (data) {
                        swal({
                            type: 'error',
                            title: '입력실패',
                            text: '데이터 입력을 실패하였습니다.',
                            showConfirmButton: false,
                            timer: 1500
                        })
                    }

                });
            }

        }
    }
}
// 게시글 모두삭제버튼
function cntRemoveBtn() {
    swal({
        title: '정말 삭제하시겠습니까?',
        text: "작성한 모든 정보가 삭제됩니다.",
        type: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: '네, 삭제할께요'
    }).then((result) => {
        if (result.value) {
        swal({
            position: 'top-end',
            type: 'success',
            title: '삭제완료',
            text: '작성중인 게시물을 삭제하였습니다.',
            showConfirmButton: false,
            timer: 1500
        })
        $('#post_title_1').val("")
        $('#post_address_1').val("")
        $('#post_text_form_1').val("")
        $('#post_x_1').val("")
        $('#post_y_1').val("")
        $('.add_post').remove();
        $('.upload_remove_btn').trigger('click')
        positions = []
        for (var i2 = 0; i2 < markers2.length; i2++) {
            markers2[i2].setMap(null)
        }
        markers2 = []



        // markerRemove(removeMarkers);
    }

})
}

var button = document.getElementById("enter");
var input = document.getElementById("input");
var ul = document.getElementById("tag_box");
var li = document.getElementById("tag_box").getElementsByTagName("li");
var deleteBtns = document.getElementsByClassName("delete");
var cnt

function inputLength() {
    return input.value.length;
}

function createListElement() {
    cnt = $("#tag_box > li").length;
    console.log(cnt);
    if (cnt > 4) {
        swal({
            type: 'error',
            title: '입력초과',
            text: 'HashTag는 5개까지 입력가능합니다.',
            showConfirmButton: false,
            timer: 1500
        })
        return;
    }
    if (input.value.replace(/\s/g, '') && input.value.replace(/\#/g, '')) {
        var li = document.createElement("li");
        var button = document.createElement("button");
        button.appendChild(document.createTextNode("X"));
        li.appendChild(document.createTextNode('#' + input.value.replace(/\s/g, '').replace(/\#/g, '')));
        ul.appendChild(li);
        li.appendChild(button)
        input.value = "";

        li.classList.add("items", "it");

        button.addEventListener("click", deleteItem);
        button.classList.add("delete");
        return;
    }
}

function addNewItemAfterClick() {
    if (inputLength() > 0) {
        createListElement();
    }
}

function addNewItemAfterKeypress(event) {
    if (inputLength() > 0 && event.keyCode === 13) {
        createListElement();
    }
}

function toggleDone() {
    this.classList.toggle("done");
}

function deleteItem() {
    var child = document.activeElement.parentNode;
    ul.removeChild(child);
}

function manageGroupTag(tag, event, action) {
    for (let i = 0; i < tag.length; i++) {
        tag[i].addEventListener(event, action);
    }
}

button.addEventListener("click", addNewItemAfterClick);
input.addEventListener("keypress", addNewItemAfterKeypress);
manageGroupTag(li, "click", toggleDone);
manageGroupTag(deleteBtns, "click", deleteItem);

var count = 2;

function add_div() {
    $('#post_wraps').append(
        "<div id=" + "'cont_" + count + "'" +
        " onclick='imgBoxClick(this)' class='post_wrap add_post'>" +
        "<button class='cancel_btn' onclick='delete_cont(this.value)'></button>" +
        "<div class='post_imgbox' data-toggle='modal' data-target='.bd-example-modal-lg" + count + "'>" +
        "<span class='post_imgbox_text'>클릭하여 사진 등록</span>" +
        "<img src=''>" +
        "</div>" +
        "<div class='post_text'>" +

        "<form>" +
        "<input id='post_title_" + count + "'" +
        "type='text' value='' class='post_titl' placeholder='장소' title='장소' required>" +
        "<input id='post_address_" + count + "'" +
        "type='text' onclick='addrClick(this)' class='post_addr' value='' placeholder='주소' readonly data-toggle='modal' data-target='.bd-example-modal-lg-map1' title='주소' required>" +
        "<input id='post_x_" + count +
        "' type='text' class='post_x' value='' placeholder='위도' hidden required>" +
        "<input id='post_y_" + count +
        "' type='text' class='post_y' value='' placeholder='경도' hidden required>" +
        "<div id='map' style='width:300px;height:300px;margin-top:10px;display:none'></div>" +
        "<textarea id='post_text_form_" + count + "'" +
        "class='title_textarea text_area' placeholder='내용을 입력하세요.' title='내용' data-is-observable='false' required></textarea>" +
        "<input id='main_img_" + count + "' value='' title='메인사진' hidden>" +
        "<div class='daySelect_div" + count + " daySelect_div'>" +
        "</div>" +
        "<div class='text_count text_count" + count + "'>글자제한:" +
        "<span class='count count" + count + "'>80</span>/80</div>" +
        "<button onclick='map()'></button>" +
        "</form>" +
        "</div>" +
        "</div>"
    );

    (function (count) {
        $('#modalDiv').append(
            "<div class='modal fade bd-example-modal-lg" + count + "'" +
            "tabindex='-1' role='dialog' aria-labelledby='myLargeModalLabel' aria-hidden='true'>" +
            "<div class='modal-dialog modal-lg'>" +
            "<div class='modal-content'>" +
            "<div class='modal-header'>" +
            "<h5>사진업로드</h5>" +
            "<button type='button' class='close' data-dismiss='modal' aria-label='Close'>" +
            "<span aria-hidden='true'>&times;</span>" +
            "</button>" +
            "</div>" +
            "<div id='trueForm" + count + "'" + ">" +
            "<div class='dropzone' onclick='imgClickBoxsSub(this)'>" +
            "<div>" +
            "</div>" +
            "<span class='dz_ico'>+</span>" +
            "<div id='imgbox" + count + "'" + " class='imgboxs1'>업로드 대기중인 사진이 없습니다.</div>" +
            "</div>" +

            "<div id=imgbox2_" + count + "'" + " class='imgboxs2'>" +
            "<p>업로드 완료된 사진</p>" +
            "</div>" +
            "<input id='fileupload" + count + "'" +
            "class='fileuploads' type='file' name='files' multiple hidden required>" +

            "<div class='clear'>" +


            "<button id='btnSubmit' class='btnSubmits' type='button' onclick='closeBtn()'>확인</button>" +

            "</div>" +

            "</div>" +

            "</div>" +
            "</div>" +
            "</div>"
        )
    })(count)
    calCopy = $('.cont_select_box').clone();
    $('.daySelect_div' + count).append(calCopy[0])
    $('.daySelect_div' + count).children().attr("id", "selectid_" + count)


    /********************************************
     글자제한 검사 추가
     ********************************************/
    $('.text_count' + count).each(function () {
        // count 정보 및 count 정보와 관련된 textarea/input 요소를 찾아내서 변수에 저장한다.
        let $count2 = $('.count' + count, this);
        let $input2 = $('#post_text_form_' + count)
        // .text()가 문자열을 반환하기에 이 문자를 숫자로 만들기 위해 1을 곱한다.   // .text()가 문자열을 반환하기에 이 문자를 숫자로 만들기 위해 1을 곱한다.
        var maximumCount = $count2.text() * 1;
        // update 함수는 keyup, paste, input 이벤트에서 호출한다.
        var update = function () {
            var before = $count2.text() * 1;
            var now = maximumCount - $input2.val().length;
            // 사용자가 입력한 값이 제한 값을 초과하는지를 검사한다.
            if (now < 0) {
                var str = $input2.val();
                swal({
                    type: 'error',
                    title: '입력초과',
                    text: '글자 입력수를 초과하였습니다.',
                    showConfirmButton: false,
                    timer: 1500
                })
                $input2.val(str.substr(0, maximumCount));
                now = 0;
            }
            // 필요한 경우 DOM을 수정한다.
            if (before != now) {
                $count2.text(now);
            }
        };
        // input, keyup, paste 이벤트와 update 함수를 바인드한다
        $input2.bind('input keyup paste', function () {
            setTimeout(update, 0)
        });
        update();
    });

    // $('.daySelect_div'+count).clone(contSelectBox)

    count++;
    var imagesDiv = null;
    var previewsCnt2 = 0;
    $("#fileupload" + (count - 1)).fileupload({
        url: serverRoot + '/json/upload/travel', // 서버에 요청할 URL
        dataType: 'json', // 서버가 보낸 응답이 JSON임을 지정하기
        sequentialUploads: true, // 여러 개의 파일을 업로드 할 때 순서대로 요청하기.
        singleFileUploads: false, // 한 요청에 여러 개의 파일을 전송시키기.
        autoUpload: true, // 파일을 추가할 때 자동 업로딩 하지 않도록 설정.
        disableImageResize: /Android(?!.*Chrome)|Opera/
            .test(window.navigator && navigator.userAgent), // 안드로이드와 오페라 브라우저는 크기 조정 비활성 시키기
        previewMaxWidth: 120, // 미리보기 이미지 너비
        previewMaxHeight: 120, // 미리보기 이미지 높이
        previewCrop: true, // 미리보기 이미지를 출력할 때 원본에서 지정된 크기로 자르기
        processalways: function (e, data) {
            imagesDiv = $(this).parent().children()[0].childNodes[2];
            console.log('fileuploadprocessalways()...');
            console.log(data.files);
            console.log($(this).parent().children()[0].childNodes[2]);
            console.log("len:", data.files.length);
            $(imagesDiv).html("");

            for (var i = 0; i < data.files.length; i++) {
                try {
                    if (data.files[i].preview.toDataURL) {
                        $(imagesDiv).append(
                            "<div style='display:inline-block;position:relative;'>" +
                            "<div style='position:absolute;top:44px;left:26px'>" +
                            "<div id='floatingCirclesG'>" +
                            "<div class='f_circleG' id='frotateG_01'></div>" +
                            "<div class='f_circleG' id='frotateG_02'></div>" +
                            "<div class='f_circleG' id='frotateG_03'></div>" +
                            "<div class='f_circleG' id='frotateG_04'></div>" +
                            "<div class='f_circleG' id='frotateG_05'></div>" +
                            "<div class='f_circleG' id='frotateG_06'></div>" +
                            "<div class='f_circleG' id='frotateG_07'></div>" +
                            "<div class='f_circleG' id='frotateG_08'></div>" +
                            "</div>" +
                            "</div>" +
                            "<img src=" + data.files[i].preview.toDataURL() +
                            " style='width: 120px;' class=" +
                            "'readyImgs'" + ">" +
                            "</div>"
                        )


                        // $("<img>").attr('src', data.files[i].preview.toDataURL()).css('width',
                        //     '120px').appendTo(imagesDiv);

                        // console.log(data.files[i].name)
                        $('#btnSubmit').removeAttr("disabled");
                    }
                } catch (err) {
                    console.log("err");
                }
            }
            console.log($('.readyImgs').length)


            // $('#btnSubmit').unbind("click");
            // $('#btnSubmit').click(function () {

            //     alert("클릭")
            //     data.submit();
            // });
        },
        submit: function (e, data) { // 서버에 전송하기 직전에 호출된다.
            console.log('submit()...');
        },
        done: function (e, data) { // 서버에서 응답이 오면 호출된다. 각 파일 별로 호출된다.
            console.log('done()...');
            console.log(data.result.files);

            $.each(data.result.files, function (index, file) {
                let previews = [];
                $.each(data.files, function (index, file) {
                    previews.push(file.preview.toDataURL())
                });
                let imgBox = $(imagesDiv).parent().parent().children()[1]
                $(imgBox).append(
                    "<div class='imgboxDiv' style='display:inline-block;width:120px;height:120px;overflow:hidden;border-radius:20%;margin:3px;position:relative;'>" +
                    "<div class='colorBox'></div>" +
                    "<button class='upload_remove_btn' onclick='removeImg(this)'><img src='../../img/sub08/remove.png'></button>" +
                    "<button class='upload_main_btn' onclick='mainImg(this)'><img src='../../img/sub08/main.png'>대표사진 변경</button>" +
                    "<img src='" + previews[previewsCnt2] + "'>" +
                    "<input name='imgName' type='text' value='" + file.filename +
                    "'>" +
                    "<input name='imgX' type='text' value='" + file.filename + "'>" +
                    "<input name='imgY' type='text' value='" + file.filename + "'>" +
                    "</div>"
                )
                previewsCnt2++;

                if ($('imagesDiv').children("img").length == 0) {
                    $(imagesDiv).html("업로드 대기중인 사진이 없습니다.")
                }
            });
            previewsCnt2 = 0;


        }
    });
}

var imgBoxClicks = null;

function imgBoxClick(val) {
    console.log(val)
    imgBoxClicks = val

}

function removeImg(val) {
    // 추가되는 사진
    $(val).parent().remove();
    if ($(imgBoxClicks).children()[1].childNodes[1].src == $(val).parent().children()[3].currentSrc) {
        $(imgBoxClicks).children()[1].childNodes[1].src = "";
        $(imgBoxClicks).children()[1].childNodes[0].childNodes[13].value = "";
        $(imgBoxClicks).children()[1].childNodes[0].style.display = "block";
    }
    // 첫번째 사진
    if ($(imgBoxClicks).children()[0].childNodes[3].src == $(val).parent().children()[3].currentSrc) {
        $(imgBoxClicks).children()[0].childNodes[3].src = "";
        $(imgBoxClicks).children()[1].childNodes[1].childNodes[13].value = "";
        $(imgBoxClicks).children()[0].childNodes[1].style.display = "block";
    }

}

function mainImg(val) {
    console.log($(val).parent().children()[3].currentSrc)
    // console.log($(imgBoxClicks).children()[1].childNodes[1].childNodes[13].value)
    $(imgBoxClicks).children()[1].childNodes[1].src = $(val).parent().children()[3].currentSrc
    $(imgBoxClicks).children()[1].childNodes[0].style.display = "none";
    $(imgBoxClicks).children()[2].childNodes[0].childNodes[6].value = $(val).parent().children()[4].value

    swal({
        position: 'top-end',
        type: 'success',
        title: '대표사진이 변경되었습니다.',
        showConfirmButton: false,
        timer: 1500
    })
    $(".close").trigger('click')
}

function mainImg2(val) {
    console.log($(imgBoxClicks).children())
    $(imgBoxClicks).children()[0].childNodes[3].src = $(val).parent().children()[3].currentSrc
    $(imgBoxClicks).children()[0].childNodes[1].style.display = "none";

    $(imgBoxClicks).children()[1].childNodes[1].childNodes[13].value = $(val).parent().children()[4].value // 메인이미지 이름과 메인이미지 들어가야 할곳
    swal({
        position: 'top-end',
        type: 'success',
        title: '대표사진이 변경되었습니다.',
        showConfirmButton: false,
        timer: 1500
    })
    $(".close").trigger('click')
}

function closeBtn() {

    $(".close").trigger('click')
    // $(".upload_remove_btn").trigger('click')
}

function delete_cont() {
    var contDiv = document.activeElement.parentNode;
    var removeMarkers = contDiv.childNodes[2].childNodes[0][2].value
    swal({
        title: '정말 삭제하시겠습니까?',
        text: "작성한 모든 정보가 삭제됩니다.",
        type: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: '네, 삭제할께요'
    }).then((result) => {
        if (result.value) {
        swal({
            position: 'top-end',
            type: 'success',
            title: '삭제완료',
            text: '작성중인 게시물을 삭제하였습니다.',
            showConfirmButton: false,
            timer: 1500
        })
        contDiv.remove();
        markerRemove(removeMarkers);
    }

})

}

var $imgClickBox = null;
var showBox = null;
var showBox2 = null;

function imgClickBoxs(val) {
    console.log(val.parentNode.childNodes[5])
    $imgClickBox = val.parentNode.childNodes[5]
    $imgClickBox.click();
    // imgClickBox.
}

function imgClickBoxsSub(val) {
    console.log(val.parentNode.childNodes[2])
    $imgClickBox = val.parentNode.childNodes[2]
    $imgClickBox.click();
    // imgClickBox.
}



var previewImg = [];
var previewsCnt = 0;
$("#fileupload1").fileupload({
    url: serverRoot + '/json/upload/travel', // 서버에 요청할 URL
    dataType: 'json', // 서버가 보낸 응답이 JSON임을 지정하기
    sequentialUploads: true, // 여러 개의 파일을 업로드 할 때 순서대로 요청하기.
    singleFileUploads: false, // 한 요청에 여러 개의 파일을 전송시키기.
    autoUpload: true, // 파일을 추가할 때 자동 업로딩 하지 않도록 설정.
    disableImageResize: /Android(?!.*Chrome)|Opera/
        .test(window.navigator && navigator.userAgent), // 안드로이드와 오페라 브라우저는 크기 조정 비활성 시키기
    previewMaxWidth: 120, // 미리보기 이미지 너비
    previewMaxHeight: 120, // 미리보기 이미지 높이
    previewCrop: true, // 미리보기 이미지를 출력할 때 원본에서 지정된 크기로 자르기
    processalways: function (e, data) {

        imagesDiv = $(this).parent().children()[0].childNodes[5];
        console.log('fileuploadprocessalways()...');
        $(imagesDiv).html("");

        for (var i = 0; i < data.files.length; i++) {
            try {
                if (data.files[i].preview.toDataURL) {
                    $(imagesDiv).append(
                        "<div style='display:inline-block;position:relative;'>" +
                        "<div style='position:absolute;top:44px;left:26px'>" +
                        "<div id='floatingCirclesG'>" +
                        "<div class='f_circleG' id='frotateG_01'></div>" +
                        "<div class='f_circleG' id='frotateG_02'></div>" +
                        "<div class='f_circleG' id='frotateG_03'></div>" +
                        "<div class='f_circleG' id='frotateG_04'></div>" +
                        "<div class='f_circleG' id='frotateG_05'></div>" +
                        "<div class='f_circleG' id='frotateG_06'></div>" +
                        "<div class='f_circleG' id='frotateG_07'></div>" +
                        "<div class='f_circleG' id='frotateG_08'></div>" +
                        "</div>" +
                        "</div>" +
                        "<img src=" + data.files[i].preview.toDataURL() +
                        " style='width: 120px;' class=" +
                        "'readyImgs'" + ">" +
                        "</div>"
                    )


                }
            } catch (err) {
                console.log("err");
            }
        }

    },
    submit: function (e, data) { // 서버에 전송하기 직전에 호출된다.
        console.log('submit()...');
    },
    done: function (e, data) { // 서버에서 응답이 오면 호출된다. 각 파일 별로 호출된다.
        console.log('done()...');
        console.log(data.result.files);

        $.each(data.result.files, function (index, file) {
            let previews = [];
            $.each(data.files, function (index, file) {
                previews.push(file.preview.toDataURL())
            });
            let imgBox = $(imagesDiv).parent().parent().children()[1]
            $(imgBox).append(
                "<div class='imgboxDiv' style='display:inline-block;width:120px;height:120px;overflow:hidden;border-radius:20%;margin:3px;position:relative;'>" +
                "<div class='colorBox'></div>" +
                "<button class='upload_remove_btn' onclick='removeImg(this)'><img src='../../img/sub08/remove.png'></button>" +
                "<button class='upload_main_btn' onclick='mainImg2(this)'><img src='../../img/sub08/main.png'>대표사진 변경</button>" +
                "<img src='" + previews[previewsCnt] + "'>" +
                "<input name='imgName' type='text' value='" + file.filename + "'>" +
                "<input name='imgX' type='text' value='" + file.filename + "'>" +
                "<input name='imgY' type='text' value='" + file.filename + "'>" +
                "</div>"
            )
            previewsCnt++;


            if ($('imagesDiv').children("img").length == 0) {
                $(imagesDiv).html("업로드 대기중인 사진이 없습니다.")
            }
        });
        previewsCnt = 0;


    }
});
