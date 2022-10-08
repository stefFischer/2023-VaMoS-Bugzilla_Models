package at.scch.mbteclo.state;

import osmo.tester.OSMOTester;
import osmo.tester.model.FSM;
import osmo.tester.model.FSMTransition;
import osmo.tester.model.InvocationTarget;

import java.util.*;
import java.util.stream.Collectors;

public class ConfigurationSelector {

    public static class Feature{
        public final boolean positive;
        public final ConfigurationOption option;
        public final int number;

        public Feature(boolean positive, ConfigurationOption option, int number) {
            this.positive = positive;
            this.option = option;
            this.number = number;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Feature feature = (Feature) o;
            return positive == feature.positive && option == feature.option;
        }

        @Override
        public int hashCode() {
            return Objects.hash(positive, option);
        }

        public boolean isPositive() {
            return positive;
        }

        public boolean isNegative() {
            return !positive;
        }

        public static Feature positive(ConfigurationOption option, int number){
            return new Feature(true, option, number);
        }

        public static Feature negative(ConfigurationOption option, int number){
            return new Feature(false, option, number);
        }

        @Override
        public String toString() {
            if(positive){
                return option.name();
            } else {
                return "!" + option.name();
            }
        }
    }

    public static void selectConfigurations(OSMOTester tester){
        ConfigurationOption[] options = ConfigurationOption.values();
        FSM fsm = tester.getFsm();

        int t_min = 1;
        int t_max = 4;
        int[] features = new int[options.length];
        for (int i = 0; i < features.length; i++) {
            features[i] = i + 1;
        }
        Map<Integer, Set<Set<Integer>>> tSets = new HashMap<>();
        getTSets(features, t_min, t_max, tSets);

        System.out.println("Test steps: " + fsm.getTransitions().size());

        long count = numberOfTsets(tSets, t_max);
        System.out.println(count);

        Map<String, Set<Set<Feature>>> variationPoints = new HashMap<>();
        for (FSMTransition fsmTransition : fsm.getTransitions()) {

            Map<String, Set<Set<Feature>>> guards = new HashMap<>();
            for (InvocationTarget guard : fsmTransition.getGuards()) {
                String name = guard.getMethod().getName();
                Set<Set<Feature>> products = new HashSet<>();

                // Find all 4-wise combinations for which the guard returns true, regardless if all other options are positive or negative.
                Set<Set<Integer>> maxTSets = tSets.get(t_max);
                for (Set<Integer> set : maxTSets) {
                    Set<Feature> product = validProduct(options, set,guard);
                    if(product != null){
                        products.add(product);
                    }
                }

                if(!products.isEmpty() && products.size() < count){
                    Map<Set<Integer>, Long> superSetCounts = new HashMap<>();
                    Set<Set<Feature>> toRemove = new HashSet<>();
                    Set<Set<Feature>> toAdd = new HashSet<>();
                    for (int t = t_min; t < t_max; t++) {
                        Set<Set<Integer>> t_sets = tSets.get(t);
                        for (Set<Integer> t_set : t_sets) {
                            Set<Feature> t_setFeatures = toFeatureSet(options, t_set);
                            if (toRemove.contains(t_setFeatures)) {
                                continue;
                            }
                            boolean skip = false;
                            for (Set<Feature> featureSet : toAdd) {
                                if(t_setFeatures.containsAll(featureSet)){
                                    skip = true;
                                    break;
                                }
                            }
                            if(skip){
                                continue;
                            }

                            long superCount = superSetCounts.computeIfAbsent(t_set, k -> computeSuperSetCount(k, tSets, t_max));
                            Set<Set<Feature>> superSets = computeSuperSetCount(t_set, products);

                            // If for a tSet all supersets are contained in the products, the other options are not needed. Remove supersets and add tSet instead.
                            if (superSets.size() == superCount) {
                                toRemove.addAll(superSets);
                                toAdd.add(t_setFeatures);
                            }
                        }
                    }

                    products.removeAll(toRemove);
                    products.addAll(toAdd);

                    guards.put(name, products);
                }
            }

            // Entries are in an OR relation but the conditions over the guards are in an AND relation.
            String condition = "";
            boolean first = true;
            for (Map.Entry<String, Set<Set<Feature>>> entry : guards.entrySet()) {
                if(!first){
                    condition += " && "; // AND over multiple guards.
                }
                String cc = "(";
                boolean ff = true;
                for (Set<Feature> featureSet : entry.getValue()) {
                    if(!ff){
                        cc += " || "; // OR over multiple featureSets that fulfill the guard.
                    }
                    String c = "(";
                    boolean f = true;
                    for (Feature feature : featureSet) {
                        if(!f){
                            c += " && "; // AND within a featureSet.
                        }
                        c += feature.toString();
                        f = false;
                    }
                    c += ")";

                    cc += c;
                    ff = false;
                }

                cc += ")";

                condition += cc;
                first = false;
            }

            System.out.println(fsmTransition.getStringName() + ": " + condition);

//            variationPoints.put(fsmTransition.getStringName(), consistent);
        }
    }

