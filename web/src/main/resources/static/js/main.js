$(function () {
  // banner的高度

  var bannerh = $(window).height() - $(".header").height() - $(".footer").height();
  var bannerheight = $('.banner').height();
  if (bannerh > bannerheight) {
    $(".banner").height(bannerh);
  } else {
    $(".banner").height(bannerheight);
  }


  $(".lltabs1").click(function () {
    if ($(".ulright").hasClass("lltabs1ul")) {　　//如果node是隐藏的则显示node元素，否则隐藏
      $('.ulright').removeClass("lltabs1ul")
    } else {
      $('.ulright').addClass("lltabs1ul")
    }
  })
  $(".banner").click(function () {
    $(".ulright").addClass("lltabs1ul")
  })

  // 横菜单栏的切换
  $(".lltabs>ul>li").click(function () {
    $(this).addClass("active").siblings().removeClass("active");
  })

  // 启动轮播图
})