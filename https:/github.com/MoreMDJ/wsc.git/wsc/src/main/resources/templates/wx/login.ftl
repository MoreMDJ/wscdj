<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title>登录</title>
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
<script type="text/javascript">
function check()
{
	var mobile = $("#mobileId").val();
	var password = $("#passwordId").val();
	if(mobile==null || mobile=="" || mobile==undefined)
		{
			warning("请输入手机号");
			document.getElementById("mobileId").focus()
			return false;
		}
		
		if(!((/^1\d{10}$/.test(mobile)) || (/^MD\d{3}$/.test(mobile))))
		{
			warning("请输入正确的手机号");
			document.getElementById("mobileId").focus()
			return false;
		}
		
	if(password=null || password=="" || password==undefined)
	{
		warning("请输入密码");
		document.getElementById("passwordId").focus()
		return false;
	}
}
$(function(){
	<#if msg??>
	setTimeout(warning("${msg!''}"),10);
	</#if>
})
</script>
</head>
<body class="loginbg"">
<#-- 引入警告提示样式 -->
<#include "/common/common_warn.ftl">
<#-- 引入等待提示样式 -->
<#include "/common/common_wait.ftl">

<!-- header -->
<section class="login_header">
    <a href="javascript:history.go(-1)" class="hleft headleft header"><img src="/wx/images/leftbackwhite.png"> </a>
    <div class="htitle header colfff">登 录</div>
    <a class="hright headright header"></a>
</section>
<!-- header end -->

<!-- logo -->
<section class="login_logo">
    <img src="/wx/images/loginlogo.png">
</section>
<!-- logo end -->

<!-- textlinebox -->
<section class="textlinebox">
    <form action="/wx/login" onsubmit="return check();" method="POST">
    <#if param?? && param.returnTo?? && ""!=param.returnTo><input type="hidden" name="returnTo" value="${param.returnTo!'/'}"></#if>
    <span>
        <img src="/wx/images/user.png">
        <#-- <input type="tel" name="mobile" onkeyup="onlyInt(this)" onafterpaste="onlyInt(this)" id="mobileId" <#if param?? && param.mobile??>value="${param.mobile!''}"</#if> placeholder="用户名">-->
        <input type="text" name="mobile" id="mobileId" <#if param?? && param.mobile??>value="${param.mobile!''}"</#if> placeholder="用户名">
    </span>
    <span>
        <img src="/wx/images/pwd.png">
        <input type="password" name="password" id="passwordId" placeholder="密码" class="login_pwd">
    </span>
        <!-- 条件符合后，登录样式用subyes，未符合用sub -->
        <input type="submit" value="登 录" class="sub">
    </form>
</section>
<!-- textlinebox end -->

<section class="login_words">
    <a href="/wx/login/passwordReset" title="" >忘记密码</a>|
    <a href="/wx/join" title="">注册</a>
</section>




</body>
</html>
