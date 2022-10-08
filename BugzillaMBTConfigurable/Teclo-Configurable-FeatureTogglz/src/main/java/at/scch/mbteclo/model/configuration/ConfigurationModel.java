package at.scch.mbteclo.model.configuration;

import at.scch.mbteclo.adapter.Adapter;
import at.scch.mbteclo.model.AbstractModel;
import at.scch.mbteclo.state.BugzillaState;
import at.scch.mbteclo.state.ConfigurationOption;
import at.scch.teclo.configuration.*;
import osmo.tester.annotation.AfterTest;
import osmo.tester.annotation.Guard;
import osmo.tester.annotation.TestStep;

public class ConfigurationModel extends AbstractModel {

    private static final int WEIGHT = 2;

    public ConfigurationModel(Adapter adapter, BugzillaState state) {
        super(adapter, state);
    }

    //--------------------------- C01 -----------------------------
    @TestStep(name = "configStatusWhiteboard", weight = WEIGHT)
    public void configStatusWhiteboard(){
        new StatusWhiteboard().configure(adapter);
        ConfigurationOption.UseStatusWhiteboard.set();
        adapter.gotoStart();
    }

    @Guard("configStatusWhiteboard")
    public boolean configStatusWhiteboardGuard() {
        return state.getScenario() == null && state.getPage() == BugzillaState.Page.Start && adapter.isLoggedIn() && !ConfigurationOption.UseStatusWhiteboard.isActive();
    }

    @TestStep(name = "resetStatusWhiteboard", weight = WEIGHT)
    public void resetStatusWhiteboard(){
        new StatusWhiteboard().reset(adapter);
        ConfigurationOption.UseStatusWhiteboard.reset();
        adapter.gotoStart();
    }

    @Guard("resetStatusWhiteboard")
    public boolean resetStatusWhiteboardGuard() {
        return state.getScenario() == null && state.getPage() == BugzillaState.Page.Start && adapter.isLoggedIn() && ConfigurationOption.UseStatusWhiteboard.isActive();
    }

    //--------------------------- C02 -----------------------------
    @TestStep(name = "configLetSubmitterChoosePriority", weight = WEIGHT)
    public void configLetSubmitterChoosePriority(){
        new LetSubmitterChoosePriority().configure(adapter);
        ConfigurationOption.Letsubmitterchoosepriority.set();
        adapter.gotoStart();
    }

    @Guard("configLetSubmitterChoosePriority")
    public boolean configLetSubmitterChoosePriorityGuard() {
        return state.getScenario() == null && state.getPage() == BugzillaState.Page.Start && adapter.isLoggedIn() && !ConfigurationOption.Letsubmitterchoosepriority.isActive();
    }

    @TestStep(name = "resetLetSubmitterChoosePriority", weight = WEIGHT)
    public void resetLetSubmitterChoosePriority(){
        new LetSubmitterChoosePriority().reset(adapter);
        ConfigurationOption.Letsubmitterchoosepriority.reset();
        adapter.gotoStart();
    }

    @Guard("resetLetSubmitterChoosePriority")
    public boolean resetLetSubmitterChoosePriorityGuard() {
        return state.getScenario() == null && state.getPage() == BugzillaState.Page.Start && adapter.isLoggedIn() && ConfigurationOption.Letsubmitterchoosepriority.isActive();
    }

    //--------------------------- C04 -----------------------------
    @TestStep(name = "addProduct", weight = WEIGHT)
    public void addProduct(){
        new AddProduct().configure(adapter);
        ConfigurationOption.AddProduct.set();
        adapter.gotoStart();
    }

    @Guard("addProduct")
    public boolean addProductGuard() {
        return state.getScenario() == null && state.getPage() == BugzillaState.Page.Start && adapter.isLoggedIn() && !ConfigurationOption.AddProduct.isActive();
    }

    @TestStep(name = "removeProduct", weight = WEIGHT)
    public void removeProduct(){
        new AddProduct().reset(adapter);
        ConfigurationOption.AddProduct.reset();
        adapter.gotoStart();
    }

    @Guard("removeProduct")
    public boolean removeProductGuard() {
        return state.getScenario() == null && state.getPage() == BugzillaState.Page.Start && adapter.isLoggedIn() && ConfigurationOption.AddProduct.isActive();
    }

