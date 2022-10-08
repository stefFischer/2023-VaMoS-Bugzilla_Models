package at.scch.mbteclo.factories;

import at.scch.mbteclo.state.Bug;
import at.scch.mbteclo.state.Query;

/**
 * Creates queries that can be given to a page object, which fills the necessary
 * fields. This factory exists in order to have one single point at which the
 * data can be changed and therefore have consistent test data. The search
 * functionality relies on each session having a unique id, in order to have
 * search results consistent with search results of the SUT.
 */
public class QueryFactory {

	/**
	 * Creates a simple query. The following fields are set: Simple Status, Product
	 * and the query.
	 */
	public Query createSimpleQuery(Bug bug) {
		return new Query(bug.getSimpleStatus(), bug.getProduct(), bug.getSummary() + "*");
	}

	/**
	 * Creates an advanced query. The following fields are set: Status, Product,
	 * Query and Resolution
	 */
	public Query createAdvancedQuery(Bug bug) {
		return new Query(bug.getStatus(), bug.getProduct(), bug.getComponent(), bug.getVersion(), bug.getSummary(), bug.getResolution().isEmpty() ? "---" : bug.getResolution());
	}

	/**
	 * Creates a simple query for the quick-search functionality of Bugzilla.
	 * Applied to a bug it tells you, if the quicksearch should find that bug, or
	 * not.
	 * 
	 * @param id The id the searched bug has.
	 */
	public Query createQuickSearch(int id) {
		Query query = new Query();
		query.setQuery(String.valueOf(id));
		return query;
	}

	/**
	 * Creates a query that applied to the a bug, tells you if the bug should be
	 * found, when browsing, or not.
	 */
	public Query createBrowseSearch() {
		Query query = new Query();
		query.setSimpleStatus("Open");
		return query;
	}
}
