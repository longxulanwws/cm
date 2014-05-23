DROP VIEW IF EXISTS vw_actual_stock;

CREATE VIEW vw_actual_stock AS 
SELECT Code, sum((CASE WHEN (Type = '0') THEN Product_Qty ELSE  (0 - Product_Qty ) END)) AS Actual FROM stock_move WHERE stock_move.State ='20'  GROUP BY CODE;

DROP VIEW IF EXISTS vw_occupy_stock;

CREATE VIEW vw_occupy_stock AS 
SELECT Code, sum((CASE WHEN (Type = '1') THEN Product_Qty ELSE 0 END )) AS Occupy FROM stock_move WHERE stock_move.State ='10' GROUP BY CODE;


DROP VIEW IF EXISTS view_stock_acoc_qty;

CREATE VIEW view_stock_acoc_qty AS  
SELECT CASE WHEN Actual IS NULL THEN 0 ELSE Actual END AS Product_Actual_Qty,
CASE WHEN Occupy IS NULL THEN 0 ELSE Occupy END AS Product_Occupy_Qty, 
product_product.ID,product_product.COMPANY_ID,product_product.DEPT_ID,product_product.BUSIORG_ID,product_product.TYPE,product_product.CATEG_ID,product_product.CODE,product_product.NAME,product_product.SHORT_NAME,product_product.HELP_TAG,product_product.BAR_CODE,product_product.SPECS,product_product.BRAND,product_product.WARRANTY,product_product.UOM_ID,product_product.UOM_PRECISION,product_product.EACH_WEIGHT,product_product.WEIGHT_UNIT_ID,product_product.EACH_VOLUME,product_product.VOLUME_UNIT_ID,product_product.SUPPLY_METHOD,product_product.PURCHASE_OK,product_product.SALE_OK,product_product.SALE_DELAY,product_product.DESC_PURCHASE,product_product.PRODUCE_DELAY,product_product.PRODUCT_MANAGER,product_product.PROCURE_METHOD,product_product.COST_METHOD,product_product.DESC_SALE,product_product.LOC_RACK,product_product.LOC_ROW,product_product.STATE,product_product.NOTE,product_product.CREATECOMPANY_ID,product_product.CREATEDATE,product_product.CREATEUSER,product_product.WRITEDATE,product_product.WRITEUSER
FROM product_product
LEFT JOIN  vw_actual_stock  AS mva ON mva.code=product_product.code
LEFT JOIN  vw_occupy_stock AS mvb ON mvb.code=product_product.code
 ;
 
DROP VIEW IF EXISTS view_stock_qty;

CREATE VIEW view_stock_qty AS 
SELECT Product_Actual_Qty, Product_Occupy_Qty,(Product_Actual_Qty- Product_Occupy_Qty) AS Product_Enable_Qty , 
product_product.ID,product_product.COMPANY_ID,product_product.DEPT_ID,product_product.BUSIORG_ID,product_product.TYPE,product_product.CATEG_ID,product_product.CODE,product_product.NAME,product_product.SHORT_NAME,product_product.HELP_TAG,product_product.BAR_CODE,product_product.SPECS,product_product.BRAND,product_product.WARRANTY,product_product.UOM_ID,product_product.UOM_PRECISION,product_product.EACH_WEIGHT,product_product.WEIGHT_UNIT_ID,product_product.EACH_VOLUME,product_product.VOLUME_UNIT_ID,product_product.SUPPLY_METHOD,product_product.PURCHASE_OK,product_product.SALE_OK,product_product.SALE_DELAY,product_product.DESC_PURCHASE,product_product.PRODUCE_DELAY,product_product.PRODUCT_MANAGER,product_product.PROCURE_METHOD,product_product.COST_METHOD,product_product.DESC_SALE,product_product.LOC_RACK,product_product.LOC_ROW,product_product.STATE,product_product.NOTE,product_product.CREATECOMPANY_ID,product_product.CREATEDATE,product_product.CREATEUSER,product_product.WRITEDATE,product_product.WRITEUSER
FROM view_stock_acoc_qty AS  product_product  ;

















