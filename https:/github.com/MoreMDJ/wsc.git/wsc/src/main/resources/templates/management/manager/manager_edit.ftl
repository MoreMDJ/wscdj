<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,initial-scale=1.0,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<title>编辑内容</title>
<link href="/management/css/ui-dialog.css" rel="stylesheet" type="text/css">
<link href="/management/css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" charset="utf-8" src="/management/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/WdatePicker.js"></script>
<link href="/management/js/skin/WdatePicker.css" rel="stylesheet" type="text/css">
<script type="text/javascript" charset="utf-8" src="/management/js/dialog-plus-min.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/webuploader.min.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/kindeditor-min.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/uploader.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/laymain.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/common.js"></script>
<script type="text/javascript">
    $(function () {
        //初始化表单验证
        $("#form1").initValidform();

        //初始化编辑器
        var editor = KindEditor.create('.editor', {
            width: '100%',
            height: '350px',
            filterMode: false, //默认不过滤HTML
            resizeType: 1,
            uploadJson: '/management/tools/upload?action=EditorFile&IsWater=1',
            fileManagerJson: '/management/tools/upload?action=ManagerFile',
            allowFileManager: true
        });
        var editorMini = KindEditor.create('.editor-mini', {
            width: '100%',
            height: '250px',
            filterMode: false, //默认不过滤HTML
            resizeType: 1,
            uploadJson: '/management/tools/upload?action=EditorFile&IsWater=1',
            items: [
				'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
				'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
				'insertunorderedlist', '|', 'emoticons', 'image', 'link']
        });

        // 缩略图自动添加
        $("#coverImgUri").change(function(){
          var imgUri = $("#coverImgUri").val();
          if (imgUri == "" || imgUri == null) {
              $(".div-img-box").hide();
          }
          else {
              $(".div-img-box").html('<img src="'+ imgUri +'" width="70" height="70">');
              $(".div-img-box").show();
          }
        });
        
        // 初始化缩略图
        var imgUri = $("#coverImgUri").val();
        if (imgUri == "" || imgUri == null) {
          $(".div-img-box").hide();
        }
        else {
          $(".div-img-box").html('<img src="'+ imgUri +'" width="70" height="70">');
          $(".div-img-box").show();
        }
        
        //初始化上传控件
        $(".upload-img").InitUploader({ filesize: "10240", sendurl: "/management/tools/upload", swf: "/management/js/uploader.swf", filetypes: "gif,jpg,png,bmp,rar,zip,doc,xls,txt" });
        $(".upload-video").InitUploader({ filesize: "102400", sendurl: "/management/tools/upload", swf: "/management/js/uploader.swf", filetypes: "flv,mp4,avi" });
        $(".upload-album").InitUploader({ btntext: "批量上传", multiple: true, water: true, thumbnail: true, filesize: "10240", sendurl: "/management/tools/upload", swf: "../../scripts/webuploader/uploader.swf" });
    });
</script>
<link href="/management/js/themes/default/default.css" rel="stylesheet"></head>

<body class="mainbody">
<form method="post" action="/management/manager/save" id="form1">
<div class="aspNetHidden">
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="">
<input type="hidden" name="id" value="<#if djManager??>${djManager.id?c}</#if>">
</div>
<!--导航栏-->
<div class="location">
  <a href="/management/manager/list" class="back"><i></i><span>返回列表页</span></a>
  <a href="/management/center" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <a href="/management/manager/list"><span>内容管理</span></a>
  <i class="arrow"></i>
  <span>编辑内容</span>
</div>
<div class="line10"></div>
<!--/导航栏-->

<!--内容-->
<div id="floatHead" class="content-tab-wrap" style="height: 42px;">
  <div class="content-tab">
    <div class="content-tab-ul-wrap">
      <div class="tab-title"><span>基本信息</span><i></i></div><ul>
        <li><a class="selected" href="javascript:;">基本信息</a></li>
      </ul>
    </div>
  </div>
</div>

<div class="tab-content" style="display: block;">
  <dl>
    <dt>所属类别</dt>
    <dd>
      <div class="rule-single-select">
        <select id="roleId" name="roleId" datatype="*" sucmsg=" " nullmsg="请选择！" class="Validform_error" style="display: none;">
  	      <option value="">请选择角色...</option>
  	      <#if role_list??>
  	      <#list role_list as item>
  	         <option value="${item.id?c}" <#if djManager?? && djManager.roleId?? && djManager.roleId==item.id>selected="selected"</#if>>${item.title!}</option>
          </#list>
  	      </#if>
  	    </select>
      </div>
    </dd>
  </dl>
  <dl>
    <dt>用户名</dt>
    <dd>
      <input name="username" type="text" <#if djManager??>value="${djManager.username!''}"<#else>ajaxurl="/management/tools/check?action=manager_conform"</#if> class="input normal" datatype="*6-20" sucmsg=" ">
      <span class="Validform_checktip">*6-20个字符</span>
    </dd>
  </dl>
  <dl>
    <dt>密码</dt>
    <dd>
      <input type="password" name="password" <#if djManager??>value="${djManager.password!''}"</#if> class="input normal" datatype="*6-20" sucmsg=" ">
      <span class="Validform_checktip">*6-20个字符，新增后不可修改</span>
    </dd>
  </dl>
  <dl>
    <dt>确认密码</dt>
    <dd><input name="txtPassword1" type="password"  class="input normal" datatype="*" recheck="password" nullmsg="请再输入一次密码" errormsg="两次输入的密码不一致" sucmsg=" " <#if djManager??>value="${djManager.password!''}"</#if>> <span class="Validform_checktip Validform_right"> </span></dd>
  </dl>
  <dl>
  <dl>
    <dt>姓名</dt>
    <dd><input name="realName" type="text" value="<#if djManager??>${djManager.realName!''}</#if>" class="input normal"></dd>
  </dl>
  <dl>
    <dt>电话</dt>
    <dd><input name="telephone" type="text" value="<#if djManager??>${djManager.telephone!'-'}</#if>" class="input normal"></dd>
  </dl>
  <dl>
    <dt>邮箱</dt>
    <dd><input name="email" type="text" value="<#if djManager??>${djManager.email!'-'}</#if>" class="input normal"></dd>
  </dl>
    <dt>是否开启</dt>
    <dd>
      <div class="rule-multi-radio">
        <span style="display: none;">
          <input type="radio" class="typeRadio" name="isEnable" value="1" <#if djManager?? && djManager.isEnable?? && djManager.isEnable>checked="checked"</#if>><label>开启</label>
          <input type="radio" class="typeRadio" name="isEnable" value="0" <#if !djManager?? || !djManager.isEnable?? || !djManager.isEnable>checked="checked"</#if>><label>关闭</label>
        </span>
      </div>
    </dd>
  </dl>
  <dl>
    <dt>排序数字</dt>
    <dd>
      <input name="sortId" type="text" value="<#if djManager??>${djManager.sortId!'99'}<#else>99</#if>" class="input small" datatype="n" sucmsg=" ">
      <span class="Validform_checktip">*数字，越小越向前</span>
    </dd>
  </dl>
</div>
<!--/内容-->

<!--工具栏-->
<div class="page-footer">
  <div class="btn-wrap" style="position: static;">
    <input type="submit" name="btnSubmit" value="提交保存" id="btnSubmit" class="btn">
    <input name="btnReturn" type="button" value="返回上一页" class="btn yellow" onclick="javascript:history.back(-1);">
  </div>
</div>
<!--/工具栏-->

</form>

</body></html>