    //--------------------------- C05 -----------------------------
    @TestStep(name = "addComponent", weight = WEIGHT)
    public void addComponent(){
        new AddComponent().configure(adapter);
        ConfigurationOption.AddComponent.set();
        adapter.gotoStart();
    }

    @Guard("addComponent")
    public boolean addComponentGuard() {
        return state.getScenario() == null && state.getPage() == BugzillaState.Page.Start && adapter.isLoggedIn() && !ConfigurationOption.AddComponent.isActive();
    }

    @TestStep(name = "removeComponent", weight = WEIGHT)
    public void removeComponent(){
        new AddComponent().reset(adapter);
        ConfigurationOption.AddComponent.reset();
        adapter.gotoStart();
    }

    @Guard("removeComponent")
    public boolean removeComponentGuard() {
        return state.getScenario() == null && state.getPage() == BugzillaState.Page.Start && adapter.isLoggedIn() && ConfigurationOption.AddComponent.isActive();
    }

    //--------------------------- C06 -----------------------------
    @TestStep(name = "addVersion", weight = WEIGHT)
    public void addVersion(){
        new AddVersion().configure(adapter);
        ConfigurationOption.AddVersion.set();
        adapter.gotoStart();
    }

    @Guard("addVersion")
    public boolean addVersionGuard() {
        return state.getScenario() == null && state.getPage() == BugzillaState.Page.Start && adapter.isLoggedIn() && !ConfigurationOption.AddVersion.isActive();
    }

    @TestStep(name = "removeVersion", weight = WEIGHT)
    public void removeVersion(){
        new AddVersion().reset(adapter);
        ConfigurationOption.AddVersion.reset();
        adapter.gotoStart();
    }

    @Guard("removeVersion")
    public boolean removeVersionGuard() {
        return state.getScenario() == null && state.getPage() == BugzillaState.Page.Start && adapter.isLoggedIn() && ConfigurationOption.AddVersion.isActive();
    }

    //--------------------------- C07 -----------------------------
    @TestStep(name = "configSimpleBugWorkflow", weight = WEIGHT)
    public void configSimpleBugWorkflow(){
        new SimpleBugWorkflow().configure(adapter);
        ConfigurationOption.SimpleBugWorkflow.set();
        adapter.gotoStart();
    }

    @Guard("configSimpleBugWorkflow")
    public boolean configSimpleBugWorkflowGuard() {
        return state.getScenario() == null && state.getPage() == BugzillaState.Page.Start && adapter.isLoggedIn() && !ConfigurationOption.SimpleBugWorkflow.isActive();
    }

    @TestStep(name = "resetSimpleBugWorkflow", weight = WEIGHT)
    public void resetSimpleBugWorkflow(){
        new SimpleBugWorkflow().reset(adapter);
        ConfigurationOption.SimpleBugWorkflow.reset();
        adapter.gotoStart();
    }

    @Guard("resetSimpleBugWorkflow")
    public boolean resetSimpleBugWorkflowGuard() {
        return state.getScenario() == null && state.getPage() == BugzillaState.Page.Start && adapter.isLoggedIn() && ConfigurationOption.SimpleBugWorkflow.isActive();
    }

    //--------------------------- C08 -----------------------------
    @TestStep(name = "configUnconfirmedState", weight = WEIGHT)
    public void configUnconfirmedState(){
        new UnconfirmedState().configure(adapter);
        ConfigurationOption.UnconfirmedState.set();
        adapter.gotoStart();
    }

    @Guard("configUnconfirmedState")
    public boolean configUnconfirmedStateGuard() {
        return state.getScenario() == null && state.getPage() == BugzillaState.Page.Start && adapter.isLoggedIn() && !ConfigurationOption.UnconfirmedState.isActive();
    }

    @TestStep(name = "resetUnconfirmedState", weight = WEIGHT)
    public void resetUnconfirmedState(){
        new UnconfirmedState().reset(adapter);
        ConfigurationOption.UnconfirmedState.reset();
        adapter.gotoStart();
    }

    @Guard("resetUnconfirmedState")
    public boolean resetUnconfirmedStateGuard() {
        return state.getScenario() == null && state.getPage() == BugzillaState.Page.Start && adapter.isLoggedIn() && ConfigurationOption.UnconfirmedState.isActive();
    }

