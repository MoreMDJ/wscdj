<!DOCTYPE html>
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,initial-scale=1.0,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<title>修改收货信息窗口</title>
<link href="/management/css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" charset="utf-8" src="/management/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/PCASClass.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/common.js"></script>
<script type="text/javascript">
    var api = top.dialog.get(window); //获取窗体对象
    var W = api.data;
    //页面加载完成执行
    $(function () {
        //设置按钮及事件
        api.button([{
            value: '确定',
            callback: function () {
                submitForm();
            },
            autofocus: true
        }, {
            value: '取消',
            callback: function () { }
        }]);
        //初始化省市区
        var mypcas = new PCAS("province,所属省份", "city,所属城市", "district,所属地区");
        var areaArr = $("#spanAreaId", W.document).val().split(",");
        var acceptName = $("#acceptNameId", W.document).text();
        var spanMobile = $("#spanMobile", W.document).text();
        var address = $("#addressId", W.document).val();
        $("#detailAddress").val(address);
        $("#shippingPhone").val(spanMobile);
        $("#shippingName").val(acceptName);
        if (areaArr.length == 3){
            mypcas.SetValue(areaArr[0], areaArr[1], areaArr[2]);
        }
    });

    //提交表单处理
    function submitForm() {
        var currDocument = $(document); //当前文档
        //验证表单
        if ($("#shippingName").val() == "") {
            top.dialog({
                title: '提示',
                content: '请填写收货人姓名！',
                okValue: '确定',
                ok: function () { },
                onclose: function () {
                    $("#shippingName", currDocument).focus();
                }
            }).showModal(api);
            return false;
        }
        if ($("#district").val() == "") {
            top.dialog({
                title: '提示',
                content: '请选择所在地区！',
                okValue: '确定',
                ok: function () { },
                onclose: function () {
                    $("#province", currDocument).focus();
                }
            }).showModal(api);
            return false;
        }
        if ($("#detailAddress").val() == "") {
            top.dialog({
                title: '提示',
                content: '请填写详细的送货地址！',
                okValue: '确定',
                ok: function () { },
                onclose: function () {
                    $("#detailAddress", currDocument).focus();
                }
            }).showModal(api);
            return false;
        }
        if ($("#shippingPhone").val() == "") {
            top.dialog({
                title: '提示',
                content: '请填写电话！',
                okValue: '确定',
                ok: function () { },
                onclose: function () {
                    $("#shippingPhone", currDocument).focus();
                }
            }).showModal(api);
            return false;
        }
        //下一步，AJAX提交表单
        var postData = {
            "orderNo": "${orderNo!''}", 
            "action": "editUInfo",
            "shippingName": $("#shippingName").val(), 
            "province": $("#province").val(),
            "city": $("#city").val(), 
            "district": $("#district").val(), 
            "detailAddress": $("#detailAddress").val(),
            "shippingPhone": $("#shippingPhone").val()
        };
        //发送AJAX请求
        W.sendAjaxUrl(api, postData, "/management/order/update");
        return false;
    }

</script>
</head>

<body>
<div class="div-content">
    <dl>
      <dt>收件人姓名</dt>
      <dd><input type="text" id="shippingName" value="<#if order??>${order.shippingName!}</#if>" class="input txt"> <span class="Validform_checktip">*</span></dd>
    </dl>
    <dl>
      <dt>所属省市</dt>
      <dd>
        <select id="province" name="province" class="select"></select>
        <select id="city" name="city" class="select"></select>
        <select id="district" name="district" class="select"></select>
        <span class="Validform_checktip"></span>
      </dd>
      
    </dl>
    <dl>
      <dt>详细地址</dt>
      <dd><input type="text" id="detailAddress" value="<#if order??>${order.detailAddress!}</#if>" class="input normal"> <span class="Validform_checktip">*</span></dd>
    </dl>
    <dl>
      <dt>电话</dt>
      <dd><input type="text" id="shippingPhone" value="<#if order??>${order.shippingPhone!}</#if>" class="input txt"> <span class="Validform_checktip">*</span></dd>
    </dl>
</div>


</body></html>