
package at.scch.teclo.configuration;

import at.scch.mbteclo.adapter.Adapter;

public class StatusWhiteboard extends BugzillaConfig{

    public StatusWhiteboard() {
        super("Usestatuswhiteboard");
    }

    @Override
    public void configure(Adapter adapter) {
        adapter.gotoConfigBugFieldsPage();
        adapter.configStatusWhiteboard();
    }

    @Override
    public void reset(Adapter adapter) {
        adapter.gotoConfigBugFieldsPage();
        adapter.resetStatusWhiteboard();
    }
}
