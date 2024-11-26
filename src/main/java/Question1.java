import java.util.ArrayList;
import java.util.List;

/**
 *  Your Name: Luke Clarke
 *  Class Group: SD2A
 */
public class Question1 {    // Interfaces
    public static void main(String[] args) {
        System.out.println("Question 1");

        ContainerManager manager = new ContainerManager();

        // Tests
        // Add containers
        manager.add(new Box(2, 3, 4, 5));
        manager.add(new Cylinder(5, 2, 3));
        manager.add(new Pyramid(3, 3, 1.5));

        // Display total weight and volume
        System.out.println("Total Weight: " + manager.totalWeight());
        System.out.println("Total Rectangular Volume: " + manager.totalRectangularVolume());

        // Retrieve and display all containers
        List<IMeasurableContainer> containers = manager.getAllContainers();
        for (IMeasurableContainer container : containers) {
            if (container instanceof Box) {
                Box box = (Box) container;
                System.out.println("Box - Length: " + box.getLength() + ", Width: " + box.getWidth() +
                        ", Depth: " + box.getDepth() + ", Weight: " + box.getWeight());
            } else if (container instanceof Cylinder) {
                Cylinder cylinder = (Cylinder) container;
                System.out.println("Cylinder - Height: " + cylinder.getHeight() + ", Diameter: " +
                        cylinder.getDiameter() + ", Weight: " + cylinder.getWeight());
            } else if (container instanceof Pyramid) {
                Pyramid pyramid = (Pyramid) container;
                System.out.println("Pyramid - Length: " + pyramid.getLength() +
                        ", SideLength: " + pyramid.getSideLength() + ", Weight: " + pyramid.getWeight());
            }
        }
    }
    public interface IMeasurableContainer {
        double weight();
        double rectangularVolume();
    }

    public static class ContainerManager {
        private List<IMeasurableContainer> containers = new ArrayList<>();

        public void add(IMeasurableContainer container) {
            containers.add(container);
        }

        public double totalWeight() {
            return containers.stream().mapToDouble(IMeasurableContainer::weight).sum();
        }

        public double totalRectangularVolume() {
            return containers.stream().mapToDouble(IMeasurableContainer::rectangularVolume).sum();
        }

        public void clearAll() {
            containers.clear();
        }

        public List<IMeasurableContainer> getAllContainers() {
            return new ArrayList<>(containers);
        }
    }

    public static class Pyramid implements IMeasurableContainer {
        private double length, sideLength, weight;

        public Pyramid(double length, double sideLength, double weight) {
            this.length = length;
            this.sideLength = sideLength;
            this.weight = weight;
        }

        @Override
        public double weight() {
            return this.weight;
        }

        @Override
        public double rectangularVolume() {
            return Math.pow(length, 3); // Bounding box: cube with all sides equal to length
        }

        public double getLength() {
            return length;
        }

        public double getSideLength() {
            return sideLength;
        }

        public double getWeight() {
            return weight;
        }
    }

    public static class Cylinder implements IMeasurableContainer {
        private double height, diameter, weight;

        public Cylinder(double height, double diameter, double weight) {
            this.height = height;
            this.diameter = diameter;
            this.weight = weight;
        }

        @Override
        public double weight() {
            return this.weight;
        }

        @Override
        public double rectangularVolume() {
            double radius = diameter / 2;
            return height * diameter * diameter; // Rectangular prism: height x diameter²
        }

        public double getHeight() {
            return height;
        }

        public double getDiameter() {
            return diameter;
        }

        public double getWeight() {
            return weight;
        }
    }

    public static class Box implements IMeasurableContainer {
        private double length, width, depth, weight;

        public Box(double length, double width, double depth, double weight) {
            this.length = length;
            this.width = width;
            this.depth = depth;
            this.weight = weight;
        }

        @Override
        public double weight() {
            return this.weight;
        }

        @Override
        public double rectangularVolume() {
            return length * width * depth;
        }

        public double getLength() {
            return length;
        }

        public double getWidth() {
            return width;
        }

        public double getDepth() {
            return depth;
        }

        public double getWeight() {
            return weight;
        }
    }

}


