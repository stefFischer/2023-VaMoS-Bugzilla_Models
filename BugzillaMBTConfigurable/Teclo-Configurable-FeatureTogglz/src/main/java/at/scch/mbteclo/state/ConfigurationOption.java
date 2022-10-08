package at.scch.mbteclo.state;

import org.togglz.core.Feature;
import org.togglz.core.annotation.Label;

public enum ConfigurationOption implements Feature {

    @Label("UseStatusWhiteboard")
    UseStatusWhiteboard,

    @Label("Letsubmitterchoosepriority")
    Letsubmitterchoosepriority,

    @Label("AddProduct")
    AddProduct,

    @Label("AddComponent")
    AddComponent,

    @Label("AddVersion")
    AddVersion,

    @Label("SimpleBugWorkflow")
    SimpleBugWorkflow,

    @Label("UnconfirmedState")
    UnconfirmedState,

    @Label("CommentOnBugCreation")
    CommentOnBugCreation,

    @Label("CommentOnAllTransitions")
    CommentOnAllTransitions,

    @Label("CommentOnChangeResolution")
    CommentOnChangeResolution,

    @Label("CommentOnDuplicate")
    CommentOnDuplicate,

    @Label("NoResolveOnOpenBlockers")
    NoResolveOnOpenBlockers,

    @Label("DuplicateOrMoveBugStatusVerified")
    DuplicateOrMoveBugStatusVerified;

    private enum Value{
        TRUE, FALSE, UNSET
    }

    private Value value;

    private boolean overwritten;

    private boolean overwrittenValue;

    ConfigurationOption() {
        this.value = Value.UNSET;
        this.overwritten = false;
        this.overwrittenValue = false;
    }

    public void override(boolean overwrittenValue){
        this.overwritten = true;
        this.overwrittenValue = overwrittenValue;
    }

    public void stopOverride(){
        this.overwritten = false;
        this.overwrittenValue = false;
    }

    public void invert(){
        boolean cur = isActive();
        override(!cur);
    }

    public void set() {
        this.value = Value.TRUE;
    }

    public void reset() {
        this.value = Value.FALSE;
    }

    @Override
    public boolean isActive() {
        if(overwritten){
            return overwrittenValue;
        }

        if(value != Value.UNSET) {
            return value == Value.TRUE;
        }

        return Feature.super.isActive();
    }
}
