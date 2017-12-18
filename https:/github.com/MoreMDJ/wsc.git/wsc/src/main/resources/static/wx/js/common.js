//common js
$(function(){
	pageSize();//页面计算 rem
	newsScroll ();//新闻滚动
//	$.scrollPage();
	menuOut ();
	navChange ();
	listNav ();
});

//计算rem单位
function pageSize()
{
	$(document).ready(function(){
		setRootSize();
	});
	function setRootSize() {
		var deviceWidth = document.documentElement.clientWidth; 
		if(deviceWidth>640){deviceWidth = 640;}  
		document.documentElement.style.fontSize = deviceWidth / 6.4 + 'px';
	}
	setRootSize();
	window.addEventListener('resize', function () {
	    setRootSize();
	}, false);
};

//滚动加载
function newsScroll () {
	var timer=null,
		num = 0,
		l = $('.dynamic div span').size();
		$('.dynamic div span').eq(0).clone().appendTo($('.dynamic div'));
		l = l+1;
	timer = setInterval(function(){
		if(num == 0){
			$('.dynamic div').addClass('animate');
		};
		num++;
		if(num>l-1){
			$('.dynamic div').removeClass('animate');
			num=0;
		};
		$('.dynamic div').css({top:-num*0.77 + 'rem'});
	},4000)
}

///////////////////////////////////////封装  goods_list 分类选择
function myTab(one,two,three){
	one.click(function(){
		if(two.hasClass('onOff')){
			two.fadeOut(600).removeClass("onOff");
		}else{
			two.fadeIn(600).addClass("onOff");
		};
		if(three){
			three.fadeOut(600).removeClass("onOff");
		};
	});
}
function myChange (one,two,three) {
	one.eq(0).show();
	two.eq(0).addClass(three);
	two.each(function(i,o){
		$(o).click(function(){
			one.hide();
			one.eq(i).show();
			two.removeClass(three);
			two.eq(i).addClass(three);
		});
	});
}
function activeDown (a,b,c) {
	a.each(function(i,o){
		$(o).click(function(){
			b.removeClass('onOff');
			b.fadeOut(600);
			a.removeClass(c);
			$(o).addClass(c);
		});
	});
}
///////////////////////////////////////封装

//筛选
function listNav () {
    myTab($('.goods_lis_type a').eq(0),$('.goods_lis_type1'),$('.goods_lis_type2'));
    myChange($('.type1_ul2'),$('.type1_ul1').find('li'),"type1_choiced");
    myTab($('.goods_lis_type a').eq(1),$('.goods_lis_type2'),$('.goods_lis_type1'));
    activeDown($('.goods_lis_type1 .type1_ul2 li'),$('.goods_lis_type1'),'type1_ul2_choiced');
    activeDown($('.goods_lis_type2 .type1_ul3 li'),$('.goods_lis_type2'),'type1_ul2_choiced');
}


//上方按钮弹出
function menuOut () {
	myTab($('.headright'),$('.rightmore'));
	$('.rightmore .rightmore_box a').each(function(i,o){
		$(o).click(function(){
			$('.rightmore').fadeOut(600);
		});
	});
}

//选择项调用
function navChange () {
	myChange($('.type-right-scroll'),$('.type-left-scroll').find('dl'),"active-type");
}

function left_del(val){
	var delh=getClassName(document,val);
	for(var i=0;i<delh.length;i++){
		delgo(delh[i]);
	}
	function delgo(obj){
		var childTag=obj.children[0].tagName;
		var oLi=obj.getElementsByTagName(childTag);
		var left0;
		for(var j=0;j<oLi.length;j++){
			var delClick=oLi[j].getElementsByTagName('menu')[0];
			var bW=delClick.offsetWidth;
			if(delClick){
				oLi[j].addEventListener('touchstart',function(event){
					this.style.transition='1s';
					var touch=event.changedTouches[0];
					left0=touch.pageX;
				},false)
				oLi[j].addEventListener('touchmove',function(event){
					var touch=event.changedTouches[0];
					nowleft=touch.pageX;
					if(nowleft-left0<-30){
						this.style.transform='translateX('+(-bW)+'px)';
					}
					if(nowleft-left0>30){
						this.style.transform='translateX('+0+'px)';
					}
				},false)
				delClick.onclick=function(){
					obj.removeChild(this.parentNode);
				}
			}
		}
	}
}


