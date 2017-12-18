<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title>消息中心</title>
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
<link href="/wx/css/my_swiper.css" rel="stylesheet" type="text/css" />
<!-- js -->
<script type="text/javascript" src="/wx/js/jquery-2.1.0.min.js"></script>
<script type="text/javascript" src="/wx/js/swiper.min.js"></script>
<script type="text/javascript" src="/wx/js/common.js"></script>
</head>
<body>
<div class="top_size"></div>
<div class="clear"></div>

<!-- header -->
<section class="white_header">
    <a href="/wx/u" title="" class="hleft headleft header"><img src="/wx/images/leftback.png"> </a>
    <div class="htitle header">消息中心</div>
    <a href="#" title="" class="hright headright header"><img src="/wx/images/rightmore.png"></a>
</section>
<!-- header end -->

<section class="newscenter_box">
    <a href="/wx/msg/1?returnTo=${param.reqUrl!''}" title="">
        <dl>
            <dt><img src="/wx/images/news1.png"><#if msg1?? && msg1 gt 0><p></p></#if> </dt>
            <dd>消息通知</dd>
            <dd><span><#if msg1?? && msg1 gt 0>您有 ${msg1!''}  条新短消息未读！<#else>暂无新消息！</#if></span></dd>
        </dl>
    </a>
    <a href="/wx/msg/2?returnTo=${param.reqUrl!''}" title="">
        <dl>
            <dt><img src="/wx/images/news2.png"><#if msg2?? && msg2 gt 0><p></p></#if></dt>
            <dd>在线客服</dd>
            <dd><span><#if msg2?? && msg2 gt 0>您有 ${msg2!''}  条新短消息未读！<#else>暂无新消息！</#if></span></dd>
        </dl>
    </a>
    <a href="/wx/msg/3?returnTo=${param.reqUrl!''}" title="">
        <dl>
            <dt><img src="/wx/images/news3.png"><#if msg3?? && msg3 gt 0><p></p></#if></dt>
            <dd>物流助手</dd>
            <dd><span><#if msg3?? && msg3 gt 0>您有 ${msg3!''}  条新短消息未读！<#else>暂无新消息！</#if></span></dd>
        </dl>
    </a>
    <a href="/wx/msg/4?returnTo=${param.reqUrl!''}" title="">
        <dl>
            <dt><img src="/wx/images/news4.png"><#if msg4?? && msg4 gt 0><p></p></#if></dt>
            <dd>积分通知</dd>
            <dd><span><#if msg4?? && msg4 gt 0>您有 ${msg4!''}  条新短消息未读！<#else>暂无新消息！</#if></span></dd>
        </dl>
    </a>
</section>



</body>
</html>