/*
DROP VIEW IF EXISTS view_stock_qty;

CREATE VIEW view_stock_qty AS 
SELECT CASE WHEN Actual IS NULL THEN 0 ELSE Actual END AS Product_Actual_Qty,
CASE WHEN Occupy IS NULL THEN 0 ELSE Occupy END AS Product_Occupy_Qty, 
CASE WHEN (Actual-Occupy) IS NULL THEN 0 ELSE (Actual-Occupy) END  AS Product_Enable_Qty, 
product_product.ID,product_product.COMPANY_ID,product_product.DEPT_ID,product_product.BUSIORG_ID,product_product.TYPE,product_product.CATEG_ID,product_product.CODE,product_product.NAME,product_product.SHORT_NAME,product_product.HELP_TAG,product_product.BAR_CODE,product_product.SPECS,product_product.BRAND,product_product.WARRANTY,product_product.UOM_ID,product_product.UOM_PRECISION,product_product.EACH_WEIGHT,product_product.WEIGHT_UNIT_ID,product_product.EACH_VOLUME,product_product.VOLUME_UNIT_ID,product_product.SUPPLY_METHOD,product_product.PURCHASE_OK,product_product.SALE_OK,product_product.SALE_DELAY,product_product.DESC_PURCHASE,product_product.PRODUCE_DELAY,product_product.PRODUCT_MANAGER,product_product.PROCURE_METHOD,product_product.COST_METHOD,product_product.DESC_SALE,product_product.LOC_RACK,product_product.LOC_ROW,product_product.STATE,product_product.NOTE,product_product.CREATECOMPANY_ID,product_product.CREATEDATE,product_product.CREATEUSER,product_product.WRITEDATE,product_product.WRITEUSER
FROM product_product
LEFT JOIN  vw_actual_stock  AS mva ON mva.code=product_product.code
LEFT JOIN  vw_occupy_stock AS mvb ON mvb.code=product_product.code
WHERE mva.actual>0 OR mvb.Occupy>0 ;
*/


/*
 * 按品牌统计库存处理 
 */

CREATE VIEW vw_actual_stock_brand AS 
SELECT Code, (CASE WHEN brand IS NULL THEN '' ELSE brand END) AS brand ,sum((CASE WHEN (Type = '0') THEN Product_Qty ELSE  (0 - Product_Qty ) END)) AS Actual FROM stock_move WHERE stock_move.State ='20'  GROUP BY brand,CODE;

DROP VIEW IF EXISTS vw_occupy_stock;

CREATE VIEW vw_occupy_stock_brand AS 
SELECT Code, (CASE WHEN brand IS NULL THEN '' ELSE brand END) AS brand,sum((CASE WHEN (Type = '1') THEN Product_Qty ELSE 0 END )) AS Occupy FROM stock_move WHERE stock_move.State ='10' GROUP BY brand,CODE;

/*
 * 
 * 
 * */
SELECT code FROM cm_dict_item WHERE DICT_DEF_ID='0023'


SELECT * FROM stock_move


(SELECT brand.code AS brandcode,product_product.* FROM product_product
,(SELECT code FROM cm_dict_item WHERE DICT_DEF_ID='0023') AS brand
) AS product_brand

SELECT * FROM 
(
SELECT brand.code AS brandcode,product_product.* FROM product_product
,(SELECT code FROM cm_dict_item WHERE DICT_DEF_ID='0023') AS brand
) AS product_brand
LEFT JOIN vw_actual_stock_brand  AS vma ON vma.brand =product_brand.brandcode AND product_brand.Code=vma.code
LEFT JOIN vw_occupy_stock_brand  AS vmb ON vmb.brand =product_brand.brandcode AND product_brand.Code=vmb.code

/**
 * 20140523 wws 新增加 产品部件工时定额相关试图
 */

