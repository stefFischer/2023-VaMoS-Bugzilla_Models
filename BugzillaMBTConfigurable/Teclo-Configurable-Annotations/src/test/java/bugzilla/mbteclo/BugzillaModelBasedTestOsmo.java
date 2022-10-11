package bugzilla.mbteclo;

import bugzilla.mbteclo.adapter.Adapter;
import bugzilla.teclo.configuration.Configurator;
import bugzilla.mbteclo.factories.BugzillaModelFactory;
import bugzilla.mbteclo.listener.DOTListener;
import bugzilla.mbteclo.listener.ListenerWithState;
import bugzilla.teclo.BugzillaSetup;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import osmo.common.TestUtils;
import osmo.tester.OSMOTester;
import osmo.tester.explorer.ExplorationConfiguration;
import osmo.tester.generator.algorithm.WeightedBalancingAlgorithm;
import osmo.tester.generator.endcondition.Length;
import osmo.tester.generator.testsuite.TestSuite;
import osmo.tester.model.FSM;
import osmo.tester.model.ModelFactory;
import osmo.tester.reporting.coverage.CSVCoverageReporter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class BugzillaModelBasedTestOsmo {
	private Adapter adapter;

	private Configurator configurator;

	@Before
	public void init() {
		BugzillaSetup.initialize();
		/* in order to not test the SUT (only run the model) run with AdapterEmpty */
		adapter = new Adapter(BugzillaSetup.gotoStartPage(), BugzillaSetup.getPassword(), BugzillaSetup.getUsername());
	}

	@Test
	public void test() {
		Logger log = LoggerFactory.getLogger(BugzillaModelBasedTestOsmo.class);
		long seed = System.currentTimeMillis();
		log.info("Seed is {}", seed);

		OSMOTester tester = makeOsmoTester(new BugzillaModelFactory(adapter), "test.dot", seed);
		tester.generate(seed);

		TestSuite suite = tester.getSuite();
		FSM fsm = tester.getFsm();
		CSVCoverageReporter csv = new CSVCoverageReporter(suite.getCoverage(), suite.getAllTestCases(), fsm);
		String report = csv.getTraceabilityMatrix();
		TestUtils.write(report, "target/modelCoverage.csv");
	}

	@After
	public void tearDown() {
		BugzillaSetup.close();
	}

	private ExplorationConfiguration getConfig() {
		BugzillaModelFactory factory = new BugzillaModelFactory(adapter);
		ExplorationConfiguration config = new ExplorationConfiguration(factory, 1, 55);
		config.setFallbackProbability(1d);
		config.setMinTestLength(10);
		config.setLengthWeight(0);
		config.setRequirementWeight(0);
		config.setDefaultValueWeight(1);
		config.setStepWeight(0);
		config.setStepPairWeight(10);
		config.setVariableCountWeight(0);
		config.setMinTestScore(60);
		config.setMinSuiteLength(5);
		config.setTestPlateauThreshold(1);
		config.setParallelism(1);
		return config;
	}

	private OSMOTester makeOsmoTester(ModelFactory factory, String dotFileName, long seed) {
		OSMOTester tester = new OSMOTester();

		int steps = Integer.parseInt(System.getProperty("TEST_LENGHT"));
		int tests = Integer.parseInt(System.getProperty("TESTS"));

        File states = new File("target/states.txt");

		tester.setModelFactory(factory);
		tester.setTestEndCondition(new Length(steps));
		tester.setSuiteEndCondition(new Length(tests));
		tester.setAlgorithm(new WeightedBalancingAlgorithm());
//		tester.setAlgorithm(new ManualTraversalGui());
		tester.addListener(new ListenerWithState());
        try {
            tester.addListener(new ListenerWithState(new PrintStream(states)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
		tester.addListener(new DOTListener(dotFileName));

		return tester;

	}

}
