/*#if ($DuplicateOrMoveBugStatusVerified)*/

package at.scch.teclo.configuration;

import at.scch.mbteclo.adapter.Adapter;

public class DuplicateOrMoveBugStatusVerified extends BugzillaConfig{

    public DuplicateOrMoveBugStatusVerified() {
        super("DuplicateOrMoveBugStatusVerified");
    }

    @Override
    public void configure(Adapter adapter) {
        adapter.gotoConfigBugChangePoliciesPage();
        adapter.configDuplicateOrMoveBugStatusVerified();
    }

    @Override
    public void reset(Adapter adapter) {
        adapter.gotoConfigBugChangePoliciesPage();
        adapter.resetDuplicateOrMoveBugStatusVerified();
    }
}
/*#end*/