package at.scch.mbteclo.state;

import org.togglz.core.Feature;
import org.togglz.core.manager.TogglzConfig;
import org.togglz.core.repository.StateRepository;
import org.togglz.core.repository.file.FileBasedStateRepository;
import org.togglz.core.user.UserProvider;

import java.io.File;

public class Configuration implements TogglzConfig {

    @Override
    public Class<? extends Feature> getFeatureClass() {
        return ConfigurationOption.class;
    }

    @Override
    public StateRepository getStateRepository() {
        File config = new File("Teclo-Configurable-FeatureTogglz/src/config.properties").getAbsoluteFile();
        if(!config.exists()){
            config = new File("src/config.properties").getAbsoluteFile();
        }
        System.out.println(config.getAbsolutePath());
        System.out.println(config.exists());
        return new FileBasedStateRepository(config);
    }

    @Override
    public UserProvider getUserProvider() {
        return null;
    }
}
