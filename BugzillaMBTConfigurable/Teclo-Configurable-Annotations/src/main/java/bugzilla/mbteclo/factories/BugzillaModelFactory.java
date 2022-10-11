package bugzilla.mbteclo.factories;

import bugzilla.mbteclo.adapter.Adapter;
import bugzilla.mbteclo.model.*;
import bugzilla.mbteclo.state.BugzillaState;
import osmo.tester.model.ModelFactory;
import osmo.tester.model.TestModels;

/**
 * This is a convenience factory, that creates the general BugzillaModel. With
 * each new instance also a new BugzillaState is constructed.
 */
public class BugzillaModelFactory implements ModelFactory {
	private BugzillaState state;
	private Adapter adapter;

	public BugzillaModelFactory(Adapter adapter) {
		this.adapter = adapter;
		this.state = new BugzillaState();
	}

	@Override
	public void createModelObjects(TestModels addThemHere) {
		addThemHere.add(state);
		addThemHere.add(new BaseModel(adapter, state));
//		addThemHere.add(new BrowseBugsModel(adapter, state));
		addThemHere.add(new BugLifecycleModel(adapter, state));
		addThemHere.add(new CreateBugModel(adapter, state));
		addThemHere.add(new EditBugModel(adapter, state));
		addThemHere.add(new LoginLogoutModel(adapter, state));
		addThemHere.add(new SearchBugModel(adapter, state));
	}

}