DROP VIEW IF EXISTS vw_pro_hours_ph;
CREATE VIEW vw_pro_hours_ph AS 
SELECT product_id,
sum(CASE task_routing_code WHEN '001' THEN task_routing_standard_hour  ELSE '' END) AS waixie  ,
sum(CASE task_routing_code WHEN '002' THEN task_routing_standard_hour  ELSE '' END) AS beiliao ,
sum(CASE task_routing_code WHEN '003' THEN task_routing_standard_hour  ELSE '' END) AS jiguang ,
sum(CASE task_routing_code WHEN '004' THEN task_routing_standard_hour  ELSE '' END) AS shuchong,
sum(CASE task_routing_code WHEN '005' THEN task_routing_standard_hour  ELSE '' END) AS zhewan  ,
sum(CASE task_routing_code WHEN '006' THEN task_routing_standard_hour  ELSE '' END) AS yamao   ,
sum(CASE task_routing_code WHEN '007' THEN task_routing_standard_hour  ELSE '' END) AS yahan   ,
sum(CASE task_routing_code WHEN '008' THEN task_routing_standard_hour  ELSE '' END) AS qiege   ,
sum(CASE task_routing_code WHEN '009' THEN task_routing_standard_hour  ELSE '' END) AS dakong  ,
sum(CASE task_routing_code WHEN '010' THEN task_routing_standard_hour  ELSE '' END) AS gongsi  ,
sum(CASE task_routing_code WHEN '011' THEN task_routing_standard_hour  ELSE '' END) AS hanjie  ,
sum(CASE task_routing_code WHEN '012' THEN task_routing_standard_hour  ELSE '' END) AS damo    ,
sum(CASE task_routing_code WHEN '013' THEN task_routing_standard_hour  ELSE '' END) AS lasi    ,
sum(CASE task_routing_code WHEN '014' THEN task_routing_standard_hour  ELSE '' END) AS siyin   ,
sum(CASE task_routing_code WHEN '015' THEN task_routing_standard_hour  ELSE '' END) AS peitu   ,
sum(CASE task_routing_code WHEN '016' THEN  task_routing_standard_hour ELSE '' END) AS zuzhuang 
FROM mrp_task_routing WHERE task_routing_type='3' GROUP BY  product_id ;

DROP VIEW IF EXISTS vw_pro_hours_th;
CREATE VIEW vw_pro_hours_th AS
SELECT product_id,up_task_routing_code AS up_task_code ,
sum(CASE task_routing_code WHEN '001' THEN task_routing_standard_hour  ELSE '' END) AS waixie  ,
sum(CASE task_routing_code WHEN '002' THEN task_routing_standard_hour  ELSE '' END) AS beiliao ,
sum(CASE task_routing_code WHEN '003' THEN task_routing_standard_hour  ELSE '' END) AS jiguang ,
sum(CASE task_routing_code WHEN '004' THEN task_routing_standard_hour  ELSE '' END) AS shuchong,
sum(CASE task_routing_code WHEN '005' THEN task_routing_standard_hour  ELSE '' END) AS zhewan  ,
sum(CASE task_routing_code WHEN '006' THEN task_routing_standard_hour  ELSE '' END) AS yamao   ,
sum(CASE task_routing_code WHEN '007' THEN task_routing_standard_hour  ELSE '' END) AS yahan   ,
sum(CASE task_routing_code WHEN '008' THEN task_routing_standard_hour  ELSE '' END) AS qiege   ,
sum(CASE task_routing_code WHEN '009' THEN task_routing_standard_hour  ELSE '' END) AS dakong  ,
sum(CASE task_routing_code WHEN '010' THEN task_routing_standard_hour  ELSE '' END) AS gongsi  ,
sum(CASE task_routing_code WHEN '011' THEN task_routing_standard_hour  ELSE '' END) AS hanjie  ,
sum(CASE task_routing_code WHEN '012' THEN task_routing_standard_hour  ELSE '' END) AS damo    ,
sum(CASE task_routing_code WHEN '013' THEN task_routing_standard_hour  ELSE '' END) AS lasi    ,
sum(CASE task_routing_code WHEN '014' THEN task_routing_standard_hour  ELSE '' END) AS siyin   ,
sum(CASE task_routing_code WHEN '015' THEN task_routing_standard_hour  ELSE '' END) AS peitu   ,
sum(CASE task_routing_code WHEN '016' THEN  task_routing_standard_hour ELSE '' END) AS zuzhuang 
FROM mrp_task_routing WHERE task_routing_type='3' GROUP BY  product_id,up_task_routing_code;

DROP VIEW IF EXISTS vw_pro_hours_ptm;
CREATE VIEW vw_pro_hours_ptm AS 
SELECT Product_ID,sum(task_routing_qty) AS p_task_num  FROM mrp_task_routing GROUP BY Product_ID;

