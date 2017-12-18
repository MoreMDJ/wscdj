<!DOCTYPE html>
<html><head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,initial-scale=1.0,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<title>管理员登录</title>
<link href="/management/css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/management/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript">
$(function () {
    if (window.name == "mainframe")
    {
      //top.location.reload();
    }
    
    //检测IE
    if ('undefined' == typeof (document.body.style.maxHeight)) {
        window.location.href = '/management/tools/ie6update';
    }
});
</script>
</head>

<body class="loginbody">
<form method="post" action="/management/login" id="form1">
<div class="aspNetHidden">
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="" >
</div>
<div style="width:100%; height:100%; min-width:300px; min-height:260px;"></div>
<div class="login-wrap">
  <#--<div class="login-logo">LOGO</div>-->
  <div class="login-form">
    <div class="col">
      <input name="username" type="text" id="username" class="login-input" placeholder="管理员账号" title="管理员账号" value="">
      <label class="icon user" for="txtUserName"></label>
    </div>
    <div class="col">
      <input name="password" type="password" id="txtPassword" class="login-input" placeholder="管理员密码" title="管理员密码" value="">
      <label class="icon pwd" for="txtPassword"></label>
    </div>
    <div class="col">
      <input type="submit" name="btnSubmit" value="登 录" id="btnSubmit" class="login-btn">
    </div>
  </div>
  <div class="login-tips"><i></i><p id="msgtip">${error!"请输入用户名和密码"}</p></div>
</div>

<div class="copy-right">
  <p>版权所有 重庆真心善美医药有限公司 Copyright © 2016 - 2020. All Rights Reserved.</p>
</div>
</form>


</body></html>