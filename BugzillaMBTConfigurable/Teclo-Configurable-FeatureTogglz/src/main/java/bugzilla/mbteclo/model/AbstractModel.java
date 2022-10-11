package bugzilla.mbteclo.model;

import bugzilla.mbteclo.adapter.Adapter;
import bugzilla.mbteclo.factories.BugFactory;
import bugzilla.mbteclo.factories.QueryFactory;
import bugzilla.mbteclo.state.BugzillaState;

public abstract class AbstractModel {
	protected BugFactory bugFactory = new BugFactory();
	protected QueryFactory queryFactory = new QueryFactory();
	protected Adapter adapter;
	protected BugzillaState state;

	public AbstractModel(Adapter adapter, BugzillaState state) {
		this.adapter = adapter;
		this.state = state;
	}
}