<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title>首页</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" >
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<!-- css -->
<link href="/wx/css/common.css" rel="stylesheet" type="text/css" />
<link href="/wx/css/main.css" rel="stylesheet" type="text/css" />
<link href="/wx/css/swiper.min.css" rel="stylesheet" type="text/css" />
<!-- js -->
<script type="text/javascript" src="/wx/js/jquery-2.1.0.min.js"></script>
<script type="text/javascript" src="/wx/js/common.js"></script>


</head>
<body background="#fff;">
<#include "/wx/rightmore.ftl">
	
<div class="top_size"></div>
<div class="clear"></div>
<!-- header -->
<section class="color_header" >
    <a href="javascript:history.go(-1);" title="" class="hleft headleft header"><img src="/wx/images/leftbackwhite.png"> </a>
    <div class="htitle header"><#if article??>${article.title!''}<#else>无文章</#if></div>
    <a href="#" title="" class="hright headright header"><img src="/wx/images/rightmorewhite.png"></a>
</section>
<!-- header end -->
<#if article??>${article.content!''}</#if>

</body>
</html>
