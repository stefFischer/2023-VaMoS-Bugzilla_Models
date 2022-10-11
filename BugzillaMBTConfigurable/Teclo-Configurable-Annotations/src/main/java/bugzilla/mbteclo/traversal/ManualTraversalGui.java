package bugzilla.mbteclo.traversal;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import osmo.tester.generator.algorithm.FSMTraversalAlgorithm;
import osmo.tester.generator.testsuite.TestSuite;
import osmo.tester.model.FSM;
import osmo.tester.model.FSMTransition;

import java.util.List;

public class ManualTraversalGui extends Application implements FSMTraversalAlgorithm{
	private static TraversalLayoutBuilder builder;
	private static boolean isRunning = false;
	private String title = "ManualTraversalGui";
	private GuiState state = new GuiState();
	private static Stage primaryStage;
	
	@Override
	public FSMTransition choose(TestSuite suite, List<FSMTransition> choices) {
		builder.getButtons().stream()
			.filter(button -> choices.stream().anyMatch(choice -> button.getId().equals(choice.getStringName())))
			.forEach(button -> button.setDisable(false));
		waitForChoice();
		return choices.stream()
				.filter(choice -> choice.getStringName().toLowerCase().equals(state.getTransition().toLowerCase()))
				.findFirst().get();
	}
	
	@Override
	public void init(long seed, FSM fsm) {
		builder = new TraversalLayoutBuilder(state);
		fsm.getTransitions().stream()
			.forEach((transition) -> builder.addFSMTransition(transition));
		launchOrRefresh();
	}

	@Override
	public void initTest(long seed) {
	}
	
	@Override
	public FSMTraversalAlgorithm cloneMe() {
		return this;
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		ManualTraversalGui.primaryStage = primaryStage;
		ManualTraversalGui.primaryStage.setTitle(title);
		ManualTraversalGui.primaryStage.setScene(new Scene(builder.getGrid()));
		ManualTraversalGui.primaryStage.show();
	}
	
	private void waitForChoice() {
		while(!state.isNewTransitionSelected()) {
			sleep(10);
		}
		state.setNewTransitionSelected(false);
		builder.getButtons().stream()
			.forEach(button -> button.setDisable(true));
	}
	
	private void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// ignore
		}
	}
	
	private void launchOrRefresh() {
		if(!isRunning) {
			new Thread(() -> launch(ManualTraversalGui.class)).start();
			isRunning = true;
		} else {
			Platform.runLater(() -> {
				ManualTraversalGui.primaryStage.close();
				ManualTraversalGui.primaryStage.setScene(new Scene(builder.getGrid()));
				ManualTraversalGui.primaryStage.show();
			});
		}
	}
}
