<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title>医生列表</title>
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
		height: 100%;
	}
</style>
</head>
<body>

<div class="top_size"></div>

<div class="clear"></div>

<!-- header -->
<section class="white_header">
    <a href="javascript:history.go(-1);" title="" class="hleft headleft header"><img src="/wx/images/leftback.png"> </a>
    <div class="htitle header"><#if title??>${title!'医生列表'}<#else>医生列表</#if></div>
    <a href="#" title="" class="hright headright header"><img src="/wx/images/rightmore.png"></a>
</section>
<!-- header end -->

<#if summary??>
<section style="width:100%;display:block;overflow:hidden;background:#fff;padding:.2rem;font-size:.2rem;color:#999;">
	${summary!'暂无'}
</section>
</#if>

<section class="doctor_list">
	<#if doctor_list??>
	<#list doctor_list.content as doc>
	<a href="/wx/doctor/${doc.id?c}">
	<div class="h20"></div>
	<div class="doctor_list1">
        <div class="doctor_list1_l">
            <img src="${doc.headImgUrl!''}">
            <#if doc.isOnline>
            <label class="on_line">在线</label>
            <#else>
            <label class="off_line">离线</label>
            </#if>
        </div>

        <div class="doctor_list1_r">
            <dl>
                <dt>${doc.name!''}<span></span></dt>
                <dd><span>${doc.hospital!''}</span><span></span></dd>
                <dd>擅长：<i>${doc.speciality!''}</i></dd>
                <dd>
                    <div class="doctor_list1_r_num">服务人数<span>${doc.servedNo!'0'}</span></div>
                    <div class="doctor_list1_r_numnow">当前咨询人数<span>${doc.serveNo!'0'}</span></div>
                </dd>
            </dl>

        </div>
    </div>
    </a>
	</#list>
	</#if>
</section>




<!-- rightmore 右上角更多pop -->
<#include "/wx/rightmore.ftl">
<!-- rightmore end -->

</body>
</html>
