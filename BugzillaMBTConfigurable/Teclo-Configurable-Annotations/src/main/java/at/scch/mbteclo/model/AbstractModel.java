package at.scch.mbteclo.model;

import at.scch.mbteclo.adapter.Adapter;
import at.scch.mbteclo.factories.BugFactory;
import at.scch.mbteclo.factories.QueryFactory;
import at.scch.mbteclo.state.BugzillaState;

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