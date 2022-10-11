
package bugzilla.teclo.configuration;

import bugzilla.mbteclo.adapter.Adapter;
import bugzilla.mbteclo.state.BugzillaState;

public class AddComponent extends BugzillaConfig{

    private String component;

    public AddComponent() {
        super("AddComponent");
        this.component = "AddedComponent";
    }

    @Override
    public void configure(Adapter adapter) {
        adapter.gotoAddComponentPage(BugzillaState.DEFAULT_PRODUCT);
        adapter.addAComponent(BugzillaState.DEFAULT_PRODUCT, this.component);
    }

    @Override
    public void reset(Adapter adapter) {
        adapter.deleteComponent(BugzillaState.DEFAULT_PRODUCT, this.component);
    }
}