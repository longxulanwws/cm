INSERT INTO cm_dict_def (ID, CODE, NAME, TYPE, VALUE, STATUS, DESCRIPTION)
VALUES ('0059', 'pro_type', '��������', '1', NULL, '1', '');

INSERT INTO cm_dict_def (ID, CODE, NAME, TYPE, VALUE, STATUS, DESCRIPTION)
VALUES ('0060', 'task_type', '�ƻ���������', '1', NULL, '1', '');

INSERT INTO cm_dict_def (ID, CODE, NAME, TYPE, VALUE, STATUS, DESCRIPTION)
VALUES ('0061', 'gongyi_luxian', '����·����Ŀ', '1', NULL, '1', '');

INSERT INTO cm_dict_def (ID, CODE, NAME, TYPE, VALUE, STATUS, DESCRIPTION)
VALUES ('0064', 'product.task_type', '����������', '1', NULL, '1', '');

INSERT INTO cm_dict_def (ID, CODE, NAME, TYPE, VALUE, STATUS, DESCRIPTION)
VALUES ('0070', 'production.state', '����ִ�����', '1', NULL, '1', '');

INSERT INTO cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)
VALUES ('000224', '0', '0059', '0', '��������', '0', 0, NULL, 1, '1', '1', '');

INSERT INTO cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)
VALUES ('000225', '0', '0060', '0', '�������', '0', 0, NULL, 1, '1', '1', '');

INSERT INTO cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)
VALUES ('000226', '1', '0060', '0', '�ɹ�����', '1', 1, NULL, 1, '1', '1', '');

INSERT INTO cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)
VALUES ('000227', '2', '0060', '0', '��������', '2', 2, NULL, 1, '1', '1', '');

INSERT INTO cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)
VALUES ('000257', '3', '0060', '0', '��������', '3', 3, NULL, 1, '1', '1', '');

INSERT  INTO ruitest.cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)VALUES ('000228', 'r001', '0061', '0', '��Э', 'r001', 1, 'null', 1, '1', '1', '');
insert  INTO ruitest.cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)VALUES ('000229', 'r002', '0061', '0', '����', 'r002', 2, 'null', 1, '1', '1', '');
insert  INTO ruitest.cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)VALUES ('000230', 'r003', '0061', '0', '����', 'r003', 3, 'null', 1, '1', '1', '');
insert  INTO ruitest.cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)VALUES ('000231', 'r004', '0061', '0', '����', 'r004', 4, 'null', 1, '1', '1', '');
insert  INTO ruitest.cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)VALUES ('000232', 'r005', '0061', '0', '����', 'r005', 5, 'null', 1, '1', '1', '');
insert  INTO ruitest.cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)VALUES ('000233', 'r006', '0061', '0', 'ѹí', 'r006', 6, 'null', 1, '1', '1', '');
insert  INTO ruitest.cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)VALUES ('000234', 'r007', '0061', '0', 'ѹ��', 'r007', 7, 'null', 1, '1', '1', '');
insert  INTO ruitest.cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)VALUES ('000235', 'r008', '0061', '0', '�и�', 'r008', 8, 'null', 1, '1', '1', '');
insert  INTO ruitest.cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)VALUES ('000236', 'r009', '0061', '0', '���', 'r009', 9, 'null', 1, '1', '1', '');
insert  INTO ruitest.cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)VALUES ('000238', 'r010', '0061', '0', '��˿', 'r010', 10, 'null', 1, '1', '1', '');
insert  INTO ruitest.cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)VALUES ('000239', 'r011', '0061', '0', '����', 'r011', 11, 'null', 1, '1', '1', '');
insert  INTO ruitest.cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)VALUES ('000241', 'r012', '0061', '0', '��ĥ', 'r012', 12, 'null', 1, '1', '1', '');
insert  INTO ruitest.cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)VALUES ('000242', 'r013', '0061', '0', '��˿', 'r013', 13, 'null', 1, '1', '1', '');
insert  INTO ruitest.cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)VALUES ('000243', 'r014', '0061', '0', '˿ӡ', 'r014', 14, 'null', 1, '1', '1', '');
insert  INTO ruitest.cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)VALUES ('000244', 'r015', '0061', '0', '��Ϳ', 'r015', 15, 'null', 1, '1', '1', '');
insert  INTO ruitest.cm_dict_item (ID, CODE, DICT_DEF_ID, HIGHER_ID, NAME, VALUE, SEQ, Path, Layer, Detail, STATUS, DESCRIPTION)VALUES ('000245', 'r016', '0061', '0', '��װ', 'r016', 16, 'null', 1, '1', '1', '');



INSERT INTO cm_menu VALUES ('0068', '0350', '����̵�', '0300', 5, '0350', '', '', '1', 1, '1', '1', '');
INSERT INTO cm_menu VALUES ('0070', '0205', '��ͬ��Ϣ����', '0200', 5, '0205', '', '', '2', 2, '1', '1', '');
INSERT INTO cm_menu VALUES ('0073', '0206', '��Ʒ��ʱ�������', '0200', 6, '0206', '', '��Ʒ��Ϣά��', '6', 6, '1', '1', '');
INSERT INTO cm_menu VALUES ('0075', '0680', '�����ƻ�', '0800', 1, '0680', '', '', '1', 1, '1', '1', '');
INSERT INTO cm_menu VALUES ('0077', '0670', '��������', '0800', 3, '0670', '', '', '1', 1, '1', '1', '');
INSERT INTO cm_menu VALUES ('0079', '0681', '�����ƻ�����', '0800', 2, '0681', '', '1', '1', 1, '1', '1', '');
INSERT INTO cm_menu VALUES ('0081', '0671', '���������ո���', '0800', 4, '0671', '', '1', '1', 1, '1', '1', '');
INSERT INTO cm_menu VALUES ('0082', '0800', '�ƻ�����', '0000', 8, '0800', '0', '1', '1', 1, '1', '1', '1');


INSERT INTO cm_resource VALUES ('0205', '��ͬ��Ϣ����', './sysmgt/Compact.html', '1', '1', '');
INSERT INTO cm_resource VALUES ('0206', '��Ʒ��Ϣά��', './production/search_task.html', '1', '1', '');
INSERT INTO cm_resource VALUES ('0350', '����̵�', './stock/stock_taking.html', '0', '1', '');
INSERT INTO cm_resource VALUES ('0670', '��������', './production/search_production_task.html', '0', '1', '');
INSERT INTO cm_resource VALUES ('0671', '���������ո���', './production/search_production_task.html%3Fm%3Dmng', '', '1', '');
INSERT INTO cm_resource VALUES ('0680', '�����ƻ�', './production/search_production_plan.html', '0', '1', '');
INSERT INTO cm_resource VALUES ('0681', '�����ƻ�����', './production/search_production_plan.html%3Fm%3Dmng', '0', '1', '');
INSERT INTO cm_resource VALUES ('0800', '�ƻ�����', ' ', '0', '1', '');

