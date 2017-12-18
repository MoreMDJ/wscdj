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
    });
</script>
<link href="/management/js/themes/default/default.css" rel="stylesheet"></head>

<body class="mainbody">
<form method="post" action="/management/parameter/save" id="form1">
<div class="aspNetHidden">
<input type="hidden" name="VIEWSTATE" id="__VIEWSTATE" value="">
<input type="hidden" name="id" value="<#if parameter??><#if !action?? || action!="Copy">${parameter.id?c}</#if></#if>">
</div>
<!--导航栏-->
<div class="location">
  <a href="/management/parameter/list" class="back"><i></i><span>返回列表页</span></a>
  <a href="/management/center.aspx" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <a href="/management/parameter/list"><span>参数管理</span></a>
  <i class="arrow"></i>
  <span>编辑参数</span>
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
        	<#if category_list??>
            <#list category_list as item>
              <option value="${item.id?c}" <#if parameter?? && parameter.categoryId?? && parameter.categoryId==item.id>selected="selected"</#if>><#if item.layerId!=1><#list 2..item.layerId as idx>　</#list>├ </#if>${item.title!''}</option>
            </#list>
          </#if>
        </select>
      </div>
    </dd>
  </dl>
  <dl>
    <dt>参数名</dt>
    <dd>
      <input name="title" type="text" value="<#if parameter??>${parameter.title!''}</#if>" class="input normal" datatype="*2-100" sucmsg=" ">
      <span class="Validform_checktip">*最多100个字符</span>
    </dd>
  </dl>
  <dl>
    <dt>参数值输入类型</dt>
    <dd>
      <div class="rule-multi-radio">
        <span style="display: none;">
          <input type="radio" name="inputType" value="0" <#if !parameter?? || parameter.inputType==0>checked="checked"</#if>><label>手动输入</label>
          <input type="radio" name="inputType" value="1" <#if parameter?? && parameter.inputType==1>checked="checked"</#if>><label>在列表中选择</label>
        </span>
      </div>
    </dd>
  </dl>
  <dl>
    <dt>参数值选择类型</dt>
    <dd>
      <div class="rule-multi-radio">
        <span style="display: none;">
          <input type="radio" name="isMultiple" value="0" <#if !parameter?? || parameter.isMultiple==false>checked="checked"</#if>><label>单选</label>
          <input type="radio" name="isMultiple" value="1" <#if parameter?? && parameter.isMultiple==true>checked="checked"</#if>><label>多选</label>
        </span>
      </div>
      <span class="Validform_checktip">*仅在参数值输入类型为“在列表中选择”时生效</span>
    </dd>
  </dl>
  <dl>
    <dt>前台可进行筛选</dt>
    <dd>
      <div class="rule-multi-radio">
        <span style="display: none;">
          <input type="radio" name="isSearchable" value="0" <#if !parameter?? || parameter.isSearchable==false>checked="checked"</#if>><label>否</label>
          <input type="radio" name="isSearchable" value="1" <#if parameter?? && parameter.isSearchable==true>checked="checked"</#if>><label>是</label>
        </span>
      </div>
      <span class="Validform_checktip">*前台是否可以按该参数进行商品筛选, 仅在参数值输入类型为“在列表中选择”时生效</span>
    </dd>
  </dl>
  <dl>
    <dt>排序数字</dt>
    <dd>
      <input name="sortId" type="text" value="<#if parameter??>${parameter.sortId!'99'}<#else>99</#if>" class="input small" datatype="n" sucmsg=" ">
      <span class="Validform_checktip">*数字，越小越向前</span>
    </dd>
  </dl>
  <dl>
    <dt>参数值列表</dt>
    <dd>
      <textarea name="valueList" rows="2" cols="20" class="input" datatype="*0-255" sucmsg=" "><#if parameter??>${parameter.valueList!''}</#if></textarea>
      <span class="Validform_checktip">当参数值输入类型为"在列表中选择"时的参数值列表, 多个值以英文,分开</span>
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