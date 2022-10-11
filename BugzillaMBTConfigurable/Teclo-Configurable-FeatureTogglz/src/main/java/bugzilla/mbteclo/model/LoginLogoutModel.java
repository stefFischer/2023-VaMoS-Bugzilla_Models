package bugzilla.mbteclo.model;

import bugzilla.mbteclo.adapter.Adapter;
import bugzilla.mbteclo.state.BugzillaState;
import osmo.tester.annotation.Guard;
import osmo.tester.annotation.TestStep;

public class LoginLogoutModel extends AbstractModel {

	public LoginLogoutModel(Adapter adapter, BugzillaState state) {
		super(adapter, state);
	}

	@TestStep("gotoLogin")
	public void gotoLogin() {
		adapter.gotoLogin();
		state.setPage(BugzillaState.Page.Login);
		state.setScenario(BugzillaState.Scenario.Login);
	}

	@Guard("gotoLogin")
	public boolean gotoLoginGuard() {
		return state.getPage() != BugzillaState.Page.Login && state.getPage() != BugzillaState.Page.LoginError && !adapter.isLoggedIn();
	}

	@TestStep("login")
	public void login() {
		adapter.login();
		state.setPage(BugzillaState.Page.Start);
		state.setScenario(null);
	}

	@Guard("login")
	public boolean loginGuard() {
		return state.getPage() == BugzillaState.Page.Login && !adapter.isLoggedIn();
	}

	@TestStep("invalidLogin")
	public void invalidLogin() {
		adapter.loginError("wrongUsername", "wrongPassword");
		state.setPage(BugzillaState.Page.LoginError);
		state.setScenario(null);
	}

	@Guard("invalidLogin")
	public boolean invalidLoginGuard() {
		return state.getPage() == BugzillaState.Page.Login && !adapter.isLoggedIn();
	}

	@TestStep("logout")
	public void logout() {
		adapter.logout();
		state.setPage(BugzillaState.Page.Start);
	}

	@Guard("logout")
	public boolean logoutGuard() {
		return adapter.isLoggedIn() && state.getScenario() == null;
	}
}
