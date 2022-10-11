package bugzilla.mbteclo.state;

import java.util.*;

public class Product {

    private final String name;

    private final String description;

    private final Set<String> components;

    private final Set<String> versions;

    private final Set<Bug> addedBugs;

    public Product(String name) {
        this.name = name;
        this.description = name.toLowerCase();
        this.components = new HashSet<>();
        this.versions = new HashSet<>();
        this.addedBugs = new HashSet<>();
        addVersion(BugzillaState.DEFAULT_VERSION);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Set<String> getComponents() {
        return components;
    }

    public Set<String> getVersions() {
        return versions;
    }

    public void addComponent(String component) {
        this.components.add(component);
    }

    public void addVersion(String version) {
        this.versions.add(version);
    }

    public void removeComponent(String component) {
        this.components.remove(component);
    }

    public void removeVersion(String version) {
        this.versions.remove(version);
    }

    public String addRandomNewComponent(){
        String componentName = "Component";
        int i = 1;
        while(this.components.contains(componentName)){
            componentName = "Component" + i;
            i++;
        }
        this.components.add(componentName);
        return componentName;
    }

    public String getRandomComponent() {
        Random rand = new Random();
        String[] components = this.components.toArray(new String[this.components.size()]);
        return components[rand.nextInt(components.length)];
    }

    public String getRandomComponent(String... excluding) {
        List<String> excludedComponents = new ArrayList<>();
        for (String s : excluding) {
            if(s != null) {
                excludedComponents.add(s);
            }
        }
        return getRandomComponent(excludedComponents);
    }

    public String getRandomComponent(Collection<String> excluding) {
        Random rand = new Random();
        List<String> components = new LinkedList<>(this.components);
        components.removeAll(excluding);
        return components.get(rand.nextInt(components.size()));
    }

    public String addRandomNewVersion() {
        String versionName = "Version";
        int i = 1;
        while(this.versions.contains(versionName)){
            versionName = "Version" + i;
            i++;
        }
        this.versions.add(versionName);
        return versionName;
    }

    public String getRandomVersion() {
        Random rand = new Random();
        String[] versions = this.versions.toArray(new String[this.versions.size()]);
        return versions[rand.nextInt(versions.length)];
    }

    public String getRandomVersion(String... excluding) {
        List<String> excludedVersions = new ArrayList<>();
        for (String s : excluding) {
            if(s != null) {
                excludedVersions.add(s);
            }
        }
        return getRandomVersion(excludedVersions);
    }

    public String getRandomVersion(Collection<String> excluding) {
        Random rand = new Random();
        List<String> versions = new LinkedList<>(this.versions);
        versions.removeAll(excluding);
        return versions.get(rand.nextInt(versions.size()));
    }

    public void addBug(Bug newBug) {
        this.addedBugs.add(newBug);
    }

    public Bug getBug(Integer id) {
        for (Bug addedBug : addedBugs) {
            if(addedBug.getId() == id){
                return addedBug;
            }
        }
        return null;
    }
}
