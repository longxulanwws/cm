/*
 * �����ƻ�����
 */
drop table if exists mrp_plan_task;

/*==============================================================*/
/* Table: mrp_plan_task    �����ƻ�����                                              */
/*==============================================================*/
create table mrp_plan_task
(
   task_plan_id         varchar(36) not null,
   Company_ID           varchar(36),
   Dept_ID              varchar(36),
   BusiOrg_ID           varchar(36),
   pdc_plan_code        varchar(36),
   plan_type            varchar(2),
   exec_dept            varchar(36),
   plan_dateStart       varchar(20),
   plan_dateEnd         varchar(20),
   plan_cycle           varchar(20),
   date_start           varchar(20),
   date_end             varchar(20),
   cycle                varchar(20),
   plan_trackState      varchar(2),
   plan_note            varchar(512),
   State                varchar(4) comment '00 δ���
            10 ���',
   Flow_State           varchar(2),
   CreateUser           varchar(20),
   CreateDate           varchar(20),
   WriteDate            varchar(20),
   WriteUser            varchar(20)
);

alter table mrp_plan_task comment '�����ƻ�����';
/*
 * ���������ƻ�
 */
drop table if exists mrp_production_plan;

/*==============================================================*/
/* Table: mrp_production_plan     ���������ƻ�                                  */
/*==============================================================*/
create table mrp_production_plan
(
   pdc_plan_id          varchar(36),
   Company_ID           varchar(36) not null,
   Dept_ID              varchar(36) not null,
   BusiOrg_ID           varchar(36) not null,
   pdc_plan_code        varchar(36),
   pdc_plan_Name        varchar(60),
   pdc_plan_type        varchar(2) comment '0 �ջ�
            1 ����',
   pdc_plan_origin_id   varchar(36),
   Sale_ID              varchar(36),
   Project_ID           varchar(36),
   Partner_ID           varchar(36),
   Product_ID           varchar(36),
   Product_Qty          decimal(28,6),
   Product_Uom          varchar(36),
   Bom_ID               varchar(36),
   Bom_Code             varchar(36),
   draw_id              varchar(36),
   draw_code            varchar(36),
   date_start           varchar(20),
   date_planned         varchar(20),
   date_finished        varchar(20),
   cycle_total          varchar(20),
   cycle_uom            varchar(20),
   user_id              varchar(20),
   State                varchar(4) comment '00 �½�
            01 ���ʼ�
            02 �����
            03 ������',
   Flow_State           varchar(2),
   Note                 varchar(512),
   CreateUser           varchar(20),
   CreateDate           varchar(20),
   WriteDate            varchar(20) comment '���ϵĴ�������',
   WriteUser            varchar(20) comment '�������ϵ���Ա����'
);

alter table mrp_production_plan comment '���������ƻ�';

drop table if exists mrp_production_task;

/*==============================================================*/
/* Table: mrp_production_task    ����������ϸ                                */
/*==============================================================*/
create table mrp_production_task
(
   production_task_id   varchar(36) not null,
   Company_ID           varchar(36) not null,
   Dept_ID              varchar(36) not null,
   BusiOrg_ID           varchar(36) not null,
   production_task_code varchar(36),
   production_task_name varchar(60),
   production_task_type varchar(2) comment '1 �������
            2 �ɹ�����
            3 ��������
            4 ��������
             ',
   production_plan_id   varchar(36),
   production_plan_code varchar(36),
   production_task_Origin_id varchar(36),
   Partner_ID           varchar(36),
   task_cycle           int,
   task_plan_start_date varchar(20),
   task_plan_finish_date varchar(20),
   task_start_date      varchar(20),
   task_finish_date     varchar(20),
   task_track_state     varchar(2),
   task_track_note      varchar(512),
   task_note            varchar(512),
   bom_id               varchar(36),
   bom_code             varchar(36),
   drawing_id           varchar(36),
   drawing_code         varchar(36),
   State                varchar(2),
   Flow_State           varchar(2),
   CreateUser           varchar(20),
   CreateDate           varchar(20),
   WriteDate            varchar(20) comment '���ϵĴ�������',
   WriteUser            varchar(20) comment '�������ϵ���Ա����',
   primary key (production_task_id)
);

alter table mrp_production_task comment '�������������ƻ���������';

/*
 * ���������������������
 */
drop index I_WareHouses_Code on mrp_production_task_rel;

drop table if exists mrp_production_task_rel;

/*==============================================================*/
/* Table: mrp_production_task_rel                               */
/*==============================================================*/
create table mrp_production_task_rel
(
   production_task_rel_id varchar(36) not null,
   production_task_code varchar(36),
   task_routing_id      varchar(36),
   Product_ID           varchar(36),
   Product_name         varchar(60),
   Product_Uom          varchar(36),
   production_product_qty decimal(28,6),
   primary key (production_task_rel_id)
);

