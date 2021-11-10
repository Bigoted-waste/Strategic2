/**
 * @author  郑树聪 zhengsc1993@gmail.com
 * @date 2015.3.19
 * /
 
 /**
 * 封装的弹窗函数：先在弹窗代码之前添加一个遮罩层，然后弹出窗口
 * @param  element 弹窗的DOM对象
 */
function popWin(element) {
	//遮罩层
	element.before("<div id=\"zhezhao\"></div>");
	//获取窗口的高度 
	var windowHeight;
	//获取窗口的宽度 
	var windowWidth;
	//获取弹窗的宽度 
	var popWidth;
	//获取弹窗高度 
	var popHeight;
	windowHeight = $(window).height();
	windowWidth = $(window).width();
	popHeight = element.height();
	popWidth = element.width();
	//计算弹出窗口的左上角Y的偏移量 
	var popY = (windowHeight - popHeight) / 2;
	var popX = (windowWidth - popWidth) / 2;
	//设定窗口的位置 
	element.css("top", popY).css("left", popX).show();
}

/**
 * 定义关闭弹窗方法
 * @param element 需要关闭的弹窗的DOM对象
 */
function closeWin(element){
	element.hide();
	element.siblings('#zhezhao').hide();
}