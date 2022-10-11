package bugzilla.mbteclo.state;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Bug {

    private int id;
    private double estimate;
    private String product="";
	private String component="";
	private String version="";
	private String status="";
	private String assignee="";
	private String cc="";
	private String deadline="";
	private String alias="";
	private String url="";
	private String seeAlso="";
	private String severity="";
	private String priority="";
	private String hardware="";
	private String os="";
	private String description="";
	private String summary="";
	private String resolution="";
	private String statusWhiteboard ="";

    private Bug duplicateOf = null;
    private Set<Bug> duplicates = new HashSet<>();

	private Set<Bug> dependsOn = new HashSet<>();
	private Set<Bug> blocks = new HashSet<>();

    public Bug() {

    }
    
    public Bug(String product, String component, String version, String severity, String priority, String summary, String hardware, String os, String status) {
    	this.product = product;
    	this.component = component;
		this.version = version;
    	this.severity = severity;
    	this.priority = priority;
    	this.summary = summary;
    	this.hardware = hardware;
    	this.os = os;
    	this.status = status;
    }
    
    public Bug(String product, String component, String version, String severity, String priority, String summary, String hardware, String os, String status, String seeAlso) {
    	this(product, component, version, severity, priority, summary, hardware, os, status);
    	this.seeAlso = seeAlso;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description= description;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
    
	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public double getEstimate() {
		return estimate;
	}

	public void setEstimate(double estimate) {
		this.estimate = estimate;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSeeAlso() {
		return seeAlso;
	}

	public void setSeeAlso(String seeAlso) {
		this.seeAlso = seeAlso;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getHardware() {
		return hardware;
	}

	public void setHardware(String hardware) {
		this.hardware = hardware;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}
	
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
    	if(this.resolution.equals("DUPLICATE") && !resolution.equals("DUPLICATE")){
			if(this.duplicateOf != null){
				this.duplicateOf.duplicates.remove(this);
				this.duplicateOf = null;
			}
		}
		this.resolution = resolution;
	}

	public boolean isAValidBug(Collection<Bug> bugsUpToNow) {
		boolean summaryIsFilled = !summary.isEmpty();
		boolean assigneeIsAUser = assignee.isEmpty() || assignee.equals("admin");
		boolean ccIsAUser = cc.isEmpty() || cc.equals("admin");
		boolean dateMatchesRegex =  deadline.equals("") ||
				deadline.matches("^((19\\d{2})|(20\\d{2}))-(((02)-(0[1-9]|[1-2][0-9]))|(((0(1|[3-9]))|(1[0-2]))-(0[1-9]|[1-2][0-9]|30))|((01|03|05|07|08|10|12)-(31)))");
		boolean hasUniqueAlias = getAlias().equals("") ||
				bugsUpToNow.stream()
					.filter(bug -> !bug.getAlias().equals(""))
					.noneMatch(bug -> this.getAlias().equals(bug.getAlias()));
		boolean seeAlsoIsCorrect = getSeeAlso() == "" ||
				getSeeAlso().matches("https://bugs.debian.org/[0-9]*");
//		boolean blockerExists = bugsUpToNow.stream()
//					.map(bug -> bug.getId())
//					.anyMatch(id -> id == this.blocks);
//		boolean dependsOnExists = bugsUpToNow.stream()
//					.map(bug -> bug.getId())
//					.anyMatch(id -> id == this.dependsOn);
		/*#if ($CommentOnBugCreation || $CommentOnAllTransitions)*/
		boolean validComment = !getDescription().isEmpty();
		/*#end*/

		return summaryIsFilled && 
				assigneeIsAUser && 
				ccIsAUser &&
				dateMatchesRegex &&
				hasUniqueAlias &&
				seeAlsoIsCorrect
				/*#if ($CommentOnBugCreation || $CommentOnAllTransitions)*/
				&&
				validComment
				/*#end*/
				;
//				&&
//				(dependsOn < 1 || dependsOnExists) &&
//				(blocks < 1 || blockerExists) &&
//				(blocks < 1 || dependsOn < 1 || blocks != dependsOn);
	}
	
	public boolean fitsQuery(Query query) {
		boolean matchesQuery = summary.matches(query.getQuery().replaceFirst("\\*", "") + ".*") ||
				String.valueOf(id).equals(query.getQuery());
		boolean matchesStatus = query.getStatus().equals("") || 
				status.equals(query.getStatus());
		boolean matchesProduct = query.getProduct().equals("") || 
				product.equals(query.getProduct());
		boolean matchesComponent = query.getComponent().equals("") ||
				component.equals(query.getComponent());
		boolean matchesVersion = query.getVersion().equals("") ||
				version.equals(query.getVersion());
		boolean matchesResolution = query.getResolution().equals("---") || 
				resolution.equals(query.getResolution());
		boolean matchesSimpleStatus = query.getSimpleStatus().equals("") || 
				"All".equals(query.getSimpleStatus()) || 
				"Open".equals(query.getSimpleStatus()) && 
					(!"RESOLVED".equals(status) && !"VERIFIED".equals(status)) ||
				"Closed".equals(query.getSimpleStatus()) && 
					("RESOLVED".equals(status) || "VERIFIED".equals(status));
		
		return matchesQuery && 
				matchesStatus &&
				matchesSimpleStatus &&
				matchesProduct &&
				matchesComponent &&
				matchesVersion &&
				matchesResolution;
		}

		public String getSimpleStatus(){
			if("RESOLVED".equals(status) || "VERIFIED".equals(status)){
				return "Closed";
			}
    		return "Open";
		}

	/*#if ($UseStatusWhiteboard)*/
	public String getStatusWhiteboard() {
		return statusWhiteboard;
	}

	public void setStatusWhiteboard(String statusWhiteboard) {
		this.statusWhiteboard = statusWhiteboard;
	}
	/*#end*/

	private void addDuplicate(Bug duplicate) {
		this.duplicates.add(duplicate);
	}

	public void setDuplicateOf(Bug original) {
		this.duplicateOf = original;
		original.addDuplicate(this);
	}

	public void addDependsOn(Bug dependsOn) {
		this.dependsOn.add(dependsOn);
		dependsOn.addBlocks(this);
	}

	public Bug getDuplicateOf() {
		return duplicateOf;
	}

	public Set<Bug> getDuplicates() {
		return duplicates;
	}

	public Set<Bug> getDependsOn() {
		return dependsOn;
	}

	public boolean hasOpenBlockers(){
		for (Bug bug : dependsOn) {
			if(bug.getStatus().equals("CONFIRMED") || bug.getStatus().equals("UNCONFIRMED") || bug.getStatus().equals("IN_PROGRESS")){
				return true;
			}
		}
		return false;
	}

	public Set<Bug> getBlocks() {
		return blocks;
	}

	private void addBlocks(Bug blocker) {
		this.blocks.add(blocker);
	}

	public boolean dependencyWouldLeadToCircle(Bug dependsOn){
		return dependsOn.hasDependencyTo(this);
	}

	public boolean hasDependencyTo(Bug bug){
		for (Bug blocker : this.dependsOn) {
			if(blocker == bug || blocker.hasDependencyTo(bug)){
				return true;
			}
		}
		return false;
	}

	public boolean duplicateWouldLeadToCircle(Bug duplicateOf){
		return duplicateOf.isDuplicateOf(this);
	}

	public boolean isDuplicateOf(Bug bug) {
		return this.duplicateOf != null && (this.duplicateOf == bug || this.duplicateOf.isDuplicateOf(bug));
//		for (Bug duplicate : this.duplicates) {
//			if (duplicate == bug || duplicate.isDuplicateOf(bug)) {
//				return true;
//			}
//		}
//		return false;
	}

	@Override
	public String toString() {
		return "Bug{" +
				"id=" + id +
				", estimate=" + estimate +
				", product='" + product + '\'' +
				", component='" + component + '\'' +
				", version='" + version + '\'' +
				", status='" + status + '\'' +
				", assignee='" + assignee + '\'' +
				", cc='" + cc + '\'' +
				", deadline='" + deadline + '\'' +
				", alias='" + alias + '\'' +
				", url='" + url + '\'' +
				", seeAlso='" + seeAlso + '\'' +
				", severity='" + severity + '\'' +
				", priority='" + priority + '\'' +
				", hardware='" + hardware + '\'' +
				", os='" + os + '\'' +
				", description='" + description + '\'' +
				", summary='" + summary + '\'' +
				", resolution='" + resolution + '\'' +
				", statusWhiteboard='" + statusWhiteboard + '\'' +
				", duplicateOf=" + ((duplicateOf == null) ? "null" : duplicateOf.getId()) +
				", duplicates=" + duplicatesToString() +
				", dependsOn=" + dependsOnToString() +
				", blocks=" + blocksToString() +
				'}';
	}

	private String duplicatesToString(){
		StringBuilder s = new StringBuilder("[");
		boolean first = true;
		for (Bug bug : duplicates) {
			if(!first){
				s.append(", ");
			}
			s.append(bug.getId());
			first = false;
		}
		s.append("]");
		return s.toString();
	}

	private String dependsOnToString(){
		StringBuilder s = new StringBuilder("[");
		boolean first = true;
		for (Bug bug : dependsOn) {
			if(!first){
				s.append(", ");
			}
			s.append(bug.getId());
			first = false;
		}
		s.append("]");
		return s.toString();
	}

	private String blocksToString(){
		StringBuilder s = new StringBuilder("[");
		boolean first = true;
		for (Bug bug : blocks) {
			if(!first){
				s.append(", ");
			}
			s.append(bug.getId());
			first = false;
		}
		s.append("]");
		return s.toString();
	}
}