/*
 * ����������ٱ�
 */
drop table if exists mrp_production_task_track;

/*==============================================================*/
/* Table: mrp_production_task_track                             */
/*==============================================================*/
create table mrp_production_task_track
(
   production_task_track_id varchar(36) not null,
   production_task_track_date varchar(20),
   production_task_id   varchar(36),
   task_routing_id      varchar(36),
   Product_ID           varchar(36),
   Product_Uom          varchar(36),
   production_product_qty decimal(28,6),
   task_routing_qty     decimal(28,6),
   task_routing_standard_hour decimal(28,6),
   plan_hours           decimal(28,6),
   day_finish_hours     decimal(28,6) not null,
   finish_hours         decimal(28,6) not null,
   finish_percent       decimal(28,6) not null,
   day_real_hours       decimal(28,6) comment '00  �½� 
            10  �ա��������
            ',
   real_hours           decimal(28,6) comment '00  �½� 
            10  �ա��������
            ',
   Note                 varchar(512),
   WriteUser            varchar(20) comment '�������ϵ���Ա����',
   WriteDate            varchar(20) comment '���ϵĴ�������',
   CreateUser           varchar(20),
   CreateDate           varchar(20),
   primary key (production_task_track_id)
);
/*
 * ����������չ�����Ϣ
 */
drop table if exists mrp_task_routing;

/*==============================================================*/
/* Table: mrp_task_routing                                      */
/*==============================================================*/
create table mrp_task_routing
(
   task_routing_id      varchar(36) not null,
   task_routing_code    varchar(36) not null,
   task_routing_name    varchar(60) not null,
   task_routing_type    varchar(36) comment '1����ͨ����
            2���׼�
            3������',
   up_task_routing_code varchar(36),
   Product_ID           varchar(36),
   Product_Name         varchar(60),
   task_routing_qty     decimal(28,6),
   task_routing_standard_hour decimal(28,6),
   task_routing_level   int,
   Note                 varchar(512),
   WriteUser            varchar(20) comment '�������ϵ���Ա����',
   WriteDate            varchar(20) comment '���ϵĴ�������',
   CreateUser           varchar(20),
   CreateDate           varchar(20),
   primary key (task_routing_id)
);

alter table mrp_task_routing comment '���ݲ�Ʒ�������Ҫ��������������չ�����Ϣ��';
/*
 * ��ʱ�������
 */
drop table if exists mrp_routing_hours;

/*==============================================================*/
/* Table: mrp_routing_hours     ��ʱ�������                                */
/*==============================================================*/
create table mrp_routing_hours
(
   task_routing_id      varchar(36) not null,
   task_routing_code    varchar(36) not null,
   task_routing_name    varchar(60) not null,
   task_routing_type    varchar(36) comment '1����ͨ����
            2���׼�
            3������',
   up_task_routing_code varchar(36),
   routing_task_code    varchar(36) not null,
   Product_ID           varchar(36),
   Product_Name         varchar(60),
   task_routing_qty     decimal(28,6),
   primary key (task_routing_id, routing_task_code)
);

alter table mrp_routing_hours comment '���ܲ�Ʒ������������������������';

/*
 * ���� ��Ʒ�����㼶�����Ĺ�ʱ�����������
   ��һ�����view��ʵ�ֽ�������Ʒ�Ĺ�ʱ�����������
   �����༶�����ĸ�������������Ĺ�ʱ�����������
 */

-- DROP VIEW  vw_task_housrs;

CREATE VIEW vw_task_housrs AS
SELECT a. *
	, sum(CASE b.task_routing_code WHEN 'r001' THEN a.task_routing_qty * b.task_routing_standard_hour ELSE '' END) AS waixie
    , sum(CASE b.task_routing_code WHEN 'r002' THEN a.task_routing_qty * b.task_routing_standard_hour ELSE '' END) AS beiliao
    , sum(CASE b.task_routing_code WHEN 'r003' THEN a.task_routing_qty * b.task_routing_standard_hour ELSE '' END) AS jiguang
    , sum(CASE b.task_routing_code WHEN 'r004' THEN a.task_routing_qty * b.task_routing_standard_hour ELSE '' END) AS shuchong
    , sum(CASE b.task_routing_code WHEN 'r005' THEN a.task_routing_qty * b.task_routing_standard_hour ELSE '' END) AS zhewan
    , sum(CASE b.task_routing_code WHEN 'r006' THEN a.task_routing_qty * b.task_routing_standard_hour ELSE '' END) AS yamao
    , sum(CASE b.task_routing_code WHEN 'r007' THEN a.task_routing_qty * b.task_routing_standard_hour ELSE '' END) AS yahan
    , sum(CASE b.task_routing_code WHEN 'r008' THEN a.task_routing_qty * b.task_routing_standard_hour ELSE '' END) AS qiege
    , sum(CASE b.task_routing_code WHEN 'r009' THEN a.task_routing_qty * b.task_routing_standard_hour ELSE '' END) AS dakong
    , sum(CASE b.task_routing_code WHEN 'r010' THEN a.task_routing_qty * b.task_routing_standard_hour ELSE '' END) AS gongsi
    , sum(CASE b.task_routing_code WHEN 'r011' THEN a.task_routing_qty * b.task_routing_standard_hour ELSE '' END) AS hanjie
    , sum(CASE b.task_routing_code WHEN 'r012' THEN a.task_routing_qty * b.task_routing_standard_hour ELSE '' END) AS damo
    , sum(CASE b.task_routing_code WHEN 'r013' THEN a.task_routing_qty * b.task_routing_standard_hour ELSE '' END) AS lasi
    , sum(CASE b.task_routing_code WHEN 'r014' THEN a.task_routing_qty * b.task_routing_standard_hour ELSE '' END) AS siyin
    , sum(CASE b.task_routing_code WHEN 'r015' THEN a.task_routing_qty * b.task_routing_standard_hour ELSE '' END) AS peitu
    , sum(CASE b.task_routing_code WHEN 'r016' THEN a.task_routing_qty * b.task_routing_standard_hour ELSE '' END) AS zuzhuang