    //--------------------------- C09 -----------------------------
    @TestStep(name = "configCommentOnBugCreation", weight = WEIGHT)
    public void configCommentOnBugCreation(){
        new CommentOnBugCreation().configure(adapter);
        ConfigurationOption.CommentOnBugCreation.set();
        adapter.gotoStart();
    }

    @Guard("configCommentOnBugCreation")
    public boolean configCommentOnBugCreationGuard() {
        return state.getScenario() == null && state.getPage() == BugzillaState.Page.Start && adapter.isLoggedIn() && !ConfigurationOption.CommentOnBugCreation.isActive();
    }

    @TestStep(name = "resetCommentOnBugCreation", weight = WEIGHT)
    public void resetCommentOnBugCreation(){
        new CommentOnBugCreation().reset(adapter);
        ConfigurationOption.CommentOnBugCreation.reset();
        adapter.gotoStart();
    }

    @Guard("resetCommentOnBugCreation")
    public boolean resetCommentOnBugCreationGuard() {
        return state.getScenario() == null && state.getPage() == BugzillaState.Page.Start && adapter.isLoggedIn() && ConfigurationOption.CommentOnBugCreation.isActive();
    }

    //--------------------------- C10 -----------------------------
    @TestStep(name = "configCommentOnAllTransitions", weight = WEIGHT)
    public void configCommentOnAllTransitions(){
        new CommentOnAllTransitions().configure(adapter);
        ConfigurationOption.CommentOnAllTransitions.set();
        adapter.gotoStart();
    }

    @Guard("configCommentOnAllTransitions")
    public boolean configCommentOnAllTransitionsGuard() {
        return state.getScenario() == null && state.getPage() == BugzillaState.Page.Start && adapter.isLoggedIn() && !ConfigurationOption.CommentOnAllTransitions.isActive();
    }

    @TestStep(name = "resetCommentOnAllTransitions", weight = WEIGHT)
    public void resetCommentOnAllTransitions(){
        new CommentOnAllTransitions().reset(adapter);
        ConfigurationOption.CommentOnAllTransitions.reset();
        adapter.gotoStart();
    }

    @Guard("resetCommentOnAllTransitions")
    public boolean resetCommentOnAllTransitionsGuard() {
        return state.getScenario() == null && state.getPage() == BugzillaState.Page.Start && adapter.isLoggedIn() && ConfigurationOption.CommentOnAllTransitions.isActive();
    }

    //--------------------------- C11 -----------------------------
    @TestStep(name = "configCommentOnChangeResolution", weight = WEIGHT)
    public void configCommentOnChangeResolution(){
        new CommentOnChangeResolution().configure(adapter);
        ConfigurationOption.CommentOnChangeResolution.set();
        adapter.gotoStart();
    }

    @Guard("configCommentOnChangeResolution")
    public boolean configCommentOnChangeResolutionGuard() {
        return state.getScenario() == null && state.getPage() == BugzillaState.Page.Start && adapter.isLoggedIn() && !ConfigurationOption.CommentOnChangeResolution.isActive();
    }

    @TestStep(name = "resetCommentOnChangeResolution", weight = WEIGHT)
    public void resetCommentOnChangeResolution(){
        new CommentOnChangeResolution().reset(adapter);
        ConfigurationOption.CommentOnChangeResolution.reset();
        adapter.gotoStart();
    }

    @Guard("resetCommentOnChangeResolution")
    public boolean resetCommentOnChangeResolutionGuard() {
        return state.getScenario() == null && state.getPage() == BugzillaState.Page.Start && adapter.isLoggedIn() && ConfigurationOption.CommentOnChangeResolution.isActive();
    }

    //--------------------------- C12 -----------------------------
    @TestStep(name = "configCommentOnDuplicate", weight = WEIGHT)
    public void configCommentOnDuplicate(){
        new CommentOnDuplicate().configure(adapter);
        ConfigurationOption.CommentOnDuplicate.set();
        adapter.gotoStart();
    }

    @Guard("configCommentOnDuplicate")
    public boolean configCommentOnDuplicateGuard() {
        return state.getScenario() == null && state.getPage() == BugzillaState.Page.Start && adapter.isLoggedIn() && !ConfigurationOption.CommentOnDuplicate.isActive();
    }

