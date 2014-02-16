
/*==============================================================*/
/* mode: CM_RBAC                start                           */
/*==============================================================*/


drop table if exists CM_ORG;

/*==============================================================*/
/* Table: CM_ORG                                                */
/*==============================================================*/
create table CM_ORG
(
   ID                   varchar(36) not null,
   CODE                 varchar(20) not null,
   NAME                 varchar(60) not null,
   SHORTFOR             varchar(20),
   HIGHER_ID            varchar(36),
   ORG_TYPE             varchar(2) comment '0 公司
            1 部门',
   DIRECTOR             varchar(20),
   ADDRESS              varchar(200),
   TEL                  varchar(20),
   FAX                  varchar(20),
   ZIPCODE              varchar(10),
   Path                 varchar(36),
   Layer                int,
   Detail               char(1) default '1' comment '0、否
            1、是',
   STATUS               varchar(2) not null,
   DESCRIPTION          varchar(256),
   primary key (ID)
);

alter table CM_ORG comment '机构信息表';
drop table if exists CM_USER;

/*==============================================================*/
/* Table: CM_USER                                               */
/*==============================================================*/
create table CM_USER
(
   ID                   varchar(36) not null,
   CODE                 varchar(20) not null,
   ACCOUNT              varchar(20) not null,
   NAME                 varchar(20) not null,
   ORGID                varchar(36) not null,
   PASSWD               varchar(100) not null,
   STATUS               varchar(2) not null,
   DESCRIPTION          varchar(256),
   primary key (ID)
);

alter table CM_USER comment '操作员信息表';
drop table if exists CM_ROLE;

/*==============================================================*/
/* Table: CM_ROLE                                               */
/*==============================================================*/
create table CM_ROLE
(
   ID                   varchar(36) not null,
   CODE                 varchar(20) not null,
   NAME                 varchar(50) not null,
   STATUS               varchar(2) not null,
   IS_EXT               varchar(2) not null,
   DESCRIPTION          varchar(256),
   primary key (ID)
);

alter table CM_ROLE comment '角色表';
drop table if exists CM_RESOURCE;

/*==============================================================*/
/* Table: CM_RESOURCE                                           */
/*==============================================================*/
create table CM_RESOURCE
(
   ID                   varchar(36) not null,
   NAME                 varchar(50) not null,
   URI                  varchar(200) not null,
   TYPE                 varchar(2) not null,
   STATUS               varchar(2) not null,
   DESCRIPTION          varchar(256),
   primary key (ID)
);

alter table CM_RESOURCE comment '资源表';
drop table if exists CM_USER_ORG_REL;

/*==============================================================*/
/* Table: CM_USER_ORG_REL                                       */
/*==============================================================*/
create table CM_USER_ORG_REL
(
   USER_ID              varchar(36) not null,
   ORG_ID               varchar(36) not null,
   primary key (USER_ID, ORG_ID)
);

alter table CM_USER_ORG_REL comment '操作员_机构_关系表';
drop table if exists CM_USER_ROLE_REL;

/*==============================================================*/
/* Table: CM_USER_ROLE_REL                                      */
/*==============================================================*/
create table CM_USER_ROLE_REL
(
   USER_ID              varchar(36) not null,
   ROLE_ID              varchar(36) not null,
   primary key (USER_ID, ROLE_ID)
);

alter table CM_USER_ROLE_REL comment '操作员_角色关系表';
drop table if exists CM_ROLE_RESOURCE_REL;

/*==============================================================*/
/* Table: CM_ROLE_RESOURCE_REL                                  */
/*==============================================================*/
create table CM_ROLE_RESOURCE_REL
(
   ROLE_ID              varchar(36) not null,
   RESOURCE_ID          varchar(36) not null,
   primary key (ROLE_ID, RESOURCE_ID)
);

alter table CM_ROLE_RESOURCE_REL comment '角色_资源关系表';
drop table if exists CM_MENU;

/*==============================================================*/
/* Table: CM_MENU                                               */
/*==============================================================*/
create table CM_MENU
(
   ID                   varchar(36) not null,
   CODE                 varchar(20) not null,
   NAME                 varchar(50) not null,
   HIGHER_ID            varchar(36) not null,
   SEQ                  int(11),
   RESOURCE_ID          varchar(36),
   ICON_URI             varchar(200),
   HINT                 varchar(50),
   Path                 varchar(36),
   Layer                int,
   Detail               char(1) default '1' comment '0、否
            1、是',
   STATUS               varchar(2) not null,
   DESCRIPTIOIN         varchar(256),
   primary key (ID)
);