FROM mrp_routing_hours AS a
    LEFT JOIN mrp_task_routing AS b ON a.Product_ID = b.Product_ID AND a.routing_task_code = b.up_task_routing_code AND b.task_routing_type = '3'
GROUP BY task_routing_id, task_routing_code, task_routing_name, task_routing_type, up_task_routing_code, routing_task_code, Product_ID, Product_Name, task_routing_qty


/*
   ���� ��Ʒ�����㼶�����Ĺ�ʱ�����������
   ����Ʒ��ʱ����ά����Ϣ���ݹ������ܺ�Ĺ�ʱ��������
   ���ջ���view��ʵ�ֽ�������Ʒ�Ĺ�ʱ�����������   
   �����༶�����ĸ�������������Ĺ�ʱ�����������
   �����༰���������в�Ʒ��Ĺ�ʱ�����������  
*/
-- DROP VIEW IF EXISTS vw_product_task_housrs;

CREATE VIEW vw_product_task_housrs AS
SELECT a.task_routing_id
    , sum(waixie) AS waixie
    , sum(beiliao) AS beiliao
    , sum(jiguang) AS jiguang
    , sum(shuchong) AS shuchong
    , sum(zhewan) AS zhewan
    , sum(yamao) AS yamao
    , sum(yahan) AS yahan
    , sum(qiege) AS qiege
    , sum(dakong) AS dakong
    , sum(gongsi) AS gongsi
    , sum(hanjie) AS hanjie
    , sum(damo) AS damo
    , sum(lasi) AS lasi
    , sum(siyin) AS siyin
    , sum(peitu) AS peitu
    , sum(zuzhuang) AS zuzhuang
FROM mrp_task_routing AS a
    LEFT JOIN vw_task_housrs AS b ON a.task_routing_id = b.task_routing_id
WHERE a.task_routing_type = '2'
GROUP BY a.task_routing_id
UNION
SELECT a.task_routing_id
    , sum(waixie) AS waixie
    , sum(beiliao) AS beiliao
    , sum(jiguang) AS jiguang
    , sum(shuchong) AS shuchong
    , sum(zhewan) AS zhewan
    , sum(yamao) AS yamao
    , sum(yahan) AS yahan
    , sum(qiege) AS qiege
    , sum(dakong) AS dakong
    , sum(gongsi) AS gongsi
    , sum(hanjie) AS hanjie
    , sum(damo) AS damo
    , sum(lasi) AS lasi
    , sum(siyin) AS siyin
    , sum(peitu) AS peitu
    , sum(zuzhuang) AS zuzhuang
FROM mrp_task_routing AS a
    LEFT JOIN vw_task_housrs AS b ON a.Product_ID = b.Product_ID
WHERE a.task_routing_type = '1'
GROUP BY a.task_routing_id

/*
 * ��ȡ�¼����ݣ���֯����������
 */
-- DROP  FUNCTION getChildLst;
CREATE  FUNCTION getChildLst(rootId VARCHAR(100)) RETURNS varchar(1000) CHARSET utf8
BEGIN 
   DECLARE sTemp VARCHAR(10000); 
   DECLARE sTempChd VARCHAR(10000); 
 
   SET sTemp = '$'; 
   SET sTempChd =cast(rootId as CHAR); 
 
   WHILE sTempChd is not null DO 
     SET sTemp = concat(sTemp,',',sTempChd); 
     SELECT group_concat(task_routing_code) INTO sTempChd FROM mrp_task_routing where FIND_IN_SET(up_task_routing_code,sTempChd)>0; 
   END WHILE; 
   RETURN sTemp; 
 END;

