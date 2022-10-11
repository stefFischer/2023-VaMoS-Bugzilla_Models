
package bugzilla.teclo.configuration;

import bugzilla.mbteclo.adapter.Adapter;

public class NoResolveOnOpenBlockers extends BugzillaConfig{

    public NoResolveOnOpenBlockers() {
        super("NoResolveOnOpenBlockers");
    }

    @Override
    public void configure(Adapter adapter) {
        adapter.gotoConfigBugChangePoliciesPage();
        adapter.configNoResolveOnOpenBlockers();
    }

    @Override
    public void reset(Adapter adapter) {
        adapter.gotoConfigBugChangePoliciesPage();
        adapter.resetNoResolveOnOpenBlockers();
    }
}