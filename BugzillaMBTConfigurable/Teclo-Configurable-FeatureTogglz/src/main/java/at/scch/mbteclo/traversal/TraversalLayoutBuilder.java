package at.scch.mbteclo.traversal;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import osmo.tester.model.FSMTransition;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TraversalLayoutBuilder{
	private Map<String, Collection<String>> modelObjectsAndTransitions = new HashMap<>();
	private Collection<Button> buttons = new HashSet<>();
	private GuiState state;

	public TraversalLayoutBuilder(GuiState state) {
		this.state = state;
	}

	public void addFSMTransition(FSMTransition transition) {
		Collection<String> transitions = modelObjectsAndTransitions.get(transition.getModelObjectName());
		if(transitions == null) {
			modelObjectsAndTransitions.put(transition.getModelObjectName(), 
					Stream.of(transition.getStringName()).collect(Collectors.toList()));
		} else {
			transitions.add(transition.getStringName());
		}
	}
	
	public  Collection<Button> getButtons(){
		return buttons; 
	}
	
	public HBox getGrid() {
		HBox hBox = new HBox();
		hBox.setPadding(new Insets(15,12,15,12));
		hBox.setSpacing(10);
		
		modelObjectsAndTransitions.entrySet().stream()
			.forEach((modelObject) -> {
				String modelName = modelObject.getKey();
				VBox vbox = new VBox();
				vbox.setId(modelName);
				vbox.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
						+ "-fx-border-width: .5;" + "-fx-border-insets: 5;"
						+ "-fx-border-color: black;" + "-fx-spacing: 10;"
				);
				Collection<String> transitions = modelObject.getValue();
				
				Text text = new Text(modelName.substring(modelName.lastIndexOf(".")+1, modelName.length()));
				
				text.setFont(Font.font(15));
				vbox.getChildren().add(text);
				
				transitions.stream().forEach((transition) -> {
					Button btn = new Button(transition);
					btn.setOnAction(event -> {
						state.setNewTransitionSelected(true);
						state.setTransition(((Control)event.getSource()).getId());
					});
					
					btn.setDisable(true);
					btn.setId(transition);
					vbox.getChildren().add(btn);	
					buttons.add(btn);
				});
				hBox.getChildren().add(vbox);
			});
		
		hBox.setAlignment(Pos.CENTER);
		return hBox;
	}
}
