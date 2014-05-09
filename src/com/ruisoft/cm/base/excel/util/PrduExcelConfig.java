package com.ruisoft.cm.base.excel.util;

public class PrduExcelConfig {
	// 产品
	protected ProductConfig product = null;
	// 产品生产部件
	protected ProductPartConfig productPart = null;
	// 工艺路线
	protected RoutingConfig routing = null;

	public ProductPartConfig getProductPart() {
		return productPart;
	}

	public void setProductPart(ProductPartConfig productPart) {
		this.productPart = productPart;
	}

	public RoutingConfig getRouting() {
		return routing;
	}

	public void setRouting(RoutingConfig routing) {
		this.routing = routing;
	}

	public ProductConfig getProduct() {
		return product;
	}

	public void setProduct(ProductConfig product) {
		this.product = product;
	}

}
