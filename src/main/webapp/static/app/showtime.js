/**
 * 前端计算显示服务器返回创建时间距离目前的间隔
 * 
 * 分别对应
 * 1分钟内  刚刚
 * 1小时内  多少分钟前
 * 1周内	  多少天钱
 * 1个月内  多少周前
 * 1年内   多少月前
 * 等等
 * 
 * @date 2015-01-31 @author zengxm
 * 
 */
var persecondsMin = 60, 
persecondsHour = persecondsMin * 60, 
persecondsday = persecondsHour * 24,
persecondsWeek = persecondsday * 7, 
persecondsMonth = persecondsday * 31, 
persecondsYear = persecondsday * 365;

/**/
function showtime(timestamp) {
	var now = new Date();
	var _date = new Date(parseInt(timestamp) * 1000);
	// 计算间隔（秒）
	var interval = parseInt((now.getTime() - timestamp * 1000) / 1000);
	// 判断间隔
	if (interval <= 0) {
		return "未来";
	} else if (interval <= persecondsMin) {
		return "刚刚";
	} else if (interval <= persecondsHour) {
		return (now.getMinutes() - _date.getMinutes()) + "分钟前";
	} else if (interval <= persecondsday) {
		return (now.getHours() - _date.getHours()) + "小时前";
	} else if (interval <= persecondsWeek) {
		return "N天前";
	}
}