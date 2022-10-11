package bugzilla.teclo;

import bugzilla.mbteclo.state.ConfigurationOption;

public class TestConfig {

    public static void main(String[] args){
        System.out.println("UseStatusWhiteboard " + ConfigurationOption.UseStatusWhiteboard.isActive());
        System.out.println("Letsubmitterchoosepriority " + ConfigurationOption.Letsubmitterchoosepriority.isActive());
        System.out.println("AddProduct " + ConfigurationOption.AddProduct.isActive());
        System.out.println("AddComponent " + ConfigurationOption.AddComponent.isActive());
        System.out.println("AddVersion " + ConfigurationOption.AddVersion.isActive());
        System.out.println("SimpleBugWorkflow " + ConfigurationOption.SimpleBugWorkflow.isActive());
        System.out.println("UnconfirmedState " + ConfigurationOption.UnconfirmedState.isActive());
        System.out.println("CommentOnBugCreation " + ConfigurationOption.CommentOnBugCreation.isActive());
        System.out.println("CommentOnAllTransitions " + ConfigurationOption.CommentOnAllTransitions.isActive());
        System.out.println("CommentOnChangeResolution " + ConfigurationOption.CommentOnChangeResolution.isActive());
        System.out.println("CommentOnDuplicate " + ConfigurationOption.CommentOnDuplicate.isActive());
        System.out.println("NoResolveOnOpenBlockers " + ConfigurationOption.NoResolveOnOpenBlockers.isActive());
        System.out.println("DuplicateOrMoveBugStatusVerified " + ConfigurationOption.DuplicateOrMoveBugStatusVerified.isActive());
    }
}
