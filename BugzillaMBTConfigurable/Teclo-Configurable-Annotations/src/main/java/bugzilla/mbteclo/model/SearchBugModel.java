package bugzilla.mbteclo.model;

import bugzilla.mbteclo.adapter.Adapter;
import bugzilla.mbteclo.state.Bug;
import bugzilla.mbteclo.state.BugzillaState;
import bugzilla.mbteclo.state.Query;
import osmo.tester.annotation.Guard;
import osmo.tester.annotation.TestStep;

public class SearchBugModel extends AbstractModel {

	public SearchBugModel(Adapter adapter, BugzillaState state) {
		super(adapter, state);
	}

	@TestStep("gotoSearch")
	public void gotoSearch() {
		adapter.gotoSearch();
		state.setPage(BugzillaState.Page.SearchPage);
		state.setScenario(BugzillaState.Scenario.Search);
	}

	@Guard("gotoSearch")
	public boolean gotoSearchGuard() {
		return state.getPage() != BugzillaState.Page.SearchPage && !state.getBugs().isEmpty() && state.getScenario() == null && adapter.isLoggedIn();
	}

	@TestStep("gotoAdvancedSearch")
	public void gotoAdvancedSearch() {
		adapter.gotoAdvancedSearch();
		state.setPage(BugzillaState.Page.AdvancedSearch);
	}

	@Guard("gotoAdvancedSearch")
	public boolean gotoAdvancedSearchGuard() {
		return state.getPage() == BugzillaState.Page.SearchPage && !state.getBugs().isEmpty();
	}

	@TestStep("gotoSimpleSearch")
	public void gotoSimpleSearch() {
		adapter.gotoSimpleSearch();
		state.setPage(BugzillaState.Page.SimpleSearch);
	}

	@Guard("gotoSimpleSearch")
	public boolean gotoSimpleSearchGuard() {
		return state.getPage() == BugzillaState.Page.SearchPage && !state.getBugs().isEmpty();
	}

	@TestStep("searchAdvanced")
	public void searchAdvanced() {
		Bug bug = state.getRandomBug();
		Query query = queryFactory.createAdvancedQuery(bug);

		System.out.println("searchAdvanced: " + query);

		adapter.searchAdvanced(query, state.getBugs(), bug);
		state.setCurrentQuery(query);
		state.setSearchResultSize();
		state.setPage(BugzillaState.Page.SearchResult);
		state.setScenario(null);
	}

	@Guard("searchAdvanced")
	public boolean searchAdvancedGuard() {
		return state.getPage() == BugzillaState.Page.AdvancedSearch && !state.getBugs().isEmpty();
	}

	@TestStep("searchSimple")
	public void searchSimple() {
		Bug bug = state.getRandomBug();
		Query query = queryFactory.createSimpleQuery(bug);

		System.out.println("searchSimple: " + query);

		adapter.searchSimple(query, state.getBugs(), bug);
		state.setCurrentQuery(query);
		state.setSearchResultSize();
		state.setPage(BugzillaState.Page.SearchResult);
		state.setScenario(null);
	}

	@Guard("searchSimple")
	public boolean searchSimpleGuard() {
		return state.getPage() == BugzillaState.Page.SimpleSearch && !state.getBugs().isEmpty();
	}

	@TestStep("quickSearch")
	public void quickSearch() {
		Bug open = state.getLastBug();
		Query query = queryFactory.createQuickSearch(open.getId());

		System.out.println("quickSearch: " + open);

		System.out.println(query);
		System.out.println(open.getId());
		adapter.quickSearch(query, open);
		state.setCurrentQuery(query);
		state.setPage(BugzillaState.Page.EditBug);
		state.setCurrentlyOpenBug(open);
		state.setScenario(null);
	}

	@Guard("quickSearch")
	public boolean quickSearchGuard() {
		return state.getPage() == BugzillaState.Page.Start && !state.getBugs().isEmpty() && adapter.isLoggedIn();
	}

	@TestStep("openBug")
	public void openBug() {
		Bug bug = state.getBugForCurrentQuery();
		adapter.openBug(bug);
		state.setPage(BugzillaState.Page.EditBug);
		state.setCurrentlyOpenBug(bug);
		state.setScenario(null);

		System.out.println("openBug: " + bug);
	}

	@Guard("openBug")
	public boolean openBugGuard() {
		return state.getPage() == BugzillaState.Page.SearchResult && state.getSearchResultSize() > 0;
	}
}
