<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title>名医咨询</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" >
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<!-- css -->
<link href="/wx/css/common.css" rel="stylesheet" type="text/css" />

<link href="/wx/css/swiper.min.css" rel="stylesheet" type="text/css" />
<link href="/wx/css/my_swiper.css" rel="stylesheet" type="text/css" />

<link rel="stylesheet" type="text/css" href="/wx/css/doctor.css"/>
<!-- js -->
<script type="text/javascript" src="/wx/js/jquery-2.1.0.min.js"></script>
<script type="text/javascript" src="/wx/js/swiper.min.js"></script>
<script type="text/javascript" src="/wx/js/common.js"></script>
<style type="text/css">
	html,body{
		/*height: 100%;*/
		/*overflow: hidden;*/
		background: #fff;
	}
</style>
<#include "/common/wx_share.ftl">
</head>
<body>


<!-- header -->
<section class="d_header">
	<a href="javascript:history.go(-1);" title="" class="hleft header"><img src="/wx/images/leftbackwhite.png"> </a>
	<div class="photo">
            <img src="${doctor.headImgUrl!''}">
            <#if doctor.isOnline>
            <label class="on_line">在线</label>
            <#else>
            <label class="off_line">离线</label>
            </#if>
    </div>
</section>
<!-- header end -->

<div class="h90"></div>

<!-- 医生名字 -->
<section class="d_job">
	<p>
    	<span>${doctor.name!''}</span>
    	<span></span>
    </p>
    <p>${doctor.hospital!''}</p>
</section>
<!-- 医生名字 end -->

<!--咨询人数 -->
<section class="d_num">
	<span class="d_num_left">当前咨询人数<i>${doctor.serveNo!'0'}</i></span>
	
	<span class="d_num_right">已为<i>${doctor.servedNo!'0'}</i>位顾客服务</span>
</section>
<!--咨询人数  end-->

<div class="h2bg"></div>

<!--擅长介绍-->
<section class="d_specialty">
	<p class="p1">擅长介绍</p>
	<span>${doctor.speciality!''}</span>
</section>
<!--擅长介绍 end-->

<div class="h2bg"></div>

<!--医学教育背景-->
<section class="d_specialty">
	<p class="p2">简介</p>
	<span>${doctor.school!''}</span>
</section>
<!--医学教育背景 end-->

<div class="h2bg"></div>

<!--学术研究成果、获奖介绍-->
<section class="d_specialty">
	<p class="p3">从业</p>
	<span>${doctor.research!''}</span>
</section>
<!--学术研究成果、获奖介绍 end-->

<div class="h2bg"></div>

<!--医生寄语-->
<section class="d_specialty">
	<p class="p4">在线时间</p>
	<span>${doctor.motto!''}</span>
</section>
<!--医生寄语 end-->

<div class="h2bg"></div>

<!--相关证件-->
<section class="d_specialty">
	<p class="p5">成功案例</p>
	${doctor.cer!'没有相关证书'}
</section>
<!--相关证件 end-->




<div class="footer_size"></div>
<#if doctor.isOnline>
<a href="http://wpa.qq.com/msgrd?v=3&uin=${doctor.qq!''}&site=qq&menu=yes" class="d_footer">发起咨询</a>
</#if>


















<!-- rightmore 右上角更多pop -->
<#include "/wx/rightmore.ftl">
<!-- rightmore end -->

</body>
</html>
