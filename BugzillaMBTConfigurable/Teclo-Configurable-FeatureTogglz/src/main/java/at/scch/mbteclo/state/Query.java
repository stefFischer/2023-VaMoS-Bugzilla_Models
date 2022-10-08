package at.scch.mbteclo.state;

public class Query {

	private String query="";
	private String status="";
	private String simpleStatus="";
	private String product="";
	private String component="";
	private String version="";
	private String resolution = "---";
	
	public Query() {
		
	}
	
	public Query(String simpleStatus, String product, String query) {
		this.query = query;
		this.simpleStatus = simpleStatus;
		this.product = product;
	}
	
	public Query(String status, String product, String component, String version, String query, String resolution) {
		this.query = query;
		this.status = status;
		this.product = product;
		this.component = component;
		this.version = version;
		this.resolution = resolution;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public String getSimpleStatus() {
		return simpleStatus;
	}

	public void setSimpleStatus(String simpleStatus) {
		this.simpleStatus = simpleStatus;
	}
	
	public String getQuery() {
		return query;
	}

	@Override
	public String toString() {
		return "Query{" +
				"query='" + query + '\'' +
				", status='" + status + '\'' +
				", simpleStatus='" + simpleStatus + '\'' +
				", product='" + product + '\'' +
				", component='" + component + '\'' +
				", resolution='" + resolution + '\'' +
				'}';
	}
}
