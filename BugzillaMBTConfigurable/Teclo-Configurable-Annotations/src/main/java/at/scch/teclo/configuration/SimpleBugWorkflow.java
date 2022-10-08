/*#if ($SimpleBugWorkflow)*/

package at.scch.teclo.configuration;

import at.scch.mbteclo.adapter.Adapter;

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
/*#end*/