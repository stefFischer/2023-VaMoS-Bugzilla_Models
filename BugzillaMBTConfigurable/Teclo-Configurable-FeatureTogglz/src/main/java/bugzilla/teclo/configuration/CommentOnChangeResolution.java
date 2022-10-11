
package bugzilla.teclo.configuration;

import bugzilla.mbteclo.adapter.Adapter;

public class CommentOnChangeResolution extends BugzillaConfig{

    public CommentOnChangeResolution() {
        super("CommentOnChangeResolution");
    }

    @Override
    public void configure(Adapter adapter) {
        adapter.gotoConfigBugChangePoliciesPage();
        adapter.configCommentOnChangeResolution();
    }

    @Override
    public void reset(Adapter adapter) {
        adapter.gotoConfigBugChangePoliciesPage();
        adapter.resetCommentOnChangeResolution();
    }
}