module.exports = {
	regFenToYuan: regFenToYuan,
	timeConversion:timeConversion
}
//分转化为元 - 正则解决精度
function regFenToYuan(fen) {
	var num = fen;
	num = fen * 0.01;
	num += '';
	var reg = num.indexOf('.') > -1 ? /(\d{1,3})(?=(?:\d{3})+\.)/g : /(\d{1,3})(?=(?:\d{3})+$)/g;
	num = num.replace(reg, '$1');
	num = toDecimal2(num)
	return num
};
//时间转换
function timeConversion(finishTime){
 let nowTime = new Date().getTime();
 let timeDiff = finishTime-nowTime;
 let minute = 0;
 let hours = 0;
 let Zhours = 0;
 if(timeDiff >= 0){
  hours = timeDiff/(1000 * 60 *60);
  Zhours = Math.floor(hours);
  minute = Math.floor((hours - Zhours) * 60);
 }
 return {minute:minute, hours:Zhours}
}

//强制保留2位小数，如：2，会在2后面补上00.即2.00
function toDecimal2(x) {
	var f = parseFloat(x);
	if (isNaN(f)) {
		return false;
	}
	var f = Math.round(x * 100) / 100;
	var s = f.toString();
	var rs = s.indexOf('.');
	if (rs < 0) {
		rs = s.length;
		s += '.';
	}
	while (s.length <= rs + 2) {
		s += '0';
	}
	return s;
}
