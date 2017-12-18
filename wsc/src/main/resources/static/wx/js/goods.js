//改变商品数量的方法
function changeQuantity(goodsId, type)
{
	// 获取指定商品显示数量的输入框的id
	var quantityElementId = "#quantity" + goodsId;
	// 获取当前指定商品选择的数量
	var quantity = $(quantityElementId).val();
	// 获取商品的库存量的标签的id
	var inventoryId = "#inventory" + goodsId;
	// 获取当前商品的库存量
	var inventory = $(inventoryId).val();

	// 如果是减少当前商品的数量
	if ("sub" == type) {
		// 如果当前商品的数量已经是0了就不做任何处理
		if (1 >= parseInt(quantity)) {
			$(quantityElementId).val(1)
			warning("亲，不能再少啦");
			updataPrice();
			return;
		}else{
			// 正常减少数量
			quantity = (isNaN(parseInt(quantity))?2:parseInt(quantity)) - 1;
		}
	}

	// 如果是增加商品数量的情况
	if ("add" == type) {
		// 如果商品数量已经等于库存量之后就不做任何处理
		if (inventory <= parseInt(quantity)) {
			$(quantityElementId).val(inventory)
			warning("亲，库存只有这么多啦");
			updataPrice();
			return;
		}
		else{
			// 正常增加数量
			quantity = (isNaN(parseInt(quantity))?0:parseInt(quantity)) + 1;
		}
	}
	if(parseInt(quantity)>parseInt(inventory)){
		// 设置为最大库存
		$(quantityElementId).val(inventory);
		warning("亲，库存只有这么多啦");
		updataPrice();
		return;
	}

	// 把更新后的商品信息和已选数量显示到页面上
	$(quantityElementId).val(quantity);
	updataPrice();
}

function updataPrice()
{	
	var totalPrice = $("#totalPrice").val();

	var subBox = $(".car_list input[name='ckIds']:checked");
	var total = 0;
	subBox.each(function(){
		var quatity = $("#quantity" + $(this).val()).val();
		var price = Number($("#sprice" + $(this).val()).val());
		total = total + Number(quatity * price);
	});
	$("#totalPrice").html(total.toFixed(2));
	$("#inputTotalPrice").val(total.toFixed(2));
	
	
}

function order()
{
	var ckBox = $(".car_list input[name='ckIds']:checked");
	var number = $(".car_list input[name='ckIds']:checked").length;
	if(isNull(number) || number < 1)
	{
		warning("请选择要购买的商品");
		return;
	}

	var isNullNo = false;
	var isZero = false;
	ckBox.each(function(){
		var quantity = $("#quantity" + $(this).val()).val();
		if(isNull(quantity))
		{
			isNullNo = true;
			this.focus();
			return;
		}
		if(quantity <= 0)
		{
			isZero = true;
			return;
		}
	});
	if(isNullNo)
	{
		warning("请填写购买的数量");
		return;
	}
	if(isZero)
	{
		warning("请输入正确的数量");
		return;
	}


	$("#formId").submit();
}

// 查看是否在限购内
function checkLimitGoods()
{
	var data = $("#formId").serializeArray();
	$.post("/wx/cart/checklimit",data,function(res){
		if(res.status == 1)
			order();
		else
			warning(res.msg);
	});
}

function limitBack(data)
{
	if(data.status == 1)
		order();
	else
		warning(data.msg);
}
