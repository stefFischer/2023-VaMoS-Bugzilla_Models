package at.scch.mbteclo.factories;

import at.scch.mbteclo.state.Bug;
import at.scch.mbteclo.state.BugzillaState;
import at.scch.mbteclo.state.ConfigurationOption;
import at.scch.mbteclo.state.Product;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * Creates bugs that can be given to a page object, which fills the necessary
 * fields. This factory exists in order to have one single point at which the
 * data can be changed and therefore have consistent test data.
 */
public class BugFactory {


	private static final String[] INITIAL_STATUS = {"UNCONFIRMED", "CONFIRMED", "IN_PROGRESS"};

	private static final String[] SEVERITY = {"blocker", "critical", "major", "normal", "minor", "trivial", "enhancement"};
	/**
	 * TODO priority can not be submitted in C02
	 */
	private static final String[] PRIORITY = {"Highest", "High", "Normal", "Low", "Lowest", "---"};

	private static final String[] HARDWARE = {"All", "PC", "Macintosh", "Other"};
	private static final String[] OS = {"All", "Windows", "Mac OS", "Linux", "Other"};

	/**
	 * Fills the fields: Severity, Summary, Hardware, Operating System and status.
	 */
	public Bug getValidBug(Product product) {
		Bug bug = new Bug(product.getName(), product.getRandomComponent(), product.getRandomVersion(), getRandomSeverity(), "---", generateSummary(), getRandomHardWare(), getRandomOS(), "CONFIRMED");
		if (ConfigurationOption.CommentOnBugCreation.isActive() || ConfigurationOption.CommentOnAllTransitions.isActive()) {
			bug.setDescription("Comment for " + bug.getSummary());
		}
		return bug;
	}

	/**
	 * Fills the fields: Severity, Summary, Hardware, Operating System, Status,
	 * SeeAlso. Gives a compatible URL for the field <em>See also</em>.
	 */
	public Bug getValidBugAdvanced(Product product) {
		Bug bug = new Bug(product.getName(), product.getRandomComponent(), product.getRandomVersion(), getRandomSeverity(), getRandomPriority(), generateSummary(), getRandomHardWare(), getRandomOS(), getRandomState(), "https://bugs.debian.org/733762");
		if (ConfigurationOption.CommentOnBugCreation.isActive() || ConfigurationOption.CommentOnAllTransitions.isActive()) {
			bug.setDescription("Comment for " + bug.getSummary());
		}
		return bug;
	}

	/**
	 * Fills the fields: Severity, Summary, Hardware, Operating System and Status.
	 * Creates an Error by leaving the Summary blank.
	 */
	public Bug getErrorBug(Product product) {
		Bug bug = new Bug(product.getName(), product.getRandomComponent(), product.getRandomVersion(), getRandomSeverity(), "---", "", getRandomHardWare(), getRandomOS(), "");
		if (ConfigurationOption.CommentOnBugCreation.isActive() || ConfigurationOption.CommentOnAllTransitions.isActive()) {
			bug.setDescription("Comment for " + bug.getSummary());
		}
		return bug;
	}

	/**
	 * Fills the fields: Severity, Summary, Hardware, Operating System, Status,
	 * SeeAlso. Creates an Error by giving an incompatible URL for <em>See
	 * also</em>.
	 */
	public Bug getErrorBugAdvanced(Product product) {
		Bug bug = new Bug(product.getName(), product.getRandomComponent(), product.getRandomVersion(), getRandomSeverity(), getRandomPriority(), generateSummary(), getRandomHardWare(), getRandomOS(), getRandomState(), "test.at");
		if (ConfigurationOption.CommentOnBugCreation.isActive() || ConfigurationOption.CommentOnAllTransitions.isActive()) {
			bug.setDescription("Comment for " + bug.getSummary());
		}
		return bug;
	}

	/**
	 * Fills the fields: Severity, Summary, Hardware, Operating System and status.
	 * Does not fill Description (Comment), which causes an Error in some configurations
	 */
	public Bug getErrorNoCommentBug(Product product) {
		Bug bug = new Bug(product.getName(), product.getRandomComponent(), product.getRandomVersion(), getRandomSeverity(), "---", generateSummary(), getRandomHardWare(), getRandomOS(), "CONFIRMED");
		return bug;
	}

	/**
	 * Fills the fields: Severity, Summary, Hardware, Operating System, Status,
	 * SeeAlso. Gives a compatible URL for the field <em>See also</em>.
	 * Does not fill Description (Comment), which causes an Error in some configurations
	 */
	public Bug getErrorNoCommentBugAdvanced(Product product) {
		Bug bug = new Bug(product.getName(), product.getRandomComponent(), product.getRandomVersion(), getRandomSeverity(), getRandomPriority(), generateSummary(), getRandomHardWare(), getRandomOS(), getRandomState(), "https://bugs.debian.org/733762");
		return bug;
	}

	public static String getRandomState() {
		if (ConfigurationOption.SimpleBugWorkflow.isActive() || ConfigurationOption.UnconfirmedState.isActive()) {
			return "CONFIRMED";
		} else {
			Random rand = new Random();
			return INITIAL_STATUS[rand.nextInt(INITIAL_STATUS.length)];
		}
	}

	public static String getRandomSeverity(){
		Random rand = new Random();
		return SEVERITY[rand.nextInt(SEVERITY.length)];
	}

	public static String getRandomPriority(){
		if(ConfigurationOption.Letsubmitterchoosepriority.isActive()){
			return PRIORITY[5];
		} else {
			Random rand = new Random();
			return PRIORITY[rand.nextInt(PRIORITY.length)];
		}
	}

	public static String getRandomHardWare(){
		Random rand = new Random();
		return HARDWARE[rand.nextInt(HARDWARE.length)];
	}

	public static String getRandomOS(){
		Random rand = new Random();
		return OS[rand.nextInt(OS.length)];
	}

	/**
	 * Returns a random date between ten years ago or ten years from now in the future
	 * as a String formatted as yyyy-MM-dd
	 * @return
	 */
	public static String getRandomDate(){
        long aDay = TimeUnit.DAYS.toMillis(1);
        long now = new Date().getTime();
        Date from = new Date(now - aDay * 365 * 10); //ten years ago
        Date to = new Date(now + aDay * 365 * 10); //ten years from now
        Date random = between(from, to);
        return new SimpleDateFormat("yyyy-MM-dd").format(random);
    }

	/**
	 * Returns random date between start (inclusive) and end (exclusive)
	 * @param startInclusive
	 * @param endExclusive
	 * @return
	 */
    private static Date between(Date startInclusive, Date endExclusive) {
        long startMillis = startInclusive.getTime();
        long endMillis = endExclusive.getTime();
        long randomMillisSinceEpoch = ThreadLocalRandom
                .current()
                .nextLong(startMillis, endMillis);

        return new Date(randomMillisSinceEpoch);
    }

	private String generateSummary(){
		return "Bug_" + BugzillaState.id;
	}
}