DROP VIEW IF EXISTS vw_pro_hours;
CREATE VIEW vw_pro_hours AS 
SELECT a.*,p_task_num*beiliao  AS beiliao , 
p_task_num*jiguang  AS jiguang , 
p_task_num*shuchong AS shuchong, 
p_task_num*zhewan   AS zhewan  , 
p_task_num*yamao    AS yamao   , 
p_task_num*yahan    AS yahan   , 
p_task_num*qiege    AS qiege   , 
p_task_num*dakong   AS dakong  , 
p_task_num*gongsi   AS gongsi  , 
p_task_num*hanjie   AS hanjie  , 
p_task_num*damo     AS damo    , 
p_task_num*lasi     AS lasi    , 
p_task_num*siyin    AS siyin   , 
p_task_num*peitu    AS peitu   , 
p_task_num*zuzhuang AS zuzhuang  
FROM  mrp_task_routing  AS a
LEFT JOIN vw_pro_hours_ph AS b ON  a.product_id=B.product_id
LEFT JOIN vw_pro_hours_ptm AS c  ON c.product_id =a.product_id 
WHERE a.task_routing_type = '1'

UNION

SELECT a.*,a.task_routing_qty*beiliao  AS beiliao , 
a.task_routing_qty*jiguang  AS jiguang , 
a.task_routing_qty*shuchong AS shuchong, 
a.task_routing_qty*zhewan   AS zhewan  , 
a.task_routing_qty*yamao    AS yamao   , 
a.task_routing_qty*yahan    AS yahan   , 
a.task_routing_qty*qiege    AS qiege   , 
a.task_routing_qty*dakong   AS dakong  , 
a.task_routing_qty*gongsi   AS gongsi  , 
a.task_routing_qty*hanjie   AS hanjie  , 
a.task_routing_qty*damo     AS damo    , 
a.task_routing_qty*lasi     AS lasi    , 
a.task_routing_qty*siyin    AS siyin   , 
a.task_routing_qty*peitu    AS peitu   , 
a.task_routing_qty*zuzhuang AS zuzhuang  
FROM  mrp_task_routing AS  a
LEFT JOIN vw_pro_hours_th AS B ON A.task_routing_code =B.up_task_code AND a.product_id=B.product_id
WHERE a.task_routing_type = '2' ;


DROP VIEW

IF EXISTS vw_pro_hours_ph;

CREATE VIEW vw_pro_hours_ph AS
    SELECT product_id
        , sum (CASE task_routing_code WHEN '001' THEN task_routing_standard_hour ELSE '' END) AS waixie
        , sum (CASE task_routing_code WHEN '002' THEN task_routing_standard_hour ELSE '' END) AS beiliao
        , sum (CASE task_routing_code WHEN '003' THEN task_routing_standard_hour ELSE '' END) AS jiguang
        , sum (CASE task_routing_code WHEN '004' THEN task_routing_standard_hour ELSE '' END) AS shuchong
        , sum (CASE task_routing_code WHEN '005' THEN task_routing_standard_hour ELSE '' END) AS zhewan
        , sum (CASE task_routing_code WHEN '006' THEN task_routing_standard_hour ELSE '' END) AS yamao
        , sum (CASE task_routing_code WHEN '007' THEN task_routing_standard_hour ELSE '' END) AS yahan
        , sum (CASE task_routing_code WHEN '008' THEN task_routing_standard_hour ELSE '' END) AS qiege
        , sum (CASE task_routing_code WHEN '009' THEN task_routing_standard_hour ELSE '' END) AS dakong
        , sum (CASE task_routing_code WHEN '010' THEN task_routing_standard_hour ELSE '' END) AS gongsi
        , sum (CASE task_routing_code WHEN '011' THEN task_routing_standard_hour ELSE '' END) AS hanjie
        , sum (CASE task_routing_code WHEN '012' THEN task_routing_standard_hour ELSE '' END) AS damo
        , sum (CASE task_routing_code WHEN '013' THEN task_routing_standard_hour ELSE '' END) AS lasi
        , sum (CASE task_routing_code WHEN '014' THEN task_routing_standard_hour ELSE '' END) AS siyin
        , sum (CASE task_routing_code WHEN '015' THEN task_routing_standard_hour ELSE '' END) AS peitu
        , sum (CASE task_routing_code WHEN '016' THEN task_routing_standard_hour ELSE '' END) AS zuzhuang
    FROM mrp_task_routing
    WHERE task_routing_type = '3'
    GROUP BY product_id;
    
DROP VIEW

    IF EXISTS vw_pro_hours_th;

