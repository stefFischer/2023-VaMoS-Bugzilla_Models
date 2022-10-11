package bugzilla.mbteclo.model;

import bugzilla.mbteclo.adapter.Adapter;
import bugzilla.mbteclo.state.BugzillaState;
import osmo.tester.annotation.Guard;
import osmo.tester.annotation.TestStep;

public class BaseModel extends AbstractModel{

    public BaseModel(Adapter adapter, BugzillaState state) {
        super(adapter, state);
    }

    @TestStep("gotoStart")
    public void gotoStart() {
        adapter.gotoStart();
        state.setPage(BugzillaState.Page.Start);
    }

    @Guard("gotoStart")
    public boolean gotoStartGuard() {
        return state.getPage() != BugzillaState.Page.Start && state.getScenario() == null;
    }
}
