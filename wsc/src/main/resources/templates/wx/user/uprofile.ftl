<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title>基本资料</title>
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
    <script type="text/javascript" charset="utf-8" src="/management/js/webuploader.min.js"></script>
    <script>
        window.onload=function(){
            setRem();
        }
        $(function(){
      	  var uploader = WebUploader.create({
              auto: true,
              swf: '/management/js/uploader.swf',
              server: '/management/tools/upload?action=headFile',
              pick: '#choice_photo',
              accept: {
                  title: 'Images',
                  extensions: 'gif,jpg,jpeg,bmp,png',
                  mimeTypes: 'image/*'
              }
          });
          
          //当文件上传成功时触发
          uploader.on('uploadSuccess', function (file, data) {
              
              if (data.status==0)
              {
            	  
              }
              else if (data.status==1)
              {
                  // 成功
                  setImage(data.path);
                  $(".webuploader-pick").html('<img src="'+data.path+'">');
              }
              uploader.removeFile(file); //从队列中移除
          });
          function setImage(uri)
          {
              $("#headImage").attr("src", uri);
              $("#imageInput").val(uri);
          }
          
      });
        
        function ajaxSubmit()
        {
          $.ajax({
              data:$('#form1').serialize(),
              url : '/wx/u/profile/save',
              method : 'POST',
              success: function(res) {
                  if (res.error == 0)
                  {
                      warning("保存成功");
                  }
                  else
                  {
                      warning(res.msg);
                  }
              }
          });
        }
    </script>
</head>
<body>
<div class="top_size"></div>
<div class="clear"></div>
<#-- 引入警告提示样式 -->
<#include "/common/common_warn.ftl">
<#-- 引入等待提示样式 -->
<#include "/common/common_wait.ftl">

<!-- header -->
<section class="white_header">
    <a href="/wx/u" title="" class="hleft headleft header"><img src="/wx/images/leftback.png"> </a>
    <div class="htitle header">基本资料</div>
    <a href="javascript:ajaxSubmit()" title="" class="hright headright header">保存</a>
</section>
<!-- header end -->

<form id="form1">
<section class="center_infobox">
    <dl>
        <dt>头像</dt>
        <dd class="center_infobox_pic">
            <span id="choice_photo" class="filespan">
            <img id="headImage" src="<#if user?? && user.headImage?? && ""!=user.headImage>${user.headImage}<#else>/wx/images/user_head_default.png</#if>"/>
            </span>
            <input id="imageInput" type="hidden" name="headImage" value="<#if user??&& user.headImage?? && ""!=user.headImage>${user.headImage}<#else>/wx/images/user_head_default.png</#if>" />
            <#--<input type="file" class="filebtn" />-->
        </dd>
    </dl>
    <dl>
        <dt>用户名</dt>
        <!--center_infobox_nomore为没有右边箭头更多-->
        <dd class="center_infobox_nomore">${user.username!''}</dd>
    </dl>
    <dl>
        <dt>昵称</dt>
        <dd><input type="text" name="nickname" value="${user.nickname!''}" placeholder="昵称" class="text center_infobox_text"></dd>
    </dl>
    <dl>
        <dt>性别</dt>
        <dd>
            <select name="sex" class="center_infobox_select">
                <option value="3" <#if user.sex=3>checked="checked"</#if>>保密</option>
                <option value="1" <#if user.sex=2>checked="checked"</#if>>男</option>
                <option value="2" <#if user.sex=1>checked="checked"</#if>>女</option>
            </select>
        </dd>
    </dl>
    <dl>
        <dt>出生年月</dt>
        <dd><input type="date" name="birthday" value="<#if user.birthday??>${user.birthday?string('yyyy-MM-dd')}</#if>" class="center_infobox_text" style="margin-right: -.5rem;"></dd>
    </dl>

    <dl>
        <dt>修改密码</dt>
        <dd><a href="/wx/login/passwordReset" title="" style="display: block;height: .8rem;width: 3rem;">&nbsp;</a> </dd>
    </dl>



</section>
</form>

</body>
</html>
