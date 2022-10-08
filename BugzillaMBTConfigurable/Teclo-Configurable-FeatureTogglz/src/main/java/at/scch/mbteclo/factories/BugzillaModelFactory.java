package at.scch.mbteclo.factories;

import at.scch.mbteclo.adapter.Adapter;
import at.scch.mbteclo.model.*;
import at.scch.mbteclo.model.configuration.ConfigurationModel;
import at.scch.mbteclo.state.BugzillaState;
import osmo.tester.model.ModelFactory;
import osmo.tester.model.TestModels;

import java.util.LinkedList;
import java.util.List;

/**
 * This is a convenience factory, that creates the general BugzillaModel. With
 * each new instance also a new BugzillaState is constructed.
 */
public class BugzillaModelFactory implements ModelFactory {
	private BugzillaState state;
	private Adapter adapter;

	private final List<Object> cachedModel = new LinkedList<>();

	public BugzillaModelFactory(Adapter adapter) {
		this.adapter = adapter;
		this.state = new BugzillaState();
	}

	@Override
	public void createModelObjects(TestModels addThemHere) {
		if(cachedModel.isEmpty()){
			cachedModel.add(state);
			cachedModel.add(new BaseModel(adapter, state));
//			cachedModel.add(new BrowseBugsModel(adapter, state));
			cachedModel.add(new BugLifecycleModel(adapter, state));
			cachedModel.add(new CreateBugModel(adapter, state));
			cachedModel.add(new EditBugModel(adapter, state));
			cachedModel.add(new LoginLogoutModel(adapter, state));
			cachedModel.add(new SearchBugModel(adapter, state));

//			cachedModel.add(new ConfigurationModel(adapter, state));
		}

		for (Object model : cachedModel) {
			addThemHere.add(model);
		}
	}
}
