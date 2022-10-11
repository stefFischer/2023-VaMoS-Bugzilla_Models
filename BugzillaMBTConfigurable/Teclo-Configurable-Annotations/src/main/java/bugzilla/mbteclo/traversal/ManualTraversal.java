package bugzilla.mbteclo.traversal;

import osmo.tester.generator.algorithm.FSMTraversalAlgorithm;
import osmo.tester.generator.testsuite.TestSuite;
import osmo.tester.model.FSM;
import osmo.tester.model.FSMTransition;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ManualTraversal implements FSMTraversalAlgorithm{
	int input;
	int idx = -1;
	Scanner sc = new Scanner(System.in);
	
	@Override
	public FSMTransition choose(TestSuite suite, List<FSMTransition> choices) {
		input = -1;
		idx = 0;
		
		List<String> choiceNames = choices.stream()
				.map(transition -> String.valueOf(idx++) + ": " + transition.getName().toString())
				.collect(Collectors.toList());
		
		System.out.println(choiceNames);
		
		while(input < 0 || input >= choices.size()){
			System.out.print("Type valid transition: ");
			input = sc.nextInt();
		}
		
		return choices.stream()
				.skip(input)
				.findFirst().get();
	}

	@Override
	public void init(long seed, FSM fsm) {
	}

	@Override
	public void initTest(long seed) {
		
	}

	@Override
	public FSMTraversalAlgorithm cloneMe() {
		return new ManualTraversal();
	}

}
