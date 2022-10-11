package bugzilla.teclo.configuration;

import bugzilla.mbteclo.adapter.Adapter;
import bugzilla.teclo.BugzillaSetup;

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
        /*#if ($UseStatusWhiteboard)*/
        configs.add(new StatusWhiteboard()); //C01
        /*#end*/
        /*#if ($Letsubmitterchoosepriority)*/
        configs.add(new LetSubmitterChoosePriority()); //C02
        /*#end*/
        /*#if ($AddProduct)*/
        configs.add(new AddProduct()); //C04
        /*#end*/
        /*#if ($AddComponent)*/
        configs.add(new AddComponent()); //C05
        /*#end*/
        /*#if ($AddVersion)*/
        configs.add(new AddVersion()); //C06
        /*#end*/
        /*#if ($SimpleBugWorkflow)*/
        configs.add(new SimpleBugWorkflow()); //C07
        /*#end*/
        /*#if ($UnconfirmedState)*/
        configs.add(new UnconfirmedState()); //C08
        /*#end*/
        /*#if ($CommentOnBugCreation)*/
        configs.add(new CommentOnBugCreation()); //C09
        /*#end*/
        /*#if ($CommentOnAllTransitions)*/
        configs.add(new CommentOnAllTransitions()); //C10
        /*#end*/
        /*#if ($CommentOnChangeResolution)*/
        configs.add(new CommentOnChangeResolution()); //C11
        /*#end*/
        /*#if ($CommentOnDuplicate)*/
        configs.add(new CommentOnDuplicate()); //C12
        /*#end*/
        /*#if ($NoResolveOnOpenBlockers)*/
        configs.add(new NoResolveOnOpenBlockers()); //C13
        /*#end*/
        /*#if ($DuplicateOrMoveBugStatusVerified)*/
        configs.add(new DuplicateOrMoveBugStatusVerified()); //C14
        /*#end*/

        configurator = new Configurator(configs, adapter);
    }

    private void setup(){
        configurator.configure();
        BugzillaSetup.close();
    }

    private void reset(){
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
