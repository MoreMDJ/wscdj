<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title>消息列表</title>
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
    <a href="/wx/msg" title="" class="hleft headleft header"><img src="/wx/images/leftback.png"> </a>
    <div class="htitle header">消息列表</div>
    <a href="#" title="" class="hright headright header"><img src="/wx/images/rightmore.png"></a>
</section>
<!-- header end -->

<section class="newscenter2_box">
    <#if msgs??>
    <#list msgs as msg>
    <a href="<#if msg.linkUrl??>${msg.linkUrl!''}?returnTo=${param.reqUrl!''}<#else>/wx/msg/detail?returnTo=${param.reqUrl!''}&msgId=${msg.id?c}</#if>" title="">
        <h3>${msg.sendTime?string("yyyy-MM-dd")}</h3>
        <dl>
            <dt>${msg.title!'消息通知'}</dt>
            <dd>${msg.brief!''}</dd>
            <dd class="fs18" style="text-align: right;">查看详情></dd>
        </dl>
    </a>
    </#list>
    </#if>
</section>



</body>
</html>
