package at.scch.mbteclo.model;

import at.scch.mbteclo.adapter.Adapter;
import at.scch.mbteclo.state.Bug;
import at.scch.mbteclo.state.BugzillaState;
import at.scch.mbteclo.state.BugzillaState.Page;
import at.scch.mbteclo.state.Query;
import osmo.tester.annotation.Guard;
import osmo.tester.annotation.TestStep;

public class SearchBugModel extends AbstractModel {

	public SearchBugModel(Adapter adapter, BugzillaState state) {
		super(adapter, state);
	}

	@TestStep("gotoSearch")
	public void gotoSearch() {
		adapter.gotoSearch();
		state.setPage(Page.SearchPage);
		state.setScenario(BugzillaState.Scenario.Search);
	}

	@Guard("gotoSearch")
	public boolean gotoSearchGuard() {
		return state.getPage() != Page.SearchPage && !state.getBugs().isEmpty() && state.getScenario() == null && adapter.isLoggedIn();
	}

	@TestStep("gotoAdvancedSearch")
	public void gotoAdvancedSearch() {
		adapter.gotoAdvancedSearch();
		state.setPage(Page.AdvancedSearch);
	}

	@Guard("gotoAdvancedSearch")
	public boolean gotoAdvancedSearchGuard() {
		return state.getPage() == Page.SearchPage && !state.getBugs().isEmpty();
	}

	@TestStep("gotoSimpleSearch")
	public void gotoSimpleSearch() {
		adapter.gotoSimpleSearch();
		state.setPage(Page.SimpleSearch);
	}

	@Guard("gotoSimpleSearch")
	public boolean gotoSimpleSearchGuard() {
		return state.getPage() == Page.SearchPage && !state.getBugs().isEmpty();
	}

	@TestStep("searchAdvanced")
	public void searchAdvanced() {
		Bug bug = state.getRandomBug();
		Query query = queryFactory.createAdvancedQuery(bug);

		System.out.println("searchAdvanced: " + query);

		adapter.searchAdvanced(query, state.getBugs(), bug);
		state.setCurrentQuery(query);
		state.setSearchResultSize();
		state.setPage(Page.SearchResult);
		state.setScenario(null);
	}

	@Guard("searchAdvanced")
	public boolean searchAdvancedGuard() {
		return state.getPage() == Page.AdvancedSearch && !state.getBugs().isEmpty();
	}

	@TestStep("searchSimple")
	public void searchSimple() {
		Bug bug = state.getRandomBug();
		Query query = queryFactory.createSimpleQuery(bug);

		System.out.println("searchSimple: " + query);

		adapter.searchSimple(query, state.getBugs(), bug);
		state.setCurrentQuery(query);
		state.setSearchResultSize();
		state.setPage(Page.SearchResult);
		state.setScenario(null);
	}

	@Guard("searchSimple")
	public boolean searchSimpleGuard() {
		return state.getPage() == Page.SimpleSearch && !state.getBugs().isEmpty();
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
		state.setPage(Page.EditBug);
		state.setCurrentlyOpenBug(open);
		state.setScenario(null);
	}

	@Guard("quickSearch")
	public boolean quickSearchGuard() {
		return state.getPage() == Page.Start && !state.getBugs().isEmpty() && adapter.isLoggedIn();
	}

	@TestStep("openBug")
	public void openBug() {
		Bug bug = state.getBugForCurrentQuery();
		adapter.openBug(bug);
		state.setPage(Page.EditBug);
		state.setCurrentlyOpenBug(bug);
		state.setScenario(null);

		System.out.println("openBug: " + bug);
	}

	@Guard("openBug")
	public boolean openBugGuard() {
		return state.getPage() == Page.SearchResult && state.getSearchResultSize() > 0;
	}
}
