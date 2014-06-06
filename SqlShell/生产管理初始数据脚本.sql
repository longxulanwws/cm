INSERT INTO cm_dict_def (ID, CODE, NAME, TYPE, VALUE, STATUS, DESCRIPTION)
VALUES ('0059', 'pro_type', '生产类型', '1', NULL, '1', '');

INSERT INTO cm_dict_def (ID, CODE, NAME, TYPE, VALUE, STATUS, DESCRIPTION)
VALUES ('0060', 'task_type', '计划任务类型', '1', NULL, '1', '');

INSERT INTO cm_dict_def (ID, CODE, NAME, TYPE, VALUE, STATUS, DESCRIPTION)
VALUES ('0061', 'gongyi_luxian', '工艺路线项目', '1', NULL, '1', '');

INSERT INTO cm_dict_def (ID, CODE, NAME, TYPE, VALUE, STATUS, DESCRIPTION)
VALUES ('0064', 'product.task_type', '任务项类型', '1', NULL, '1', '');

INSERT INTO cm_dict_def (ID, CODE, NAME, TYPE, VALUE, STATUS, DESCRIPTION)
VALUES ('0070', 'production.state', '任务执行情况', '1', NULL, '1', '');

INSERT INTO cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)
VALUES ('000224', '0', '0059', '0', '销售生产', '0', 0, NULL, 1, '1', '1', '');

INSERT INTO cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)
VALUES ('000225', '0', '0060', '0', '设计任务', '0', 0, NULL, 1, '1', '1', '');

INSERT INTO cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)
VALUES ('000226', '1', '0060', '0', '采购任务', '1', 1, NULL, 1, '1', '1', '');

INSERT INTO cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)
VALUES ('000227', '2', '0060', '0', '生产任务', '2', 2, NULL, 1, '1', '1', '');

INSERT INTO cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)
VALUES ('000257', '3', '0060', '0', '发货任务', '3', 3, NULL, 1, '1', '1', '');

INSERT  INTO ruitest.cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)VALUES ('000228', 'r001', '0061', '0', '外协', 'r001', 1, 'null', 1, '1', '1', '');
insert  INTO ruitest.cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)VALUES ('000229', 'r002', '0061', '0', '备料', 'r002', 2, 'null', 1, '1', '1', '');
insert  INTO ruitest.cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)VALUES ('000230', 'r003', '0061', '0', '激光', 'r003', 3, 'null', 1, '1', '1', '');
insert  INTO ruitest.cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)VALUES ('000231', 'r004', '0061', '0', '数冲', 'r004', 4, 'null', 1, '1', '1', '');
insert  INTO ruitest.cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)VALUES ('000232', 'r005', '0061', '0', '折弯', 'r005', 5, 'null', 1, '1', '1', '');
insert  INTO ruitest.cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)VALUES ('000233', 'r006', '0061', '0', '压铆', 'r006', 6, 'null', 1, '1', '1', '');
insert  INTO ruitest.cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)VALUES ('000234', 'r007', '0061', '0', '压焊', 'r007', 7, 'null', 1, '1', '1', '');
insert  INTO ruitest.cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)VALUES ('000235', 'r008', '0061', '0', '切割', 'r008', 8, 'null', 1, '1', '1', '');
insert  INTO ruitest.cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)VALUES ('000236', 'r009', '0061', '0', '打孔', 'r009', 9, 'null', 1, '1', '1', '');
insert  INTO ruitest.cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)VALUES ('000238', 'r010', '0061', '0', '攻丝', 'r010', 10, 'null', 1, '1', '1', '');
insert  INTO ruitest.cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)VALUES ('000239', 'r011', '0061', '0', '焊接', 'r011', 11, 'null', 1, '1', '1', '');
insert  INTO ruitest.cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)VALUES ('000241', 'r012', '0061', '0', '打磨', 'r012', 12, 'null', 1, '1', '1', '');
insert  INTO ruitest.cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)VALUES ('000242', 'r013', '0061', '0', '拉丝', 'r013', 13, 'null', 1, '1', '1', '');
insert  INTO ruitest.cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)VALUES ('000243', 'r014', '0061', '0', '丝印', 'r014', 14, 'null', 1, '1', '1', '');
insert  INTO ruitest.cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)VALUES ('000244', 'r015', '0061', '0', '喷涂', 'r015', 15, 'null', 1, '1', '1', '');
insert  INTO ruitest.cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)VALUES ('000245', 'r016', '0061', '0', '组装', 'r016', 16, 'null', 1, '1', '1', '');



INSERT INTO cm_menu VALUES ('0068', '0350', '库存盘点', '0300', 5, '0350', '', '', '1', 1, '1', '1', '');
INSERT INTO cm_menu VALUES ('0070', '0205', '合同信息管理', '0200', 5, '0205', '', '', '2', 2, '1', '1', '');
INSERT INTO cm_menu VALUES ('0073', '0206', '产品工时定额管理', '0200', 6, '0206', '', '产品信息维护', '6', 6, '1', '1', '');
INSERT INTO cm_menu VALUES ('0075', '0680', '订单计划', '0800', 1, '0680', '', '', '1', 1, '1', '1', '');
INSERT INTO cm_menu VALUES ('0077', '0670', '生产任务单', '0800', 3, '0670', '', '', '1', 1, '1', '1', '');
INSERT INTO cm_menu VALUES ('0079', '0681', '订单计划跟踪', '0800', 2, '0681', '', '1', '1', 1, '1', '1', '');
INSERT INTO cm_menu VALUES ('0081', '0671', '生产任务日跟踪', '0800', 4, '0671', '', '1', '1', 1, '1', '1', '');
INSERT INTO cm_menu VALUES ('0082', '0800', '计划管理', '0000', 8, '0800', '0', '1', '1', 1, '1', '1', '1');


INSERT INTO cm_resource VALUES ('0205', '合同信息管理', './sysmgt/Compact.html', '1', '1', '');
INSERT INTO cm_resource VALUES ('0206', '产品信息维护', './production/search_task.html', '1', '1', '');
INSERT INTO cm_resource VALUES ('0350', '库存盘点', './stock/stock_taking.html', '0', '1', '');
INSERT INTO cm_resource VALUES ('0670', '生产任务单', './production/search_production_task.html', '0', '1', '');
INSERT INTO cm_resource VALUES ('0671', '生产任务日跟踪', './production/search_production_task.html%3Fm%3Dmng', '', '1', '');
INSERT INTO cm_resource VALUES ('0680', '订单计划', './production/search_production_plan.html', '0', '1', '');
INSERT INTO cm_resource VALUES ('0681', '订单计划跟踪', './production/search_production_plan.html%3Fm%3Dmng', '0', '1', '');
INSERT INTO cm_resource VALUES ('0800', '计划管理', ' ', '0', '1', '');

