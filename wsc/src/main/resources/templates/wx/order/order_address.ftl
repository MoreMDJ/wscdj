<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title>地址管理</title>
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
    <a href="javascript:history.go(-1)" title="" class="hleft headleft header"><img src="/wx/images/leftback.png"> </a>
    <div class="htitle header">地址</div>
    <a href="/wx/order/address/edit" title="新增" class="hright headright header">新增地址</a>
</section>
<!-- header end -->

<section class="address_bg"></section>
<#if addresses??>
<#list addresses as address>
<dl class="address_list mt20" onclick="window.location.href='/wx/order?addressId=${address.id?c}'">
    <dt><span>${address.acceptName!''}</span><span>${address.telphone!''}</span></dt>
    <dd>收货地址：${address.fullAddress!''}</dd>
    <dd>
        <div style="float: left;"><i class="<#if addressId?? && addressId == address.id>address_choiced</#if>"></i><#if addressId?? && addressId == address.id>当前选择<#else>请选择</#if></div>
        <#--<a href="/wx/order/address/edit?id=${address.id?c}" title=""><img src="/wx/images/address_edit.png"> 编辑</a>-->
        <#--<a href="/wx/order/address/del?id=${address.id?c}" title=""><img src="/wx/images/address_delete.png">删除</a>-->
    </dd>
</dl>
</#list>
</#if>
</body>
</html>
