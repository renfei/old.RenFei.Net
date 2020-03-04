jQuery(function ($) {
    'use strict';

    //Responsive Nav
    $('li.dropdown').find('.fa-angle-down').each(function () {
        $(this).on('click', function () {
            if ($(window).width() < 768) {
                $(this).parent().next().slideToggle();
            }
            return false;
        });
    });

    //Fit Vids
    if ($('#video-container').length) {
        $("#video-container").fitVids();
    }

    //Initiat WOW JS
    new WOW().init();

    // portfolio filter
    $(window).on('load', function () {

        $('.main-slider').addClass('animate-in');
        $('.preloader').remove();
        //End Preloader

        if ($('.masonery_area').length) {
            $('.masonery_area').masonry();//Masonry
        }

        var $portfolio_selectors = $('.portfolio-filter >li>a');

        if ($portfolio_selectors.length) {

            var $portfolio = $('.portfolio-items');
            $portfolio.isotope({
                itemSelector: '.portfolio-item',
                layoutMode: 'fitRows'
            });

            $portfolio_selectors.on('click', function () {
                $portfolio_selectors.removeClass('active');
                $(this).addClass('active');
                var selector = $(this).attr('data-filter');
                $portfolio.isotope({filter: selector});
                return false;
            });
        }

    });


    $('.timer').each(count);

    function count(options) {
        var $this = $(this);
        options = $.extend({}, options || {}, $this.data('countToOptions') || {});
        $this.countTo(options);
    }

    // Search
    $('.fa-search').on('click', function () {
        $('.field-toggle').fadeToggle(200);
    });

    // Contact form
    // var form = $('#main-contact-form');
    // form.submit(function(event){
    //     event.preventDefault();
    //     var form_status = $('<div class="form_status"></div>');
    //     $.ajax({
    //         url: $(this).attr('action'),
    //         beforeSend: function(){
    //             form.prepend( form_status.html('<p><i class="fa fa-spinner fa-spin"></i> Email is sending...</p>').fadeIn() );
    //         }
    //     }).done(function(data){
    //         form_status.html('<p class="text-success">Thank you for contact us. As early as possible  we will contact you</p>').delay(3000).fadeOut();
    //     });
    // });

    // Progress Bar
    $.each($('div.progress-bar'), function () {
        $(this).css('width', $(this).attr('data-transition') + '%');
    });

    if ($('#gmap').length) {
        var map;

        map = new GMaps({
            el: '#gmap',
            lat: 43.04446,
            lng: -76.130791,
            scrollwheel: false,
            zoom: 16,
            zoomControl: false,
            panControl: false,
            streetViewControl: false,
            mapTypeControl: false,
            overviewMapControl: false,
            clickable: false
        });

        map.addMarker({
            lat: 43.04446,
            lng: -76.130791,
            animation: google.maps.Animation.DROP,
            verticalAlign: 'bottom',
            horizontalAlign: 'center',
            backgroundColor: '#3e8bff',
        });
    }
    checkad();
});

console.log("\n %c RENFEI.NET %c 你知道的太多了 %c \n", "color: #fff; background: #3274ff; padding:5px 0; border: 1px solid #3274ff;", "color: #3274ff; background: #fff; padding:5px 0; border: 1px solid #3274ff;", "");
console.log("\n %c 开发交流QQ群 %c 130832168 %c \n", "color: #fff; background: #eb0; padding:5px 0; border: 1px solid #eb0;", "color: #eb0; background: #fff; padding:5px 0; border: 1px solid #eb0;", "");

function checkad() {
    $.ajax({
        url: "https://cdn.renfei.net/js/ads.js",
        dataType: "script"
    }).fail(function () {
        var fab = $("#fab");
        if (fab != undefined) {
            fab.show();
        }
        var cont = $('.blog-details');
        if (cont != undefined) {
            var heig = cont.height();
            cont.height(heig);
            cont.html("");
        }

        console.log("Ads.js not loaded");
    });
}

function cancelReply() {
    $("#reply").val(undefined);
    $("#comments-reply-test").html("");
    $("#comments-reply").hide();
}

