/*#if ($Letsubmitterchoosepriority)*/

package bugzilla.teclo.configuration;

import bugzilla.mbteclo.adapter.Adapter;

public class LetSubmitterChoosePriority extends BugzillaConfig{

    public LetSubmitterChoosePriority() {
        super("LetSubmitterChoosePriority");
    }

    @Override
    public void configure(Adapter adapter) {
        adapter.gotoConfigBugChangePoliciesPage();
        adapter.resetLetSubmitterChoosePriority();
    }

    @Override
    public void reset(Adapter adapter) {
        adapter.gotoConfigBugChangePoliciesPage();
        adapter.configLetSubmitterChoosePriority();
    }
}
/*#end*/