    @TestStep(name = "resetCommentOnDuplicate", weight = WEIGHT)
    public void resetCommentOnDuplicate(){
        new CommentOnDuplicate().reset(adapter);
        ConfigurationOption.CommentOnDuplicate.reset();
        adapter.gotoStart();
    }

    @Guard("resetCommentOnDuplicate")
    public boolean resetCommentOnDuplicateGuard() {
        return state.getScenario() == null && state.getPage() == BugzillaState.Page.Start && adapter.isLoggedIn() && ConfigurationOption.CommentOnDuplicate.isActive();
    }

    //--------------------------- C13 -----------------------------
    @TestStep(name = "configNoResolveOnOpenBlockers", weight = WEIGHT)
    public void configNoResolveOnOpenBlockers(){
        new NoResolveOnOpenBlockers().configure(adapter);
        ConfigurationOption.NoResolveOnOpenBlockers.set();
        adapter.gotoStart();
    }

    @Guard("configNoResolveOnOpenBlockers")
    public boolean configNoResolveOnOpenBlockersGuard() {
        return state.getScenario() == null && state.getPage() == BugzillaState.Page.Start && adapter.isLoggedIn() && !ConfigurationOption.NoResolveOnOpenBlockers.isActive();
    }

    @TestStep(name = "resetNoResolveOnOpenBlockers", weight = WEIGHT)
    public void resetNoResolveOnOpenBlockers(){
        new NoResolveOnOpenBlockers().reset(adapter);
        ConfigurationOption.NoResolveOnOpenBlockers.reset();
        adapter.gotoStart();
    }

    @Guard("resetNoResolveOnOpenBlockers")
    public boolean resetNoResolveOnOpenBlockersGuard() {
        return state.getScenario() == null && state.getPage() == BugzillaState.Page.Start && adapter.isLoggedIn() && ConfigurationOption.NoResolveOnOpenBlockers.isActive();
    }

    //--------------------------- C14 -----------------------------
    @TestStep(name = "configDuplicateOrMoveBugStatusVerified", weight = WEIGHT)
    public void configDuplicateOrMoveBugStatusVerified(){
        new DuplicateOrMoveBugStatusVerified().configure(adapter);
        ConfigurationOption.DuplicateOrMoveBugStatusVerified.set();
        adapter.gotoStart();
    }

    @Guard("configDuplicateOrMoveBugStatusVerified")
    public boolean configDuplicateOrMoveBugStatusVerifiedGuard() {
        return state.getScenario() == null && state.getPage() == BugzillaState.Page.Start && adapter.isLoggedIn() && !ConfigurationOption.DuplicateOrMoveBugStatusVerified.isActive();
    }

    @TestStep(name = "resetDuplicateOrMoveBugStatusVerified", weight = WEIGHT)
    public void resetDuplicateOrMoveBugStatusVerified(){
        new DuplicateOrMoveBugStatusVerified().reset(adapter);
        ConfigurationOption.DuplicateOrMoveBugStatusVerified.reset();
        adapter.gotoStart();
    }

    @Guard("resetDuplicateOrMoveBugStatusVerified")
    public boolean resetDuplicateOrMoveBugStatusVerifiedGuard() {
        return state.getScenario() == null && state.getPage() == BugzillaState.Page.Start && adapter.isLoggedIn() && ConfigurationOption.DuplicateOrMoveBugStatusVerified.isActive();
    }

    @AfterTest
    public void reset(){
        new Setup().reset();
        ConfigurationOption.UseStatusWhiteboard.reset();
        ConfigurationOption.Letsubmitterchoosepriority.reset();
        ConfigurationOption.AddProduct.reset();
        ConfigurationOption.AddComponent.reset();
        ConfigurationOption.AddVersion.reset();
        ConfigurationOption.SimpleBugWorkflow.reset();
        ConfigurationOption.UnconfirmedState.reset();
        ConfigurationOption.CommentOnBugCreation.reset();
        ConfigurationOption.CommentOnAllTransitions.reset();
        ConfigurationOption.CommentOnChangeResolution.reset();
        ConfigurationOption.CommentOnDuplicate.reset();
        ConfigurationOption.NoResolveOnOpenBlockers.reset();
        ConfigurationOption.DuplicateOrMoveBugStatusVerified.reset();
    }

}
