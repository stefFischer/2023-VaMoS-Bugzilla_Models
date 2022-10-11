/*#if ($UseStatusWhiteboard)*/

package bugzilla.teclo.configuration;

import bugzilla.mbteclo.adapter.Adapter;

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
/*#end*/
