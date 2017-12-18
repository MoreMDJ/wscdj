<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title>测试</title>
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
<script type="text/javascript" src="/wx/js/echars.js"></script>
<script type="text/javascript">

</script>
</head>
<body class="loginbg">

<!-- header -->
<section class="login_header">
    <a href="javascript:history.go(-1)" class="hleft headleft header"><img src="/wx/images/leftbackwhite.png"> </a>
    <div class="htitle header colfff">注 册</div>
    <a class="hright headright header"></a>
</section>
<!-- header end -->

<!-- logo -->
<section class="login_logo">
    <img src="/wx/images/loginlogo.png">
</section>
<!-- logo end -->
<section>
   <div id="container" style="height:100%"></div>
</section>
<!-- textlinebox -->
<section class="textlinebox">
    <form action="/wx/join" onsubmit="return check();" method="POST">
    <span>
        <img src="/wx/images/phone.png">
        <input type="tel" onkeyup="onlyInt(this)" onafterpaste="onlyInt(this)"  maxlength="11" id="mobileId" datatype="n11-11" name="mobile" placeholder="手机号">
    </span>
    <span>
        <img src="/wx/images/pwd.png">
        <input type="text" maxlength="4" name="code" id="codeId" placeholder="验证码" class="login_pwd" style="width: 50%;">
        <input type="button" id="btnSmsCode" class="code"  value="获取验证码"></input>
    </span>
    <span>
        <img src="/wx/images/pwd.png">
        <input type="password" name="password" id="paswordId" placeholder="密码" class="login_pwd">
    </span>
        <!-- 条件符合后，登录样式用subyes，未符合用sub -->
        <input type="submit" value="注 册" class="sub" style="float: left;">
    </form>
</section>
<!-- textlinebox end -->

<section class="login_words">
    <a href="/wx/login" title="">登录</a>

</section>




</body>
</html>
