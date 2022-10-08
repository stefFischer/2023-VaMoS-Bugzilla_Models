/*#if ($AddVersion)*/

package at.scch.teclo.configuration;

import at.scch.mbteclo.adapter.Adapter;
import at.scch.mbteclo.state.BugzillaState;

public class AddVersion extends BugzillaConfig{

    private String version;

    public AddVersion() {
        super("AddVersion");
        this.version = "AddedVersion";
    }

    @Override
    public void configure(Adapter adapter) {
        adapter.gotoAddVersionPage(BugzillaState.DEFAULT_PRODUCT);
        adapter.addAVersion(BugzillaState.DEFAULT_PRODUCT, this.version);
    }

    @Override
    public void reset(Adapter adapter) {
        adapter.deleteVersion(BugzillaState.DEFAULT_PRODUCT, this.version);
    }
}
/*#end*/