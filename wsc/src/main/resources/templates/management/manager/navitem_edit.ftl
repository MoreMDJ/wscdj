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

        // blur事件
        $("input.upload-path").blur(function(){
          if (null != $(this).val())
          {
            var newLi = "";
            if (""!=$(this).val())
            { 
              newLi = $('<ul><li>'
                        + '<div class="img-box">'
                        + '<img src="' + $(this).val() + '" />'
                        + '</div>'
                        + '</li></ul>');
            }
           
                          
            $(this).siblings(".photo-list").html(newLi);
          }
        });
        
        // 触发blur事件
        $('input.upload-path').trigger("blur");
        
        //初始化上传控件
        $(".upload-img").InitUploader({ filesize: "10240", sendurl: "/management/tools/upload", swf: "/management/js/uploader.swf", filetypes: "gif,jpg,png,bmp,rar,zip,doc,xls,txt" });
        $(".upload-video").InitUploader({ filesize: "102400", sendurl: "/management/tools/upload", swf: "/management/js/uploader.swf", filetypes: "flv,mp4,avi" });
        $(".upload-album").InitUploader({ btntext: "批量上传", multiple: true, water: true, thumbnail: true, filesize: "10240", sendurl: "/management/tools/upload", swf: "../../scripts/webuploader/uploader.swf" });
    });
</script>
<link href="/management/js/themes/default/default.css" rel="stylesheet"></head>

<body class="mainbody">
<form method="post" action="/management/navitem/save" id="form1">
<div class="aspNetHidden">
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="">
<input type="hidden" name="id" value="<#if navitem??><#if !action?? || action!="Copy">${navitem.id?c}</#if></#if>">
</div>
<!--导航栏-->
<div class="location">
  <a href="/management/navitem/list" class="back"><i></i><span>返回列表页</span></a>
  <a href="/management/center.aspx" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <a href="/management/navitem/list"><span>内容管理</span></a>
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
        <select name="categoryId" datatype="*" sucmsg=" " nullmsg="请选择！" class="Validform_error" style="display: none;">
  	      <option value="">请选择类别...</option>
  	      <option value="0" <#if navitem?? && navitem.categoryId?? && navitem.categoryId==0>selected="selected"</#if>>触屏版导航菜单</option>
  	      <option value="1" <#if navitem?? && navitem.categoryId?? && navitem.categoryId==1>selected="selected"</#if>>触屏版医生导航菜单</option>
        </select>
      </div>
    </dd>
  </dl>
  <dl>
    <dt>显示状态</dt>
    <dd>
      <div class="rule-multi-radio">
        <span style="display: none;">
          <input type="radio" name="isEnable" value="1" <#if !navitem?? || navitem.isEnable>checked="checked"</#if>><label>显示</label>
          <input type="radio" name="isEnable" value="0" <#if navitem?? && !navitem.isEnable>checked="checked"</#if>><label>不显示</label>
        </span>
      </div>
    </dd>
  </dl>
  <dl>
    <dt>名称</dt>
    <dd>
      <input name="title" type="text" maxlength=100 value="<#if navitem??>${navitem.title!''}</#if>" class="input normal" datatype="*2-100" sucmsg=" ">
      <span class="Validform_checktip">*标题最多100个字符</span>
    </dd>
  </dl>
  <dl>
    <dt>显示图片</dt>
    <dd>
      <input name="imgUrl" id="imgUri1" type="text" value="<#if navitem??>${navitem.imgUrl!''}</#if>" class="input normal upload-path">
      <div class="upload-box upload-img"></div>
      <div class="photo-list"></div>
    </dd>
  </dl>
  <dl>
    <dt>URL链接</dt>
    <dd>
      <input name="linkUrl" type="text" maxlength="255" value="<#if navitem??>${navitem.linkUrl!''}</#if>" class="input normal">
      <span class="Validform_checktip">填写后直接跳转到该网址</span>
    </dd>
  </dl>
  <dl>
    <dt>显示窗口</dt>
    <dd>
      <div class="rule-multi-radio">
        <span style="display: none;">
          <input type="radio" name="isBlank" value="0" <#if !navitem?? || !navitem.isBlank>checked="checked"</#if>><label>当前窗口</label>
          <input type="radio" name="isBlank" value="1" <#if navitem?? && navitem.isBlank>checked="checked"</#if>><label>新窗口</label>
        </span>
      </div>
    </dd>
  </dl>
  <dl>
    <dt>简介</dt>
    <dd>
      <textarea name="summary" rows="2" cols="20" class="input" datatype="*0-255" sucmsg=" "><#if navitem??>${navitem.summary!''}</#if></textarea>
      <span class="Validform_checktip">255个字符以内</span>
    </dd>
  </dl>
  <dl>
    <dt>排序数字</dt>
    <dd>
      <input name="sortId" type="text" value="<#if navitem??>${navitem.sortId!'99'}<#else>99</#if>" class="input small" datatype="n" sucmsg=" ">
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