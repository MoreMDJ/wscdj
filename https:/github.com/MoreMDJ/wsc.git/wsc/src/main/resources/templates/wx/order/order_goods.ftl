<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title>购物清单</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" >
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
    <!-- css -->
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
    <a href="/wx/order" title="" class="hleft headleft header"><img src="/wx/images/leftback.png"> </a>
    <div class="htitle header">购物清单</div>
    <a title="" class="hright headright header"><img src="/wx/images/rightmore.png"></a>
</section>
<!-- header end -->


<!--car3_list-->
<section class="car3_box">
    <div class="car3_list">
    	<#if goods??>
    	<#list goods as goods>
        <dl>
            <dt><img src="${goods.gImgUrl!'' }"></dt>
            <dd class="dd1">${goods.gTitle!'' }</dd>
            <dd class="dd2">套餐类型：<span>三盒装</span></dd>
            <dd class="dd3"><span>￥${goods.salePrice?string('0.00')}</span><label>x${goods.quantity!'0'}</label></dd>
        </dl>
        </#list>
        </#if>
    </div>

</section>
<!--car3_list end-->

</body>
</html>