alter table CM_MENU comment '功能菜单表';


/*==============================================================*/
/* mode: CM_RBAC                end                           */
/*==============================================================*/


/*==============================================================*/
/* mode: CM_DataBase                start                       */
/*==============================================================*/
drop table if exists CM_DICT_DEF;

/*==============================================================*/
/* Table: CM_DICT_DEF                                           */
/*==============================================================*/
create table CM_DICT_DEF
(
   ID                   varchar(36) not null,
   CODE                 varchar(20) not null,
   NAME                 varchar(50) not null,
   TYPE                 varchar(2) not null,
   VALUE                varchar(40),
   STATUS               varchar(2) not null,
   DESCRIPTION          varchar(256),
   primary key (ID)
);

alter table CM_DICT_DEF comment '参数字典类型定义表';
drop table if exists CM_DICT_ITEM;

/*==============================================================*/
/* Table: CM_DICT_ITEM                                          */
/*==============================================================*/
create table CM_DICT_ITEM
(
   ID                   varchar(36) not null,
   CODE                 varchar(36),
   DICT_DEF_ID          varchar(36),
   HIGHER_ID            varchar(36) not null,
   NAME                 varchar(40) not null,
   VALUE                varchar(40) not null,
   SEQ                  int(11),
   Path                 varchar(36),
   Layer                int,
   Detail               char(1) default '1' comment '0、否
            1、是',
   STATUS               varchar(2),
   DESCRIPTION          varchar(256),
   primary key (ID)
);

alter table CM_DICT_ITEM comment '参数字典数据项定义表';
drop table if exists CM_KEY_GENERATOR;

/*==============================================================*/
/* Table: CM_KEY_GENERATOR                                      */
/*==============================================================*/
create table CM_KEY_GENERATOR
(
   KEY_ID               varchar(100) not null,
   MAX_VAL              int(11) not null,
   CIRCLE               varchar(20) not null,
   ORG_ID               varchar(36) not null,
   DESCRIPTION          varchar(512),
   primary key (KEY_ID, ORG_ID)
);

alter table CM_KEY_GENERATOR comment '主键生成器信息表';

drop table if exists Product_Category;

/*==============================================================*/
/* Table: Product_Category                                      */
/*==============================================================*/
create table Product_Category
(
   ID                   varchar(36) not null,
   Company_ID           varchar(36) not null,
   Dept_ID              varchar(36) not null,
   BusiOrg_ID           varchar(36) not null,
   Code                 varchar(30) not null,
   Name                 varchar(512) not null,
   Path                 varchar(36),
   Layer                int,
   Detail               char(1) default '1' comment '0、否
            1、是',
   Note                 varchar(255),
   primary key (ID)
);

alter table Product_Category comment '商品物料类别';


drop table if exists Product_Product;

/*==============================================================*/
/* Table: Product_Product                                       */
/*==============================================================*/
create table Product_Product
(
   ID                   varchar(36) not null,
   Company_ID           varchar(36) not null,
   Dept_ID              varchar(36) not null,
   BusiOrg_ID           varchar(36) not null,
   Type                 varchar(2) comment '原材料
            库存商品
            易耗品
            服务
            费用
            ',
   Categ_ID             varchar(36) comment 'categ 原材料、产成品等分类信息
            ',
   Code                 varchar(36) not null,
   Name                 varchar(128) not null,
   Short_Name           varchar(60) not null,
   Help_Tag             varchar(60),
   Bar_Code             varchar(30),
   Specs                varchar(1024),
   Brand                varchar(60),
   Warranty             varchar(36),
   Uom_ID               varchar(36),
   Uom_Precision        int,
   Each_Weight          decimal(20,8) default 0,
   Weight_Unit_ID       varchar(36),
   Each_Volume          decimal(20,8) default 0,
   Volume_Unit_ID       varchar(36),
   Supply_Method        char(1) comment '0 采购
            1 生产',
   Purchase_Ok          char(1),
   Sale_Ok              char(1),
   Sale_Delay           int,
   Desc_Purchase        varchar(512),
   Produce_Delay        int,
   Product_Manager      varchar(36),
   Procure_Method       char(1) comment '0备货型生产
            1按订单生产',
   Cost_Method          char(1),
   Desc_Feature         varchar(512),
   Desc_Sale            varchar(512),
   Loc_Rack             varchar(36),
   Loc_Row              varchar(36),
   State                char(1),
   Note                 varchar(512),
   CreateCompany_ID     varchar(36) comment '创建物料的公司代码',
   CreateDate           varchar(20) comment '物料的创建日期',
   CreateUser           varchar(20) comment '创建物料的人员姓名',
   WriteDate            varchar(20) comment '物料的创建日期',
   WriteUser            varchar(20) comment '创建物料的人员姓名',
   primary key (ID)
);