function replyTo(name, id) {
    $("#reply").val(id);
    $("#comments-reply-test").html(name);
    $("#comments-reply").show();
    $("html,body").animate({scrollTop: $("#comments-reply").offset().top}, 1000);
}

function replySubmi(typeid, id) {
    var data = {};
    var name = $("#name").val();
    if (isEmpty(name)) {
        notice("Notice - 提示", "昵称是必填的，请检查");
        return;
    } else {
        data.author = name;
    }
    var email = $("#email").val();
    if (isEmpty(email)) {
        notice("Notice - 提示", "邮箱是必填的，请检查");
        return;
    } else {
        data.authoremail = email;
    }
    var link = $("#link").val();
    if (!isEmpty(link)) {
        data.link = link;
    }
    var content = $("#content").val();
    if (isEmpty(content)) {
        notice("Notice - 提示", "评论内容是必填的，请检查");
        return;
    } else {
        data.content = content;
    }
    var reply = $("#reply").val();
    if (!isEmpty(reply)) {
        data.reply = reply;
    }
    $.ajax({
        url: '/api/comments/' + typeid + '/' + id,
        type: 'POST', //GET
        async: true,    //或false,是否异步
        data: data,
        timeout: 5000,    //超时时间
        dataType: 'json',
        success: function (data, textStatus, jqXHR) {
            if (data.success) {
                notice("Success - 成功！", "由于我站存在缓存机制，可能需要一些时间才能显示，请勿重复提交！");
                $("#name").val("");
                $("#email").val("");
                $("#link").val("");
                $("#content").val("");
                $("#main-contact-form").hide();
                $("#comments_success").show();
                cancelReply();
            } else {
                notice("Fail - 失败", data.message);
            }
        },
        error: function (xhr, textStatus) {
            notice("Error - 错误:" + textStatus, xhr.responseText);
        }
    });
    return false;
}

function isEmpty(obj) {
    if (obj == undefined || obj == null) {
        return true;
    } else if (obj.length > 0) {
        return false
    } else {
        return true;
    }
}

function notice(title, content) {
    new jBox('Notice', {
        theme: 'NoticeFancy',
        attributes: {
            x: 'left',
            y: 'top'
        },
        color: 'blue',
        content: content,
        title: title,
        maxWidth: 600,
        audio: '//' + staticdomain + '/font/jBox/audio/bling2',
        volume: 80,
        autoClose: Math.random() * 8000 + 2000,
        animation: {open: 'slide:bottom', close: 'slide:left'},
        delayOnHover: true,
        showCountdown: true,
        closeButton: true
    });
}

function getJiSuDownloadLink() {
    var downloadfile_jisu_btn = $("#downloadfile_jisu_btn").html();
    var downloadfile_jisu_code = $("#downloadfile_jisu_code").val();
    if (downloadfile_jisu_code === "") {
        notice("Fail - 失败", "请扫描二维码关注微信公众号获得授权码");
        $("#downloadfile_jisu_btn").html(downloadfile_jisu_btn);
        return;
    }
    $("#downloadfile_jisu_btn").html("正在获取请稍后");
    $.ajax({
        url: '/other/JiSuDownloadLink',
        type: 'GET', //GET
        async: true,    //或false,是否异步
        data: {
            code: downloadfile_jisu_code
        },
        timeout: 5000,    //超时时间
        dataType: 'json',
        success: function (data, textStatus, jqXHR) {
            if (data.success) {
                $('#downloadfile_jisu_link').attr('href', data.data.jisulink);
                $('#downloadfile_jisu_link').html(data.data.jisulink);
                $('#downloadfile_jisu_exp').html(data.data.expires);
            } else {
                $("#downloadfile_jisu_link").html("极速下载链接获取失败！" + data.message);
                notice("Fail - 失败", data.message);
            }
            $("#downloadfile_jisu_btn").html(downloadfile_jisu_btn);
        },
        error: function (xhr, textStatus) {
            $("#downloadfile_jisu_btn").html(downloadfile_jisu_btn);
            $("#downloadfile_jisu_link").html("极速下载链接获取失败，网络错误！");
            notice("Error - 错误:" + textStatus, xhr.responseText);
        }
    });
}