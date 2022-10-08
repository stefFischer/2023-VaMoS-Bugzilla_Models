package at.scch.mbteclo.traversal;

public class GuiState {
	private boolean newTransitionSelected = false;
	private String transition = "";
	
	public boolean isNewTransitionSelected() {
		return newTransitionSelected;
	}
	public void setNewTransitionSelected(boolean newTransitionSelected) {
		this.newTransitionSelected = newTransitionSelected;
	}
	public String getTransition() {
		return transition;
	}
	public void setTransition(String newTransition) {
		this.transition = newTransition;
	}
}
