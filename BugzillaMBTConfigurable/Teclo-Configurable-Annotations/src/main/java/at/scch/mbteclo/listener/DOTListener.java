package at.scch.mbteclo.listener;

import osmo.tester.OSMOConfiguration;
import osmo.tester.generator.listener.GenerationListener;
import osmo.tester.generator.testsuite.TestCase;
import osmo.tester.generator.testsuite.TestCaseStep;
import osmo.tester.generator.testsuite.TestSuite;
import osmo.tester.model.FSM;
import osmo.tester.model.FSMTransition;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Implements GenerationListener from OSMOTester. Therefore, it can be attached
 * to OSMOTester as a listener. After the test is finished, the listener writes
 * everything to a dot-file. After that the graph is built as a png-file.
 * For that graphviz has to be installed and in the path.
 */
public class DOTListener implements GenerationListener {
	private FSM fsm;
	private String filename;
	private String stateBeforeTeststep;
	private Map<String, Set<String>> graph;

	public DOTListener(String filename) {
		this.filename = filename;
		graph = new HashMap<>();
	}

	@Override
	public void init(long seed, FSM fsm, OSMOConfiguration config) {
		this.fsm = fsm;
	}

	@Override
	public void guard(FSMTransition transition) {

	}

	@Override
	public void stepStarting(TestCaseStep step) {
		stateBeforeTeststep = getCurrentState(step);
	}

	@Override
	public void stepDone(TestCaseStep step) {
		Set<String> setWithNewStep = new HashSet<>();
		setWithNewStep.add(constructLabel(step));
		Set<String> set = graph.putIfAbsent(stateBeforeTeststep + "->" + getCurrentState(step), setWithNewStep);
		if (set != null) {
			set.addAll(setWithNewStep);
		}
	}

	@Override
	public void lastStep(String name) {

	}

	@Override
	public void pre(FSMTransition transition) {

	}

	@Override
	public void post(FSMTransition transition) {

	}

	@Override
	public void testStarted(TestCase test) {

	}

	@Override
	public void testEnded(TestCase test) {
		writeTheGraph();

	}

	@Override
	public void testError(TestCase test, Throwable error) {
		writeTheGraph();
	}

	@Override
	public void suiteStarted(TestSuite suite) {

	}

	@Override
	public void suiteEnded(TestSuite suite) {

	}

	private String getCurrentState(TestCaseStep step) {
		return fsm.getCoverageMethods().stream().findFirst().get().invoke(step);
	}

	private String constructLabel(TestCaseStep step) {
		return "[label=\"" + step.getName() + "\"]";
	}

	private void createPng() {
		try {
			System.out.println("dot -Tpng " + filename  + " -o " + filename.substring(0, filename.indexOf(".")) + ".png");

			new ProcessBuilder("dot", "-Tpng", filename, "-o",
					filename.substring(0, filename.indexOf(".")) + ".png").start();
		} catch (Exception e) {
			String errorMsg = "Failed to run DOT. Have you installed Graphviz and put it on path?";
			throw new RuntimeException(errorMsg, e);
		}
	}

	private void writeTheGraph() {
		try (PrintWriter out = new PrintWriter(new FileWriter(filename));) {
			out.println("digraph G{");
			writeTheTransitions(out);
			out.println("}");
			out.close();
			createPng();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void writeTheTransitions(PrintWriter out) {
		graph.entrySet().stream().forEach(transition -> transition.getValue().stream()
				.forEach(label -> out.println("\t" + transition.getKey() + label + ";")));

	}
}
