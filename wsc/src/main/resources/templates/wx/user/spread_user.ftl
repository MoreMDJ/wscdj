<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title>会员列表</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" >
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<!-- css -->
<link href="/wx/css/main.css" rel="stylesheet" type="text/css" />
<link href="/wx/css/common.css" rel="stylesheet" type="text/css" />
<link href="/wx/css/center.css" rel="stylesheet" type="text/css" />
    <!-- js -->
    <script type="text/javascript" src="/wx/js/jquery-2.1.0.min.js"></script>
    <script type="text/javascript" src="/wx/js/common.js"></script>
</head>
<body>
<div class="top_size"></div>

<div class="clear"></div>
<!-- header -->
<section class="white_header">
    <a href="/wx/u/distribution" title="" class="hleft headleft header"><img src="/wx/images/leftback.png"> </a>
    <div class="htitle header">会员列表</div>
    <a title="" class="hright headright header"><img src="/wx/images/rightmore.png"></a>
</section>
<!-- header end -->

<section class="purse_list">
    <#if point_list?? && point_list?size gt 0>
   <#assign todayS="--">
   <#list point_list as point>
   	<#if todayS != point.initDate?string('dd')>
   	<#assign todayS=point.initDate?string('dd')>
   	<#if point_index == 0>
    <dl>
    <#else>
    </dl>
    <dl>
    </#if>
        <dt>${point.initDate?string('yyyy-MM-dd')}</dt>
        <dd>
            <span>${point.nickname!''}</span>
            <label class="purse_list_add">${point.initDate?string('HH:mm:ss')}</label>
        </dd>
    <#else>
    	<dd>
            <span>${point.nickname!''}</span>
            <label class="purse_list_add">${point.initDate?string('HH:mm:ss')}</label>
        </dd>
    </#if>
    </#list>
    </dl>
    <#else>
    <dl>
        <dt>暂无记录</dt>
    </dl>
    </#if>
</section>


</body>
</html>
