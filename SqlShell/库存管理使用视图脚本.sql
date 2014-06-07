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
