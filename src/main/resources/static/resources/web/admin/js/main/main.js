$('#mvisual ul').slick({
    dots: true,
    infinite: true,
    autoplay: true,
    speed: 1000,
    fade: true,
    cssEase: 'linear'
});

$(document).ready(function() {
	$('.best_spot').slick({
		slidesToShow: 1,
		slidesToScroll: 1,
		autoplay: true,
		autoplaySpeed: 4000,
	});
	
	
	$('.best_planner ul').slick({
		slidesToShow: 1,
		slidesToScroll: 1,
		// autoplay: true,
		autoplaySpeed: 4000,
	});
})
/* 기상청 데이터
$.getJSON(serverRoot + "/json/weather/info", {
    nx: 59,
    ny: 125
}, data => {
    $(txt_weather_sky).text(data.wth);
    $(txt_month).text(data.m);
    $(txt_day).text(data.d);
    $(txt_week).text(data.e);
    
    if (data.wth == "맑음") {
        $(icon_weather).attr("src", "/resources/img/main/weather_icon01.png");
        $(txt_weather_sky).text(data.wth);
    } else if (data.wth == "구름조금") {
        $(icon_weather).attr("src", "/resources/img/main/weather_icon02.png");
        $(txt_weather_sky).text(data.wth);
    } else if (data.wth == "구름많음") {
        $(icon_weather).attr("src", "/resources/img/main/weather_icon03.png");
        $(txt_weather_sky).text(data.wth);
    } else if (data.wth == "흐림") {
        $(icon_weather).attr("src", "/resources/img/main/weather_icon03.png");
        $(txt_weather_sky).text(data.wth);
    } else if (data.wth == "비") {
        $(icon_weather).attr("src", "/resources/img/main/weather_icon05.png");
        $(txt_weather_sky).text(data.wth);
    } else if (data.wth == "눈비") {
        $(icon_weather).attr("src", "/resources/img/main/weather_icon06.png");
        $(txt_weather_sky).text(data.wth);
    } else {
        $(icon_weather).attr("src", "/resources/img/main/weather_icon07.png");
        $(txt_weather_sky).text(data.wth);
    }
})
*/