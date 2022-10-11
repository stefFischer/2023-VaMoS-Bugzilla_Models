/*#if ($UnconfirmedState)*/

package bugzilla.teclo.configuration;

import bugzilla.mbteclo.adapter.Adapter;
import bugzilla.mbteclo.state.BugzillaState;

public class UnconfirmedState extends BugzillaConfig {

    public UnconfirmedState() {
        super("UnconfirmedState");
    }

    @Override
    public void configure(Adapter adapter) {
        adapter.gotoConfigWorkflowPage();
        adapter.configUnconfirmedstatebugworkflow(BugzillaState.DEFAULT_PRODUCT);
    }

    @Override
    public void reset(Adapter adapter) {
        adapter.gotoConfigWorkflowPage();
        adapter.resetUnconfirmedstatebugworkflow(BugzillaState.DEFAULT_PRODUCT);
    }
}
/*#end*/