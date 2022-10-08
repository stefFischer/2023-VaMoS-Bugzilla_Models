
package at.scch.teclo.configuration;

import at.scch.mbteclo.adapter.Adapter;

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