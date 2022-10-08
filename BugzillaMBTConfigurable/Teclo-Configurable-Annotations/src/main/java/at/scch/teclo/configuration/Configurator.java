package at.scch.teclo.configuration;

import at.scch.mbteclo.adapter.Adapter;
import at.scch.teclo.pageobjects.ConfigGeneralSettingsPage;

import java.util.List;

public class Configurator {

    private List<BugzillaConfig> configs;

    private Adapter adapter;

    public Configurator(List<BugzillaConfig> configs, Adapter adapter) {
        this.configs = configs;
        this.adapter = adapter;
    }

    public void configure() {
        if(!adapter.isLoggedIn()) {
            adapter.gotoLogin();
            adapter.login();
        }
        ConfigGeneralSettingsPage configGeneralSettingsPage = this.adapter.gotoConfigGeneralSettingsPage();
        configGeneralSettingsPage.setAnnounceHtml("<div id=\"test_config\" class=bz_private>" + getTestConfigName() + "</div>");
        for (BugzillaConfig config : configs) {
            config.configure(this.adapter);
        }
        adapter.logout();
        adapter.gotoStart();
    }

    public void reset() {
        adapter.gotoStart();
        if(!adapter.isLoggedIn()) {
            adapter.gotoLogin();
            adapter.login();
        }
        for (BugzillaConfig config : configs) {
            config.reset(this.adapter);
        }
        ConfigGeneralSettingsPage configGeneral = this.adapter.gotoConfigGeneralSettingsPage();
        configGeneral.setAnnounceHtml("");
        adapter.logout();
        adapter.gotoStart();
    }

    private String getTestConfigName(){
        StringBuilder configName = new StringBuilder();
        boolean first = true;
        for (BugzillaConfig config : configs) {
            if(!first){
                configName.append(" AND ");
            }
            configName.append(config.getTestConfigName());
            first = false;
        }
        return configName.toString();
    }
}
