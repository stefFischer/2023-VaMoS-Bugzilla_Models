package at.scch.mbteclo.traversal;

import at.scch.mbteclo.model.configuration.ConfigurationModel;
import at.scch.mbteclo.state.ConfigurationOption;
import osmo.tester.OSMOTester;
import osmo.tester.generator.algorithm.FSMTraversalAlgorithm;
import osmo.tester.generator.listener.AbstractListener;
import osmo.tester.generator.testsuite.TestSuite;
import osmo.tester.model.FSM;
import osmo.tester.model.FSMTransition;
import osmo.tester.model.InvocationTarget;

import java.lang.reflect.Method;
import java.util.*;

public class FeatureToggleAlgorithm extends AbstractListener implements FSMTraversalAlgorithm {

    private final FSMTraversalAlgorithm parent;

    private final OSMOTester tester;

    private Map<String, Map<String, Set<ConfigurationOption>>> variationPoints;

    private Map<Method, List<Long>> invocationTimes = new HashMap<>();

    public FeatureToggleAlgorithm(FSMTraversalAlgorithm algorithm, OSMOTester tester) {
        this.parent = algorithm;
        this.tester = tester;
        this.variationPoints = null;
    }

    @Override
    public FSMTransition choose(TestSuite testSuite, List<FSMTransition> list) {
        ConfigurationOption[] options = ConfigurationOption.values();
        FSM fsm = tester.getFsm();

        if(variationPoints == null){
            variationPoints = new HashMap<>();
            for (FSMTransition fsmTransition : fsm.getTransitions()) {
                if(fsmTransition.getTransition().getModelObject().getClass() == ConfigurationModel.class){
                    continue;
                }

                Map<String, Set<ConfigurationOption>> guards = new HashMap<>();
                variationPoints.put(fsmTransition.getStringName(), guards);
                for (InvocationTarget guard : fsmTransition.getGuards()) {
                    String name = guard.getMethod().getName();
                    List<Long> times = invocationTimes.computeIfAbsent(guard.getMethod(), k -> new LinkedList<>());

                    Set<ConfigurationOption> effecting = new HashSet<>();
                    long start = System.currentTimeMillis();
                    boolean guardResult = (boolean) guard.invoke();
                    times.add(System.currentTimeMillis() - start);
                    for (ConfigurationOption option : options) {
                        option.invert();
                        start = System.currentTimeMillis();
                        boolean guardChangedResult = (boolean) guard.invoke();
                        times.add(System.currentTimeMillis() - start);
                        if(guardResult != guardChangedResult){
                            effecting.add(option);
                        }
                        option.stopOverride();
                    }

                    if(!effecting.isEmpty()){
                        guards.put(name, effecting);
                    }
                }
            }

            return parent.choose(testSuite, list);
        }

//        System.out.println("Model from fsm");
//        for (FSMTransition fsmTransition : fsm.getTransitions()) {
//            boolean isActive = list.contains(fsmTransition);
//            if(isActive) {
//                Object model = fsmTransition.getTransition().getModelObject();
//                System.out.println(model);
//            }
//        }
//
//        System.out.println("Model from list");
//        for (FSMTransition fsmTransition : list) {
//            Object model = fsmTransition.getTransition().getModelObject();
//            System.out.println(model);
//        }

        for (FSMTransition fsmTransition : fsm.getTransitions()) {
            if(fsmTransition.getTransition().getModelObject().getClass() == ConfigurationModel.class){
                continue;
            }

            boolean isActive = list.contains(fsmTransition);
//            System.out.println(fsmTransition.getStringName() + " active: " + isActive);

            Map<String, Set<ConfigurationOption>> guards = variationPoints.computeIfAbsent(fsmTransition.getStringName(), k -> new HashMap<>());

            for (InvocationTarget guard : fsmTransition.getGuards()) {
//                System.out.println("\t" + guard.getMethod().getName());

                String name = guard.getMethod().getName();
                List<Long> times = invocationTimes.computeIfAbsent(guard.getMethod(), k -> new LinkedList<>());

                Set<ConfigurationOption> effecting = guards.computeIfAbsent(name, k -> new HashSet<>());

                boolean guardResult;
                if(isActive){
                    guardResult = true;
                } else {
                    long start = System.currentTimeMillis();
                    guardResult = (boolean) guard.invoke();
                    times.add(System.currentTimeMillis() - start);
                }

//                System.out.println("\t" + guardResult);
                for (ConfigurationOption option : options) {
                    // Skip options we already know have an effect.
                    if(effecting.contains(option)){
                        continue;
                    }

//                    System.out.println("\t" + option.name() + " active: " + option.isActive());

                    option.invert();

                    long start = System.currentTimeMillis();
                    boolean guardChangedResult = (boolean) guard.invoke();
                    times.add(System.currentTimeMillis() - start);

                    if(guardResult != guardChangedResult){
                        effecting.add(option);
                    }

                    option.stopOverride();
                }

            }
        }

        return parent.choose(testSuite, list);
    }

    @Override
    public void init(long l, FSM fsm) {
        parent.init(l, fsm);
    }

    @Override
    public void initTest(long l) {
        parent.initTest(l);
    }

    @Override
    public FSMTraversalAlgorithm cloneMe() {
        return this;
    }

    @Override
    public void suiteEnded(TestSuite suite) {
        for (Map.Entry<Method, List<Long>> entry : invocationTimes.entrySet()) {
            Method m = entry.getKey();

            OptionalDouble avg = entry.getValue().stream().mapToDouble(l -> l).average();
            assert avg.isPresent();
            double average = avg.getAsDouble();
            if(average >= 1) {
                System.out.println(m.getDeclaringClass().getCanonicalName() + "." + m.getName());
                System.out.println(average);
            }
        }

        if(variationPoints != null){
            for (Map.Entry<String, Map<String, Set<ConfigurationOption>>> entry : variationPoints.entrySet()) {
                String transition = entry.getKey();
                System.out.println(transition);
                for (Map.Entry<String, Set<ConfigurationOption>> transitionEntry : entry.getValue().entrySet()) {
                    String guard = transitionEntry.getKey();
                    System.out.println(guard + ":" + transitionEntry.getValue());
                }
            }
        }
    }
}
