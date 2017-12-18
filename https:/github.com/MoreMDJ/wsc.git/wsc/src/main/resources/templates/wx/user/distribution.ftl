<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title>我的分销</title>
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

<!--center_top-->
<input type="hidden" value="${qrcodeUrl!'为空'}">
<section class="center_top wrap100" style="height: 2.9rem;">
    <!-- header -->
    <section class="login_header">
        <#if !shop??><a href="/wx/u"class="hleft headleft header"><img src="/wx/images/leftbackwhite.png"> </a></#if>
        <div class="htitle header colfff"><#if !shop??>我的分销<#else>${shop!''}</#if></div>
        <#if !shop??><a class="hright headright header"><img src="/wx/images/rightmorewhite.png"></a></#if>
    </section>
    <!-- header end -->

    <!-- 余额积分-->
    <a href="#" title="" class="purse_a" >
        <dl>
            <dt>余额(元)</dt>
            <dd>${cash!string.currency}</dd>
        </dl>
    </a>
    <i style="border-right: .02rem #5b2d90 solid; float: left;height: .8rem;margin-top:.35rem;margin-left: .09rem;"></i>
    <a href="#" title="" class="purse_a" >
        <dl>
            <dt>积分(分)</dt>
            <dd>${points!'0' }</dd>
        </dl>
    </a>
    <a href="#" title="" class="purse_withdraw">提现</a>
    <!-- 余额积分 end-->
</section>
<!--center_top end-->

<section class="three_infobox">
    <a href="/wx/u/cash" title="">余额明细</a>
    <a href="/wx/u/point" title="">积分明细</a>
    <#--<a href="#" title="">分润明细</a>-->
    <a href="/wx/u/spreaduser" title="">会员列表</a>
    <#if shop??><a href="/wx/health/u?huid=1" title="">健康记录</a></#if>
    <#if distribution??><p>分销二维码</p>
    <span><img src="/wx/u/spreadimg/${number}"></span>
    </#if>
</section>
<#if shop??>
<section class="center_esc mt20">
   <a href="/wx/login/out">退出登录</a> 
</section>
</#if>


</body>
</html>
