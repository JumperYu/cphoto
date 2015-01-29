<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>ext-5-index</title>
<!-- 只需要其中一个样式 -->
<link href="${ctx}/static/ext-5/ext-theme-crisp-all.css" rel="stylesheet"
	type="text/css" />
<!-- 只需要其中一个JS-->
<script src="${ctx}/static/ext-5/ext-all.js" type="text/javascript"></script>
<!-- 这里编写自己的脚本 -->
<script type="text/javascript">
Ext.require([
             'Ext.grid.*',
             'Ext.data.*',
             'Ext.util.*',
             'Ext.state.*'
         ]);

         Ext.onReady(function() {
             Ext.QuickTips.init();

             var myData;

             Ext.Ajax.request({
                 url: 'http://localhost:8080/cphoto/v2/friends',//动态页地址
                 params: {userid:237},//键值对形式
                 //params:{data:Ext.encode(data)},//原始JSON结构形式
                 success: function(response){
                     var text = response.responseText;
                     console.log(text);
                     myData = eval(text); //解决不转换对象的问题
                     // process server response here
                     // create the data store
		             var store = Ext.create('Ext.data.ArrayStore', {
		                 fields: [
		                    {name: 'company'},
		                    {name: 'price'},
		                    {name: 'change'},
		                    {name: 'pctChange'},
		                    {name: 'lastChange'}
		                 ],
		                 data: myData
		             });
                     // create the Grid
                     var grid = Ext.create('Ext.grid.Panel', {
                         store: store,
                         columnLines: true,
                         columns: [{
                             xtype: 'rownumberer'
                         }, {
                             text     : 'Company<br>Name', // Two line header! Test header height synchronization!
                             locked   : true,
                             flex     : 1,
                             sortable : false,
                             dataIndex: 'company'
                         },{
                             text     : 'Price',
                             lockable: false,
                             width    : 125,
                             sortable : true,
                             dataIndex: 'price'
                         },{
                             text     : 'Change',
                             width    : 125,
                             sortable : true,
                             dataIndex: 'change'
                         },{
                             text     : '% Change',
                             width    : 125,
                             sortable : true,
                             dataIndex: 'pctChange'
                         },{
                             text     : 'Last Updated',
                             width    : 135,
                             sortable : true,
                             dataIndex: 'lastChange'
                         }],
                         height: 350,
                         width: 600,
                         title: 'Twinned Grid',
                         renderTo: 'grid-example',
                         viewConfig: {
                             stripeRows: true
                         },
                         lockedGridConfig: {
                             width: 230
                         },
                         lockedViewConfig: {
                             scroll: 'horizontal'
                         }
                     });
                 }
             });

             //--> 解决跨域数据源问题
         /*    $.post('http://localhost:8080/cphoto//v2/friends', {}, function(data){
                 console.log(data);
             },'jsonp');*/

             /**
              * Custom function used for column renderer
              * @param {Object} val
              */
             function change(val) {
                 if (val > 0) {
                     return '<span style="color:green;">' + val + '</span>';
                 } else if (val < 0) {
                     return '<span style="color:red;">' + val + '</span>';
                 }
                 return val;
             }

             /**
              * Custom function used for column renderer
              * @param {Object} val
              */
             function pctChange(val) {
                 if (val > 0) {
                     return '<span style="color:green;">' + val + '%</span>';
                 } else if (val < 0) {
                     return '<span style="color:red;">' + val + '%</span>';
                 }
                 return val;
             }
         });
</script>
</head>
<body>
  <div id="grid-example"></div>
</body>
</html>