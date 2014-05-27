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

INSERT INTO cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)
VALUES ('000228', '001', '0061', '0', '外协', '001', 1, 'null', 1, '1', '1', '');

INSERT INTO cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)
VALUES ('000229', '002', '0061', '0', '备料', '002', 2, 'null', 1, '1', '1', '');

INSERT INTO cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)
VALUES ('000230', '003', '0061', '0', '激光', '003', 3, 'null', 1, '1', '1', '');

INSERT INTO cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)
VALUES ('000231', '004', '0061', '0', '数冲', '004', 4, 'null', 1, '1', '1', '');

INSERT INTO cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)
VALUES ('000232', '005', '0061', '0', '折弯', '005', 5, 'null', 1, '1', '1', '');

INSERT INTO cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)
VALUES ('000233', '006', '0061', '0', '压铆', '006', 6, 'null', 1, '1', '1', '');

INSERT INTO cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)
VALUES ('000234', '007', '0061', '0', '压焊', '007', 7, 'null', 1, '1', '1', '');

INSERT INTO cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)
VALUES ('000235', '008', '0061', '0', '切割', '008', 8, 'null', 1, '1', '1', '');

INSERT INTO cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)
VALUES ('000236', '009', '0061', '0', '打孔', '009', 9, 'null', 1, '1', '1', '');

INSERT INTO cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)
VALUES ('000238', '010', '0061', '0', '攻丝', '010', 10, 'null', 1, '1', '1', '');

INSERT INTO cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)
VALUES ('000239', '011', '0061', '0', '焊接', '011', 11, 'null', 1, '1', '1', '');

INSERT INTO cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)
VALUES ('000241', '012', '0061', '0', '打磨', '012', 12, 'null', 1, '1', '1', '');

INSERT INTO cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)
VALUES ('000242', '013', '0061', '0', '拉丝', '013', 13, 'null', 1, '1', '1', '');

INSERT INTO cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)
VALUES ('000243', '014', '0061', '0', '丝印', '014', 14, 'null', 1, '1', '1', '');

INSERT INTO cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)
VALUES ('000244', '015', '0061', '0', '喷涂', '015', 15, 'null', 1, '1', '1', '');

INSERT INTO cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)
VALUES ('000245', '016', '0061', '0', '组装', '016', 16, 'null', 1, '1', '1', '');

INSERT INTO cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)
VALUES ('000249', '1', '0064', '0', '产品', '1', 1, NULL, 1, '1', '1', '');

INSERT INTO cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)
VALUES ('000252', '2', '0064', '0', '套件/虚项', '2', 2, NULL, 1, '1', '1', '');

INSERT INTO cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)
VALUES ('000253', '3', '0064', '0', '工艺路线', '3', 3, NULL, 1, '1', '1', '');

INSERT INTO cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)
VALUES ('000254', '1', '0070', '0', '正常', '1', 1, NULL, 1, '1', '1', '');

INSERT INTO cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)
VALUES ('000255', '2', '0070', '0', '延迟', '2', 2, NULL, 1, '1', '1', '');

INSERT INTO cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)
VALUES ('000256', '3', '0070', '0', '提前', '3', 3, NULL, 1, '1', '1', '');

