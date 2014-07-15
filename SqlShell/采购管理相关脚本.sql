INSERT INTO cm_menu (ID, CODE, NAME, HIGHER_ID, SEQ, RESOURCE_ID, ICON_URI, HINT, Path, Layer, Detail, STATUS, DESCRIPTIOIN)
VALUES ('0086', '0900', '工作流', '0000', 9, '0900', '', '1', '1', 1, '1', '1', '');

INSERT INTO cm_menu (ID, CODE, NAME, HIGHER_ID, SEQ, RESOURCE_ID, ICON_URI, HINT, Path, Layer, Detail, STATUS, DESCRIPTIOIN)
VALUES ('0087', '0901', '我的申请', '0900', 1, '0901', '', '1', '1', 1, '1', '1', '');

INSERT INTO cm_menu (ID, CODE, NAME, HIGHER_ID, SEQ, RESOURCE_ID, ICON_URI, HINT, Path, Layer, Detail, STATUS, DESCRIPTIOIN)
VALUES ('0089', '0902', '代办任务', '0900', 2, '0902', '', '1', '1', 1, '1', '1', '');

INSERT INTO cm_menu (ID, CODE, NAME, HIGHER_ID, SEQ, RESOURCE_ID, ICON_URI, HINT, Path, Layer, Detail, STATUS, DESCRIPTIOIN)
VALUES ('0090', '0903', '已办任务', '0900', 3, '0903', '', '1', '1', 1, '1', '1', '');


INSERT INTO cm_resource (ID, NAME, URI, TYPE, STATUS, DESCRIPTION)
VALUES ('0900', '工作流', '', '0', '1', '');

INSERT INTO cm_resource (ID, NAME, URI, TYPE, STATUS, DESCRIPTION)
VALUES ('0901', '我的申请', './snaker/MyApplay.html', '1', '1', '');

INSERT INTO cm_resource (ID, NAME, URI, TYPE, STATUS, DESCRIPTION)
VALUES ('0902', '待办任务', './snaker/MyGtasks.html', '0', '1', '');

INSERT INTO cm_resource (ID, NAME, URI, TYPE, STATUS, DESCRIPTION)
VALUES ('0903', '已办任务', './snaker/MyHtasks.html', '0', '1', '');
