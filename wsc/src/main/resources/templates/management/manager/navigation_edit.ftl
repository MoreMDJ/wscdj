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
        
        //初始化上传控件
        $(".upload-img").InitUploader({ filesize: "10240", sendurl: "/management/tools/upload", swf: "/management/js/uploader.swf", filetypes: "gif,jpg,png,bmp,rar,zip,doc,xls,txt" });
    });
</script>
<link href="/management/js/themes/default/default.css" rel="stylesheet"></head>

<body class="mainbody">
<form method="post" action="/management/navigation/save" id="form1">
<div class="aspNetHidden">
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="">
<input type="hidden" name="id" value="<#if navigation??><#if !action?? || action!="Copy">${navigation.id?c}</#if></#if>">
</div>
<!--导航栏-->
<div class="location">
  <a href="/management/navigation/list" class="back"><i></i><span>返回列表页</span></a>
  <a href="/management/center.aspx" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <a href="/management/navigation/list"><span>内容管理</span></a>
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
    <dt>父类别</dt>
    <dd>
      <div class="rule-single-select">
        <select name="parentId" datatype="n0-99" sucmsg=" " nullmsg="请选择！" class="Validform_error" style="display: none;">
  	      <option value="0">无父类别</option>
        	<#if menu_list??>
            <#list menu_list as item>
              <option value="${item.id?c}" <#if parent?? && parent.id==item.id || navigation?? && navigation.parentId?? && navigation.parentId==item.id>selected="selected"</#if>><#if item.layerId?? && item.layerId gt 1><#list 2..item.layerId as idx>　</#list>├ </#if>${item.title!''}</option>
            </#list>
          </#if>
        </select>
      </div>
    </dd>
  </dl>
  <dl>
    <dt>开启</dt>
    <dd>
      <div class="rule-multi-radio">
        <span style="display: none;">
          <input type="radio" name="isEnable" value="1" <#if navigation?? && navigation.isEnable?? && navigation.isEnable>checked="checked"</#if>><label>是</label>
          <input type="radio" name="isEnable" value="0" <#if navigation?? && navigation.isEnable?? && navigation.isEnable><#else>checked="checked"</#if>><label>否</label>
        </span>
      </div>
    </dd>
  </dl>
  <dl>
    <dt>标题</dt>
    <dd>
      <input name="title" type="text" value="<#if navigation??>${navigation.title!''}</#if>" class="input normal" datatype="*2-100" sucmsg=" ">
      <span class="Validform_checktip">*标题最多100个字符</span>
    </dd>
  </dl>
  <dl>
    <dt>Logo图片</dt>
    <dd>
      <input name="iconUri" id="coverImgUri" type="text" value="<#if navigation??>${navigation.iconUri!''}</#if>" class="input normal upload-path">
      <div class="upload-box upload-img"></div>
      <div class="img-box div-img-box"></div>
    </dd>
  </dl>
  <dl>
    <dt>排序数字</dt>
    <dd>
      <input name="sortId" type="text" value="<#if navigation??>${navigation.sortId!'99'}<#else>99</#if>" class="input small" datatype="n" sucmsg=" ">
      <span class="Validform_checktip">*数字，越小越向前</span>
    </dd>
  </dl>
  <dl>
    <dt>英文名称</dt>
    <dd>
      <input name="name" type="text" maxlength="100" <#if navigation?? &&navigation.name??>value="${navigation.name!''}" ajaxurl="/management/tools/check?action=navigation_conform&param1=${navigation.name!''}"<#else>ajaxurl="/management/tools/check?action=navigation_conform"</#if> class="input normal" datatype="*2-100" sucmsg=" ">
      <span class="Validform_checktip">权限控制名称,只允许数字.字母.下划线 100个字符以内</span>
    </dd>
  </dl>
  <dl>
    <dt>URL链接</dt>
    <dd>
      <input name="linkUri" type="text" maxlength="255" value="<#if navigation??>${navigation.linkUri!''}</#if>" class="input normal">
      <span class="Validform_checktip">当前管理目录，有子导航不用填</span>
    </dd>
  </dl>
  <dl>
    <dt>备注说明</dt>
    <dd>
      <textarea name="remark" rows="2" cols="20" id="remark" class="input"></textarea>
      <span class="Validform_checktip">非必填，可为空</span>
    </dd>
  </dl>
  <dl>
    <dt>权限资源</dt>
    <dd>
		<div class="rule-multi-porp multi-porp">
			<#list actionType?keys as key>
			 <input type="checkbox" name="actionType" value="${key!""}" <#if navigation?? && navigation.actionType?? && navigation.actionType?contains(key)>checked="checked"</#if>>
             <label>${actionType[key]}(${key})</label>
             </#list>
		</div>
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