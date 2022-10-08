package at.scch.teclo.configuration;

import at.scch.mbteclo.adapter.Adapter;
import at.scch.mbteclo.state.ConfigurationOption;
import at.scch.teclo.BugzillaSetup;

import java.util.LinkedList;
import java.util.List;

public class Setup {

    private Adapter adapter;

    private Configurator configurator;

    //TODO make class that creates default configuration, no matter what mix of configs was done, for hard reset automation

    public Setup() {
        BugzillaSetup.initialize();
        /* in order to not test the SUT (only run the model) run with AdapterEmpty */
        adapter = new Adapter(BugzillaSetup.gotoStartPage(), BugzillaSetup.getPassword(), BugzillaSetup.getUsername());

        List<BugzillaConfig> configs = new LinkedList<>();
        if(ConfigurationOption.UseStatusWhiteboard.isActive()){
            configs.add(new StatusWhiteboard()); //C01
        }
        if(ConfigurationOption.Letsubmitterchoosepriority.isActive()){
            configs.add(new LetSubmitterChoosePriority()); //C02
        }
        if(ConfigurationOption.AddProduct.isActive()){
            configs.add(new AddProduct()); //C04
        }
        if(ConfigurationOption.AddComponent.isActive()){
            configs.add(new AddComponent()); //C05
        }
        if(ConfigurationOption.AddVersion.isActive()){
            configs.add(new AddVersion()); //C06
        }
        if(ConfigurationOption.SimpleBugWorkflow.isActive()){
            configs.add(new SimpleBugWorkflow()); //C07
        }
        if(ConfigurationOption.UnconfirmedState.isActive()){
            configs.add(new UnconfirmedState()); //C08
        }
        if(ConfigurationOption.CommentOnBugCreation.isActive()){
            configs.add(new CommentOnBugCreation()); //C09
        }
        if(ConfigurationOption.CommentOnAllTransitions.isActive()){
            configs.add(new CommentOnAllTransitions()); //C10
        }
        if(ConfigurationOption.CommentOnChangeResolution.isActive()){
            configs.add(new CommentOnChangeResolution()); //C11
        }
        if(ConfigurationOption.CommentOnDuplicate.isActive()){
            configs.add(new CommentOnDuplicate()); //C12
        }
        if(ConfigurationOption.NoResolveOnOpenBlockers.isActive()){
            configs.add(new NoResolveOnOpenBlockers()); //C13
        }
        if(ConfigurationOption.DuplicateOrMoveBugStatusVerified.isActive()){
            configs.add(new DuplicateOrMoveBugStatusVerified()); //C14
        }

        configurator = new Configurator(configs, adapter);
    }

    public void setup(){
        configurator.configure();
        BugzillaSetup.close();
    }

    public void reset(){
        configurator.reset();
        BugzillaSetup.close();
    }

    public static void main(String[] args) {
        if (args.length > 0 && "-reset".equals(args[0])) {
            new Setup().reset();
        } else {
            new Setup().setup();
        }
    }
}
