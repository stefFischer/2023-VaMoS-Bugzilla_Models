
package bugzilla.teclo.configuration;

import bugzilla.mbteclo.adapter.Adapter;

public class SimpleBugWorkflow extends BugzillaConfig {

    public SimpleBugWorkflow() {
        super("SimpleBugWorkflow");
    }

    @Override
    public void configure(Adapter adapter) {
        adapter.gotoConfigWorkflowPage();
        adapter.configSimplebugworkflow();
    }

    @Override
    public void reset(Adapter adapter) {
        adapter.gotoConfigWorkflowPage();
        adapter.resetSimplebugworkflow();
    }
}