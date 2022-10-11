package bugzilla.mbteclo.state;

import osmo.tester.annotation.CoverageValue;
import osmo.tester.generator.testsuite.TestCaseStep;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class BugzillaState {

	public final static Product DEFAULT_PRODUCT = new Product("TestProduct");

	public final static String DEFAULT_COMPONENT = "TestComponent";

	public final static String DEFAULT_VERSION = "unspecified";

	private Page page = Page.Start;

	private boolean isPrepared = false;
	private long searchResultSize = 0;
	private static Collection<Bug> bugs = new ArrayList<>();
	private Bug currentlyOpenBug = null;
	private Bug lastOpenBug = null;
	private Query currentQuery = null;
	private Scenario scenario;

//	public void reset() {
//		isPrepared = false;
//		searchResultSize = 0;
//		page = Page.Start;
//		bugs.removeIf((bug) -> true);
//		bugs = new ArrayList<>();
//		currentlyOpenBug = null;
//		currentQuery = null;
//	}

	public BugzillaState() {
		DEFAULT_PRODUCT.addComponent(DEFAULT_COMPONENT);
	}

	public static final String id = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS"));

	public List<Product> getProducts(){
		List<Product> products = new LinkedList<>();
		products.add(DEFAULT_PRODUCT);
		if(ConfigurationOption.AddProduct.isActive()){
			Product addedProduct = new Product("FooProduct");
			addedProduct.addComponent("BarComponent");
			products.add(addedProduct);
		}
		return products;
	}

	public enum Page {
		Start,
		Login, LoginError,
		FileABug, FileABugAdvanced, FileBugError, SelectProduct,
		EditBug, BugChanged, ErrorSummaryNeeded, ErrorCommentRequired, ErrorOpenBlockers, ErrorCircularDependency, ErrorCircularDuplicates,
		Template,
		AdvancedSearch, SearchResult, SimpleSearch, SaveSearchError, SearchSaved, SearchPage,
		BrowsePage
	}

	public enum Scenario {
		Create, Edit, Search, Browse, Login
	}

	public Scenario getScenario() {
		return scenario;
	}

	public void setScenario(Scenario scenario) {
		this.scenario = scenario;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Bug getBug() {
		return bugs.stream().findFirst().orElse(null);
	}

	public void addBug(Bug bug) {
		bugs.add(bug);
	}

	public boolean isPrepared() {
		return isPrepared;
	}

	public void setPrepared(boolean isPrepared) {
		this.isPrepared = isPrepared;
	}

	public Collection<Bug> getBugs() {
		return bugs;
	}

	public long getSearchResultSize() {
		return searchResultSize;
	}

	public void setSearchResultSize() {
		searchResultSize = bugs.stream().filter(bug -> bug.fitsQuery(currentQuery)).count();
	}

	@Override
	public String toString() {
		return page.toString();
	}

	public void toggleAdvanced() {
		if (page == Page.FileABug) {
			page = Page.FileABugAdvanced;
		} else if (page == Page.FileABugAdvanced) {
			page = Page.FileABug;
		} else {
			throw new IllegalStateException("The page must be FileABug or FileABugAdvanced!");
		}
	}

	public Bug getCurrentlyOpenBug() {
		return currentlyOpenBug;
	}

	public Bug getLastOpenBug(){
		return lastOpenBug;
	}

	public void setCurrentlyOpenBug(Bug bug) {
		lastOpenBug = currentlyOpenBug;
		currentlyOpenBug = bug;
	}

	public Bug getBugForCurrentQuery(){
		return bugs.stream().filter(myBug -> myBug.fitsQuery(currentQuery)).findFirst().get();
	}

//	public Bug setCurrentlyOpenBug() {
//		currentlyOpenBug = bugs.stream().filter(myBug -> myBug.fitsQuery(currentQuery)).findFirst().get();
//		return currentlyOpenBug;
//	}

	public Query getCurrentQuery() {
		return currentQuery;
	}

	public void setCurrentQuery(Query currentQuery) {
		this.currentQuery = currentQuery;
	}

	public void setSearchResultSize(long searchResultSize) {
		this.searchResultSize = searchResultSize;

	}

	public Bug getLastBug() {
		currentlyOpenBug = ((ArrayList<Bug>) bugs).get(bugs.size() - 1);
		return currentlyOpenBug;
	}

	public Bug getRandomBug() {
		Random rand = new Random();
		return bugs.stream().skip(rand.nextInt(bugs.size())).findFirst().get();
	}

	public Bug getRandomBug(Bug... excluding) {
		Set<Bug> exlBugs = new HashSet<>();
		Collections.addAll(exlBugs, excluding);
		if(exlBugs.containsAll(bugs)){ //check if no bugs are left
			return null;
		}
		Bug randomBug = getRandomBug();
		while(exlBugs.contains(randomBug)){
			randomBug = getRandomBug();
		}
		return randomBug;
	}

	public Product getNewRandomProduct(){
		Product randProduct = new Product("Product");
		int i = 1;
		List<Product> products = getProducts();
		while(products.contains(randProduct)){
			randProduct = new Product("Product" + i);
			i++;
		}
		return randProduct;
	}

	public Product getProduct(String productName) {
		for (Product product : this.getProducts()) {
			if(product.getName().equals(productName)){
				return product;
			}
		}
		return null;
	}

	public Product getRandomProduct(){
		Random rand = new Random();
		List<Product> products = getProducts();
		return products.get(rand.nextInt(products.size()));
	}

	public Product getRandomProduct(String... excluding) {
		List<Product> excludedProducts = new ArrayList<>();
		for (String s : excluding) {
			Product p = getProduct(s);
			if(p != null){
				excludedProducts.add(p);
			}
		}
		return getRandomProduct(excludedProducts);
	}

	public Product getRandomProduct(Product... excluding) {
		List<Product> excludedProducts = new ArrayList<>();
		for (Product p : excluding) {
			if(p != null){
				excludedProducts.add(p);
			}
		}
		return getRandomProduct(excludedProducts);
	}

	public Product getRandomProduct(Collection<Product> excluding) {
		Random rand = new Random();
		List<Product> products = new LinkedList<>(getProducts());
		products.removeAll(excluding);
		return products.get(rand.nextInt(products.size()));
	}

	@CoverageValue
	public String state(TestCaseStep step) {
		return page.toString();
	}
}
