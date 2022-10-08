package at.scch.teclo.configuration;

import at.scch.mbteclo.adapter.Adapter;

public abstract class BugzillaConfig {

	private String testConfigName;

	public BugzillaConfig(String configName) {
		this.testConfigName = configName;
	}

	public abstract void configure(Adapter adapter);

	public abstract void reset(Adapter adapter);

	public String getTestConfigName() {
		return testConfigName;
	}

}
