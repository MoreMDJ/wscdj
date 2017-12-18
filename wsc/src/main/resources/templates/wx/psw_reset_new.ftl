<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title>忘记密码</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="viewport" content="initial-scale=1,maximum-scale=1,minimum-scale=1">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<!-- css -->
<link href="/wx/css/common.css" rel="stylesheet" type="text/css" />
<link href="/wx/css/main.css" rel="stylesheet" type="text/css" />
<link href="/wx/css/swiper.min.css" rel="stylesheet" type="text/css" />
<link href="/wx/css/my_swiper.css" rel="stylesheet" type="text/css" />
<!-- js -->
<script src="/wx/js/jquery-2.1.0.min.js" charset="utf-8" type="text/javascript"></script>
<script type="text/javascript" src="/wx/js/common.js"></script>
</head>
<body class="bgwhite">
<div class="top_size"></div>
<div class="clear"></div>
<#-- 引入警告提示样式 -->
<#include "/common/common_warn.ftl">
<#-- 引入等待提示样式 -->
<#include "/common/common_wait.ftl">

<!-- header -->
<section class="white_header">
    <a href="javascript:history.go(-1)" title="" class="hleft headleft header"><img src="/wx/images/leftback.png"> </a>
    <div class="htitle header">忘记密码</div>
    <a href="#" title="" class="hright headright header"></a>
</section>
<!-- header end -->



<!-- pwdtextlinebox -->
<section class="pwdtextlinebox">
    <form action="/wx/login/passwordChange" method="post">
    <span>
		<#if code??><input type="hidden" name="code"  value="${code!''}"></#if>
        <input name="password" type="password" placeholder="新密码">
    </span>
    <span>
       <input recheck="password" type="password" placeholder="确认密码" class="login_pwd" >
    </span>
        <!-- 条件符合后，登录样式用subyes，未符合用sub -->
        <input type="submit" value="确 定" class="sub">
    </form>
    <span class="Validform_checktip"></span>
</section>
<!-- pwdtextlinebox end -->






</body>
</html>
