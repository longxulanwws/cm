/**
 * 产品中的工艺路线列数组
 * @param 
 * @returns  columns[]
 */
function getProductRoutingColumns(){
	var columns=[
                 {display:'外协',name:'waixie',width:60,align:'right',render:function(item){return item.beiliao&&item.waixie!='null'&&item.waixie > 0.00001 ? parseFloat(item.beiliao).toFixed(2) : '';}},
                 {display:'备料',name:'beiliao',width:60,align:'right',render:function(item){return item.beiliao&&item.beiliao!='null'&&item.beiliao > 0.00001 ? parseFloat(item.beiliao).toFixed(2) : '';}},
                 {display:'激光',name:'jiguang',width:60,align:'right',render:function(item){return item.jiguang&&item.jiguang!='null'&&item.jiguang > 0.00001 ? parseFloat(item.jiguang).toFixed(2) : '';}},
                 {display:'数冲',name:'shuchong',width:60,align:'right',render:function(item){return item.shuchong&&item.shuchong!='null'&&item.shuchong > 0.00001 ? parseFloat(item.shuchong).toFixed(2) : '';}},
                 {display:'折弯',name:'zhewan',width:60,align:'right',render:function(item){return item.zhewan&&item.zhewan!='null'&&item.zhewan > 0.00001  ? parseFloat(item.zhewan).toFixed(2) : '';}},
                 {display:'压铆',name:'yamao',width:60,align:'right',render:function(item){return item.yamao&&item.yamao!='null'&&item.yamao > 0.00001 ? parseFloat(item.yamao).toFixed(2) : '';}},
                 {display:'压焊',name:'yahan',width:60,align:'right',render:function(item){return item.yahan&&item.yahan!='null'&&item.yahan > 0.00001 ? parseFloat(item.yahan).toFixed(2) : '';}},
                 {display:'切割',name:'qiege',width:60,align:'right',render:function(item){return item.qiege&&item.qiege!='null'&&item.qiege > 0.00001 ? parseFloat(item.qiege).toFixed(2) : '';}},
                 {display:'打孔',name:'dakong',width:60,align:'right',render:function(item){return item.dakong&&item.dakong!='null'&&item.dakong > 0.00001 ? parseFloat(item.dakong).toFixed(2) : '';}},
                 {display:'攻丝',name:'gongsi',width:60,align:'right',render:function(item){return item.gongsi&&item.gongsi!='null'&&item.gongsi > 0.00001 ? parseFloat(item.gongsi).toFixed(2) : '';}},
                 {display:'焊接',name:'hanjie',width:60,align:'right',render:function(item){return item.hanjie&&item.hanjie!='null'&&item.hanjie > 0.00001 ? parseFloat(item.hanjie).toFixed(2) : '';}},
                 {display:'打磨',name:'damo',width:60,align:'right',render:function(item){return item.damo&&item.damo!='null'&&item.damo > 0.00001 ? parseFloat(item.damo).toFixed(2) : '';}},
                 {display:'拉丝',name:'lasi',width:60,align:'right',render:function(item){return item.lasi&&item.lasi!='null'&&item.lasi > 0.00001 ? parseFloat(item.lasi).toFixed(2) : '';}},
                 {display:'丝印',name:'siyin',width:60,align:'right',render:function(item){return item.siyin&&item.siyin!='null'&&item.siyin > 0.00001 ? parseFloat(item.siyin).toFixed(2) : '';}},
                 {display:'喷涂',name:'peitu',width:60,align:'right',render:function(item){return item.peitu&&item.peitu!='null'&&item.peitu > 0.00001 ? parseFloat(item.peitu).toFixed(2) : '';}},
                 {display:'组装',name:'zuzhuang',width:60,align:'right',render:function(item){return item.zuzhuang&&item.zuzhuang!='null'&&item.zuzhuang > 0.00001 ? parseFloat(item.zuzhuang).toFixed(2) : '';}}
                 
	             ]; 
	return columns;
}

/**
 * 产品中的工艺路线工时汇总表插入数据
 * @param  product_id 产品ID
 * @returns
 */
function insertSumTaskHours(product_id){
	var param = {product_id:product_id};
	var result,data;
	var count = 0;
	var selectValue = 0;
    data = JSON.stringify(reqObj('q','production.select.mrp_routing_hours.pro_query',JSON.stringify(param)));
    ajaxSubmit("/cm/rbac/cm.do?m=c", data,function(data) {
    	selectValue = data['data'].Rows[0].count_num;
    }, null, false);
    if(selectValue > 0){
    	data = JSON.stringify(reqObj('d','production.delete.mrp_routing_hours.pro_delete',JSON.stringify(param)));
    	ajaxSubmit("/cm/rbac/cm.do?m=c", data,null, null, false);
    }
    data = JSON.stringify(reqObj('a','production.add.mrp_routing_hours.task_add',JSON.stringify(param)));
    ajaxSubmit("/cm/rbac/cm.do?m=c", data,function(data) {
    	result = data['status'];
    }, null, false);
    if(result > 0){
    	while(1){
    		data = JSON.stringify(reqObj('q','production.select.mrp_routing_hours.check',JSON.stringify(param)));
        	ajaxSubmit("/cm/rbac/cm.do?m=c", data, function(data) {
        		count = data['data'].Rows[0].count_num;
        	}, null, false);
        	if(count > 0){
         		data = JSON.stringify(reqObj('a','production.add.mrp_routing_hours.product_add',JSON.stringify(param)));
            	ajaxSubmit("/cm/rbac/cm.do?m=c", data, function(data) {
            		return data['status'];
            	}, null, false);
        	}else{
        		break;
        	}
    	}
    }
}