    private static Set<Set<Feature>> computeSuperSetCount(Set<Integer> t_set, Set<Set<Feature>> consistent) {
        Set<Set<Feature>> superSets = new HashSet<>();
        for (Set<Feature> features : consistent) {
            Set<Integer> featureNumbers = new HashSet<>();
            for (Feature feature : features) {
                if(feature.isPositive()){
                    featureNumbers.add(feature.number);
                } else {
                    featureNumbers.add(-feature.number);
                }
            }
            if(featureNumbers.containsAll(t_set)){
                superSets.add(features);
            }
        }
        return superSets;
    }

    private static long computeSuperSetCount(Set<Integer> tSet, Map<Integer, Set<Set<Integer>>> tSets, int t) {
        long count = 0;
        Set<Set<Integer>> t_Sets = tSets.get(t);
        for (Set<Integer> t_set : t_Sets) {
            if(t_set.containsAll(tSet)){
                count++;
            }
        }

        return count;
    }

    private static long numberOfTsets(Map<Integer, Set<Set<Integer>>> tSets){
        long count = 0;
        for (Map.Entry<Integer, Set<Set<Integer>>> entry : tSets.entrySet()) {
            count += entry.getValue().size();
        }
        return count;
    }

    private static long numberOfTsets(Map<Integer, Set<Set<Integer>>> tSets, int t) {
        return tSets.get(t).size();
    }

    private static Set<Feature> toFeatureSet(ConfigurationOption[] options, Set<Integer> set){
        Set<Feature> features = new HashSet<>();
        for (Integer f : set) {
            ConfigurationOption option = options[Math.abs(f) - 1];
            if(f > 0){
                features.add(Feature.positive(option, Math.abs(f)));
            } else {
                features.add(Feature.negative(option, Math.abs(f)));
            }
        }
        return features;
    }

    private static Set<Feature> validProduct(ConfigurationOption[] options, Set<Integer> set, InvocationTarget guard){
        Set<Feature> features = new HashSet<>();
        Set<ConfigurationOption> usedOptions = new HashSet<>();
        for (Integer f : set) {
            ConfigurationOption option = options[Math.abs(f) - 1];
            usedOptions.add(option);
            if(f > 0){
                features.add(Feature.positive(option, Math.abs(f)));
                option.override(true);
            } else {
                features.add(Feature.negative(option, Math.abs(f)));
                option.override(false);
            }
        }

        for (ConfigurationOption option : options) {
            if(usedOptions.contains(option)){
                continue;
            }
            option.override(false);
        }
        boolean resultFalse = (boolean) guard.invoke();

        for (ConfigurationOption option : options) {
            if(usedOptions.contains(option)){
                continue;
            }
            option.override(true);
        }
        boolean resultTrue = (boolean) guard.invoke();

        for (ConfigurationOption option : options) {
            option.stopOverride();
        }

        if(resultFalse && resultTrue){
            return features;
        }

        return null;
    }

    private static void getTSets(int[] features, int t_min, int t_max, Map<Integer, Set<Set<Integer>>> tSets){
        getTSets(features, t_min, t_max, 0, null, 0, tSets);
    }

    private static void getTSets(int[] features, int t_min, int t_max, int curF, int[] tSet, int added, Map<Integer, Set<Set<Integer>>> tSets) {
        if(tSet == null){
            //start new
            added = 0;
            tSet = new int[t_max];
            curF = 0;
        }
        if(added >= t_min && added <= t_max){
            //complete tSet
            Set<Integer> ass = Arrays.stream(tSet).boxed().filter(i -> i != 0).collect(Collectors.toSet());
            Set<Set<Integer>> set = tSets.computeIfAbsent(added, k -> new HashSet<>());
            set.add(ass);
        }
        if(added < t_max){
            //add more features
            for(int i=curF; i < features.length ;i++){
                tSet[added] = features[i];
                getTSets(features, t_min, t_max, i+1, tSet, added+1, tSets);

                tSet[added] = -features[i];
                getTSets(features, t_min, t_max, i+1, tSet, added+1, tSets);
            }
            tSet[added] = 0;
        }
    }
}
