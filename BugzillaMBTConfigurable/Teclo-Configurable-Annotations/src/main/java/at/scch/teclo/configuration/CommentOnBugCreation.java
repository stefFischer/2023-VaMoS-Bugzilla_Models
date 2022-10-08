/*#if ($CommentOnBugCreation)*/

package at.scch.teclo.configuration;

import at.scch.mbteclo.adapter.Adapter;

public class CommentOnBugCreation extends BugzillaConfig {

    public CommentOnBugCreation() {
        super("CommentOnBugCreation");
    }

    @Override
    public void configure(Adapter adapter) {
        adapter.gotoConfigRequiredCommentsPage();
        adapter.configCommentonBugcreation();
    }

    @Override
    public void reset(Adapter adapter) {
        adapter.gotoConfigRequiredCommentsPage();
        adapter.resetCommentonBugcreation();
    }
}
/*#end*/