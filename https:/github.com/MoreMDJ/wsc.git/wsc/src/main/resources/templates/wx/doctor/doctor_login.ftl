<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN">
<title>医生登陆</title>
<meta name="keywords" content="">
<meta name="description" content="医生登陆界面">
<meta name="copyright" content="" />
<!--[if IE]>
   <script src="js/html5.js"></script>
<![endif]-->
<script type="text/javascript" src="/wx/js/jquery-2.1.0.min.js"></script>
<!--<script type="text/javascript" src="/wx//js/common.js"></script>-->


<link href="/wx/css/pc_common.css" rel="stylesheet" type="text/css" />

<link href="/wx/css/pc_doctor.css" rel="stylesheet" type="text/css" />


</head>

<body style=" background-image: url(/wx/images/pc_doctor/login-bg.png);">

 
  <!-- 登录 START -->
  <div class="login_main">
  	<div class="bg_top"><img src="/wx/images/pc_doctor/bg_top.png"></div>
  	
  	<div class="login_box">
	<form action="/wx/doctor/login" method="POST">
      <menu class="tit">
      	用户登录
      </menu>
      <div id="boxtent">
      	<div class="cont">
          <ul>
            <li class="border">
              <img src="/wx/images/pc_doctor/icon01.png" />
              <input class="text" type="text" <#if username??>value="${username!''}"</#if> name="username" placeholder="用户名/手机号/邮箱" />
            </li>
            <li class="border<#if error??> wrong</#if>">
              <img src="/wx/images/pc_doctor/icon02.png" />
              <input class="text" type="password" name="password" placeholder="请输入密码" />
              <#if error??><p>您输入的密码有误，请重新输入</p></#if>
            </li>
           <#-- <li class="border m_code">
            	<img src="/wx/images/pc_doctor/icon03.png" />
              <input class="text" type="text" name="code" placeholder="请输入验证码" />
              <a href="#"><img src="/wx/images/pc_doctor/code01.png" /></a>
            </li>-->
            <li class="item"><label><a class="checkbox" onclick="$(this).toggleClass('ok')"><input type="checkbox" name="cate" value="1" /></a>记住我的登录状态</label></li>
          </ul>
          <menu class="btm">
            <input class="sub ok" type="submit" value="登录" />
          </menu>
        </div>
      </div>
      <!-- boxtent END -->
      
    </div><!-- login_box END -->
  
  	
    <div class="bg_left"><img src="/wx/images/pc_doctor/bg_left.png"></div>

    <div class="bg_right"><img src="/wx/images/pc_doctor/bg_right.png"></div>

    <div class="bg_bottom"><img src="/wx/images/pc_doctor/bg_bottom.png"></div>



  
  </div>
  <!-- 登录 END -->
  </form>

 
</body>
</html>
