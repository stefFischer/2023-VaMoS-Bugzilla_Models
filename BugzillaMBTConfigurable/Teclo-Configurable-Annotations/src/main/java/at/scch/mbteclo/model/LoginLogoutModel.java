package at.scch.mbteclo.model;

import at.scch.mbteclo.adapter.Adapter;
import at.scch.mbteclo.state.BugzillaState;
import at.scch.mbteclo.state.BugzillaState.Page;
import osmo.tester.annotation.Guard;
import osmo.tester.annotation.TestStep;

public class LoginLogoutModel extends AbstractModel {

	public LoginLogoutModel(Adapter adapter, BugzillaState state) {
		super(adapter, state);
	}

	@TestStep("gotoLogin")
	public void gotoLogin() {
		adapter.gotoLogin();
		state.setPage(Page.Login);
		state.setScenario(BugzillaState.Scenario.Login);
	}

	@Guard("gotoLogin")
	public boolean gotoLoginGuard() {
		return state.getPage() != Page.Login && state.getPage() != Page.LoginError && !adapter.isLoggedIn();
	}

	@TestStep("login")
	public void login() {
		adapter.login();
		state.setPage(Page.Start);
		state.setScenario(null);
	}

	@Guard("login")
	public boolean loginGuard() {
		return state.getPage() == Page.Login && !adapter.isLoggedIn();
	}

	@TestStep("invalidLogin")
	public void invalidLogin() {
		adapter.loginError("wrongUsername", "wrongPassword");
		state.setPage(Page.LoginError);
		state.setScenario(null);
	}

	@Guard("invalidLogin")
	public boolean invalidLoginGuard() {
		return state.getPage() == Page.Login && !adapter.isLoggedIn();
	}

	@TestStep("logout")
	public void logout() {
		adapter.logout();
		state.setPage(Page.Start);
	}

	@Guard("logout")
	public boolean logoutGuard() {
		return adapter.isLoggedIn() && state.getScenario() == null;
	}
}
