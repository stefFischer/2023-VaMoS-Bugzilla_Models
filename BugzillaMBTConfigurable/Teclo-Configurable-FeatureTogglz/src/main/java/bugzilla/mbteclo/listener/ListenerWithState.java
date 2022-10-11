package bugzilla.mbteclo.listener;

import bugzilla.teclo.BugzillaSetup;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import osmo.tester.OSMOConfiguration;
import osmo.tester.generator.listener.GenerationListener;
import osmo.tester.generator.testsuite.TestCase;
import osmo.tester.generator.testsuite.TestCaseStep;
import osmo.tester.generator.testsuite.TestSuite;
import osmo.tester.model.FSM;
import osmo.tester.model.FSMTransition;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Writes the status before the transition, the transition and the status
 * after the transition to a PrintStream. By default, everything is written to
 * System.out.
 * 
 * In order to get the name of the current state, this listener invokes every
 * coverage method and prints the resulting List<String>.
 */
public class ListenerWithState implements GenerationListener {
	private FSM fsm;
	private PrintStream out;
	private int counter;
	String step = "";

	public ListenerWithState() {
		this(System.out);
	}

	public ListenerWithState(PrintStream out) {
		this.out = out;
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
		this.step = counter++ + ". " + getCurrentState(step) + " ";
	}

	@Override
	public void stepDone(TestCaseStep step) {
		this.step = this.step + step.getName() + " " + getCurrentState(step);
		out.println(this.step);
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
		this.counter = 1;
	}

	@Override
	public void testEnded(TestCase test) {

	}

	@Override
	public void testError(TestCase test, Throwable error) {
		out.println("(ERROR)" + test.getCurrentStep().getName());
		takeSnapShot("target/ERROR_PAGE.png");
	}

	@Override
	public void suiteStarted(TestSuite suite) {
	}

	@Override
	public void suiteEnded(TestSuite suite) {
	}

	private String getCurrentState(TestCaseStep step) {
		List<String> states = fsm.getCoverageMethods().stream().map(state -> state.invoke(step))
				.collect(Collectors.toList());
		return states.toString();
	}

	/**
	 * This function will take screenshot
	 * @param fileWithPath
	 * @throws Exception
	 */
	public void takeSnapShot(String fileWithPath) {
		//Convert web driver object to TakeScreenshot
		TakesScreenshot scrShot =((TakesScreenshot) BugzillaSetup.driver);
		//Call getScreenshotAs method to create image file
		File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
		//Move image file to new destination
		File DestFile=new File(fileWithPath);
		//Copy file at destination
		try {
			FileUtils.copyFile(SrcFile, DestFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}