CREATE VIEW vw_pro_hours_th AS
        SELECT product_id
            , up_task_routing_code AS up_task_code
            , sum (CASE task_routing_code WHEN '001' THEN task_routing_standard_hour ELSE '' END) AS waixie
            , sum (CASE task_routing_code WHEN '002' THEN task_routing_standard_hour ELSE '' END) AS beiliao
            , sum (CASE task_routing_code WHEN '003' THEN task_routing_standard_hour ELSE '' END) AS jiguang
            , sum (CASE task_routing_code WHEN '004' THEN task_routing_standard_hour ELSE '' END) AS shuchong
            , sum (CASE task_routing_code WHEN '005' THEN task_routing_standard_hour ELSE '' END) AS zhewan
            , sum (CASE task_routing_code WHEN '006' THEN task_routing_standard_hour ELSE '' END) AS yamao
            , sum (CASE task_routing_code WHEN '007' THEN task_routing_standard_hour ELSE '' END) AS yahan
            , sum (CASE task_routing_code WHEN '008' THEN task_routing_standard_hour ELSE '' END) AS qiege
            , sum (CASE task_routing_code WHEN '009' THEN task_routing_standard_hour ELSE '' END) AS dakong
            , sum (CASE task_routing_code WHEN '010' THEN task_routing_standard_hour ELSE '' END) AS gongsi
            , sum (CASE task_routing_code WHEN '011' THEN task_routing_standard_hour ELSE '' END) AS hanjie
            , sum (CASE task_routing_code WHEN '012' THEN task_routing_standard_hour ELSE '' END) AS damo
            , sum (CASE task_routing_code WHEN '013' THEN task_routing_standard_hour ELSE '' END) AS lasi
            , sum (CASE task_routing_code WHEN '014' THEN task_routing_standard_hour ELSE '' END) AS siyin
            , sum (CASE task_routing_code WHEN '015' THEN task_routing_standard_hour ELSE '' END) AS peitu
            , sum (CASE task_routing_code WHEN '016' THEN task_routing_standard_hour ELSE '' END) AS zuzhuang
        FROM mrp_task_routing
        WHERE task_routing_type = '3'
        GROUP BY product_id, up_task_routing_code;
        
DROP VIEW

        IF EXISTS vw_pro_hours_ptm;

CREATE VIEW vw_pro_hours_ptm AS
            SELECT Product_ID
                , sum (task_routing_qty) AS p_task_num
            FROM mrp_task_routing
            GROUP BY Product_ID;
            
DROP VIEW

            IF EXISTS vw_pro_hours;

CREATE VIEW vw_pro_hours AS
                SELECT a. *
                    , p_task_num * beiliao AS beiliao
                    , p_task_num * jiguang AS jiguang
                    , p_task_num * shuchong AS shuchong
                    , p_task_num * zhewan AS zhewan
                    , p_task_num * yamao AS yamao
                    , p_task_num * yahan AS yahan
                    , p_task_num * qiege AS qiege
                    , p_task_num * dakong AS dakong
                    , p_task_num * gongsi AS gongsi
                    , p_task_num * hanjie AS hanjie
                    , p_task_num * damo AS damo
                    , p_task_num * lasi AS lasi
                    , p_task_num * siyin AS siyin
                    , p_task_num * peitu AS peitu
                    , p_task_num * zuzhuang AS zuzhuang
                FROM mrp_task_routing AS a
                    LEFT JOIN vw_pro_hours_ph AS b ON a.product_id = B.product_id
                    LEFT JOIN vw_pro_hours_ptm AS c ON c.product_id = a.product_id
                WHERE a.task_routing_type = '1'
                UNION
                SELECT a. *
                    , a.task_routing_qty * beiliao AS beiliao
                    , a.task_routing_qty * jiguang AS jiguang
                    , a.task_routing_qty * shuchong AS shuchong
                    , a.task_routing_qty * zhewan AS zhewan
                    , a.task_routing_qty * yamao AS yamao
                    , a.task_routing_qty * yahan AS yahan
                    , a.task_routing_qty * qiege AS qiege
                    , a.task_routing_qty * dakong AS dakong
                    , a.task_routing_qty * gongsi AS gongsi
                    , a.task_routing_qty * hanjie AS hanjie
                    , a.task_routing_qty * damo AS damo
                    , a.task_routing_qty * lasi AS lasi
                    , a.task_routing_qty * siyin AS siyin
                    , a.task_routing_qty * peitu AS peitu
                    , a.task_routing_qty * zuzhuang AS zuzhuang
                FROM mrp_task_routing AS a
                    LEFT JOIN vw_pro_hours_th AS B ON A.task_routing_code = B.up_task_code AND a.product_id = B.product_id
                WHERE a.task_routing_type = '2';