alter table Product_Product comment '产品：包含原材料及商品（产成品）';


/*==============================================================*/
/* mode: CM_DataBase                end                           */
/*==============================================================*/


/*==============================================================*/
/* mode: CM_Stock                begin                          */
/*==============================================================*/
drop table if exists Stock_Picking;

/*==============================================================*/
/* Table: Stock_Picking                                         */
/*==============================================================*/
create table Stock_Picking
(
   ID                   varchar(36),
   Company_ID           varchar(36) not null,
   Dept_ID              varchar(36) not null,
   BusiOrg_ID           varchar(36) not null,
   Code                 varchar(36),
   Name                 varchar(60),
   Type                 varchar(2) comment '0 收货
            1 发货',
   Move_Type            varchar(2) comment '01 正常入库
            02 生产入库
            10 正常出库
            11 生产领用',
   Send_Type            varchar(2) comment '0 全部
            1 部分',
   Origin               varchar(36),
   Purchase_ID          varchar(36),
   Sale_ID              varchar(36),
   Project_ID           varchar(36),
   Partner_ID           varchar(36),
   Warehose_ID          varchar(36),
   Pred_Date            varchar(20),
   Recpt_IN_Date        varchar(20),
   QC_Date              varchar(20),
   Recpt_Out_Date       varchar(20),
   Deal_End_Date        varchar(20),
   State                varchar(2),
   Invoice_State        varchar(2),
   Auto_Picking         char(1),
   Back_Order_ID        varchar(36),
   Account_Date         varchar(20),
   Stock_Journal_ID     varchar(36),
   Move_IN_User         varchar(20),
   Move_Out_User        varchar(20),
   Move_Destined        varchar(2),
   Note                 varchar(512),
   CreateUser           varchar(20),
   CreateDate           varchar(20),
   WriteDate            varchar(20) comment '物料的创建日期',
   WriteUser            varchar(20) comment '创建物料的人员姓名'
);

alter table Stock_Picking comment '出入库单';
drop table if exists Stock_Move;

/*==============================================================*/
/* Table: Stock_Move                                            */
/*==============================================================*/
create table Stock_Move
(
   ID                   varchar(36),
   Company_ID           varchar(36) not null,
   Dept_ID              varchar(36) not null,
   BusiOrg_ID           varchar(36) not null,
   Picking_ID           varchar(36),
   Origin               varchar(36),
   Purchase_ID          varchar(36),
   Purchase_Line_ID     varchar(36),
   Sale_ID              varchar(36),
   Sale_Line_ID         varchar(36),
   Type                 varchar(2) comment '0 收货
            1 发货',
   Product_ID           varchar(36),
   Code                 varchar(60),
   Name                 varchar(60),
   Partner_ID           varchar(36),
   Ware_Hose_ID         varchar(36),
   Location_ID          varchar(36),
   Product_Uom          varchar(36),
   Price_Currency       varchar(36),
   Source_Price_Unit    decimal(20,8),
   Price_Unit           decimal(20,8),
   Tar_Product_Qty      decimal(20,8),
   Product_Qty          decimal(20,8),
   Pred_Date            varchar(20),
   Real_Date            varchar(20),
   Move_End_Date        varchar(20),
   QC_Type              varchar(2),
   QC_Flag              varchar(2),
   State                varchar(2),
   Inventory_State      varchar(2) comment '1 可用
            2 待检
            3 在途
            4 冻结',
   Valuation_State      varchar(2) default '0' comment '00/默认值
            01/存货待审
            02/存货审核中
            03/存货审核未通过
            04/存货已审
            10/存货记帐
            ',
   Busi_Batch           varchar(36),
   Auto_Picking         char(1),
   Note                 varchar(512),
   CreateUser           varchar(20),
   CreateDate           varchar(20),
   WriteDate            varchar(20) comment '物料的创建日期',
   WriteUser            varchar(20) comment '创建物料的人员姓名'
);

alter table Stock_Move comment '货物移动';


/*==============================================================*/
/* mode: CM_Stock                end                           */
/*==============================================================*/
