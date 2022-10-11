/*#if ($CommentOnDuplicate)*/

package bugzilla.teclo.configuration;

import bugzilla.mbteclo.adapter.Adapter;

public class CommentOnDuplicate extends BugzillaConfig{

    public CommentOnDuplicate() {
        super("CommentOnDuplicate");
    }

    @Override
    public void configure(Adapter adapter) {
        adapter.gotoConfigBugChangePoliciesPage();
        adapter.configCommentOnDuplicate();
    }

    @Override
    public void reset(Adapter adapter) {
        adapter.gotoConfigBugChangePoliciesPage();
        adapter.resetCommentOnDuplicate();
    }
}
/*#end*/