//------------------------删除左滑------------------------
// 通过class获取元素
function getClassName(obj,sName){
	var aTmp = obj.getElementsByTagName('*');
	var aRes=[];
	var arr =[];
	for(var i=0;i<aTmp.length;i++){
		arr = aTmp[i].className.split(' ');
		for (var j=0;j<arr.length;j++){
			if(arr[j] == sName){
				aRes.push(aTmp[i]);
			}
		}
	}
	return aRes;
}

// 为元素添加class
function addClass(obj,sName){
	if(obj.className==''){
		obj.className=sName;
	}else{
		var arrClass=obj.className.split(' ');
		var dure=arrHasValue(arrClass,sName);//审查一个数组中是否含有某个值
		if(dure==-1){
			obj.className+=' '+sName;
		}
	}
}

// 为元素移除class
function removeClass(obj,sName){
	if(obj.className!=''){
		var arrClass=obj.className.split(' ');
		var dure=arrHasValue(arrClass,sName);// 审查一个数组中是否含有某个值
		if(dure!=-1){
			arrClass.splice(dure,1);
			obj.className=arrClass.join(' ');
		}
	}
}

// 设置触屏版的单位rem
function setRem(){
	var resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize';//判断屏幕旋转还是resize
	function setRem2(){
		var dWidth=document.documentElement.clientWidth;
		if(dWidth>640){dWidth=640}
		document.documentElement.style.fontSize=dWidth/6.4+'px';
	}
	setRem2();
	window.addEventListener(resizeEvt,function(){
		setRem2();
	},false)
}


//------------------------删除左滑 end------------------------


//------------------------mdj start------------------------
/**
 * 只能输入数字和小数
 * @param obj
 * @returns
 */
function onlyFloat(ob){
	if(isNull(ob.t_value))
		ob.t_value="1";

	if (!ob.value.match(/^[\+\-]?\d*?\.?\d*?$/))
		ob.value = ob.t_value;
	else
		ob.t_value = ob.value;

	if (ob.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))
		ob.o_value = ob.value;
}

/**
 * 只能输入数字
 * @param obj
 * @returns
 */
function onlyInt(ob){
	if(isNull(ob.t_value))
		ob.t_value="1";
	
	if (!ob.value.match(/^[\+\-]?\d*$/))
		ob.value = ob.t_value;
	else
		ob.t_value = ob.value;

	if (ob.value.match(/^(?:[\+\-]?\d+)?$/))
		ob.o_value = ob.value;
}
/**
 * 只能输入英文和汉字
 * @param obj
 * @returns
 */
function onlyName(ob){
	if(isNull(ob.t_value))
		ob.t_value="";
	
	if (!ob.value.match(/^([A-Za-z]|[\u4e00-\u9fa5]|^\s*$)+?$/))
		ob.value = ob.t_value;
	else
		ob.t_value = ob.value;
}

function isNull(obj)
{
	if(obj == "" || obj == undefined || obj == null)
		return true;
	return false;
}

/**
 * 手机号验证
 * @param mobile
 * @returns
 */
function isMobile(mobile)
{
	if(null==mobile|| undefined==mobile || "" == mobile || !(/^1[34578]\d{9}$/.test(mobile)))
		return false;
	return true;
}

/**
 * 特殊字符
 * @param str
 * @returns
 */
function isLegal(str){
	if(str >= '0' && str <= '9')return true;
	if(str >= 'a' && str <= 'z')return true;
	if(str >= 'A' && str <= 'Z')return true;
	var regEx=new RegExp("[-()#, ，（）]");
	if (regEx.test(str))return true;
	var reg = /^[\u4e00-\u9fa5]+$/i;
	if (reg.test(str))return true;
	return false;
}
/**
 * 反向验证特殊字符
 * @param str1
 * @returns
 */
function isAllLegal(str1){
	if(str1=="" || str1==undefined)return false;
	for (i=0; i<str1.length; i++) {
		if (!isLegal(str1.charAt(i))){
			return false;
		}
	}
	return true;
}
//------------------------mdj end------------------------

