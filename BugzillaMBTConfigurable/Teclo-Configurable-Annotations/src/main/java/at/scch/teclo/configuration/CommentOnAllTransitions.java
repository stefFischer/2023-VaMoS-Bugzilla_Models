/*#if ($CommentOnAllTransitions)*/

package at.scch.teclo.configuration;

import at.scch.mbteclo.adapter.Adapter;

public class CommentOnAllTransitions extends BugzillaConfig {

    public CommentOnAllTransitions() {
        super("CommentOnAllTransitions");
    }

    @Override
    public void configure(Adapter adapter) {
        adapter.gotoConfigRequiredCommentsPage();
        adapter.configCommentonAlltransitions();
    }

    @Override
    public void reset(Adapter adapter) {
        adapter.gotoConfigRequiredCommentsPage();
        adapter.resetCommentonAlltransitions();
    }
}
/*#end*/