/**
 * 产品中的工艺路线列数组
 * @param 
 * @returns  columns[]
 */
function getProductRoutingColumns(){
	var columns=[
                 {display:'外协',name:'waixie',width:60,align:'right',render:function(item){return item.beiliao&&item.beiliao!='null'? parseFloat(item.beiliao).toFixed(2) : '';}},
                 {display:'备料',name:'beiliao',width:60,align:'right',render:function(item){return item.beiliao&&item.beiliao!='null'? parseFloat(item.beiliao).toFixed(2) : '';}},
                 {display:'激光',name:'jiguang',width:60,align:'right',render:function(item){return item.jiguang&&item.jiguang!='null'? parseFloat(item.jiguang).toFixed(2) : '';}},
                 {display:'数冲',name:'shuchong',width:60,align:'right',render:function(item){return item.shuchong&&item.shuchong!='null'? parseFloat(item.shuchong).toFixed(2) : '';}},
                 {display:'折弯',name:'zhewan',width:60,align:'right',render:function(item){return item.zhewan&&item.zhewan!='null' ? parseFloat(item.zhewan).toFixed(2) : '';}},
                 {display:'压铆',name:'yamao',width:60,align:'right',render:function(item){return item.yamao&&item.yamao!='null'? parseFloat(item.yamao).toFixed(2) : '';}},
                 {display:'压焊',name:'yahan',width:60,align:'right',render:function(item){return item.yahan&&item.yahan!='null' ? parseFloat(item.yahan).toFixed(2) : '';}},
                 {display:'切割',name:'qiege',width:60,align:'right',render:function(item){return item.qiege&&item.qiege!='null' ? parseFloat(item.qiege).toFixed(2) : '';}},
                 {display:'打孔',name:'dakong',width:60,align:'right',render:function(item){return item.dakong&&item.dakong!='null' ? parseFloat(item.dakong).toFixed(2) : '';}},
                 {display:'攻丝',name:'gongsi',width:60,align:'right',render:function(item){return item.gongsi&&item.gongsi!='null' ? parseFloat(item.gongsi).toFixed(2) : '';}},
                 {display:'焊接',name:'hanjie',width:60,align:'right',render:function(item){return item.hanjie&&item.hanjie!='null' ? parseFloat(item.hanjie).toFixed(2) : '';}},
                 {display:'打磨',name:'damo',width:60,align:'right',render:function(item){return item.damo&&item.damo!='null' ? parseFloat(item.damo).toFixed(2) : '';}},
                 {display:'拉丝',name:'lasi',width:60,align:'right',render:function(item){return item.lasi&&item.lasi!='null' ? parseFloat(item.lasi).toFixed(2) : '';}},
                 {display:'丝印',name:'siyin',width:60,align:'right',render:function(item){return item.siyin&&item.siyin!='null' ? parseFloat(item.siyin).toFixed(2) : '';}},
                 {display:'喷涂',name:'peitu',width:60,align:'right',render:function(item){return item.peitu&&item.peitu!='null' ? parseFloat(item.peitu).toFixed(2) : '';}},
                 {display:'组装',name:'zuzhuang',width:60,align:'right',render:function(item){return item.zuzhuang&&item.zuzhuang!='null' ? parseFloat(item.zuzhuang).toFixed(2) : '';}}
                 
	             ]; 
	return columns;
}
