import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GeneticAlgorithm {
    private ArrayList<Chromosome> population; // Population with chromosomes
    private ArrayList<Integer> occurrences; // List with chromosomes (indices) based on fitness score

    private int N; // Number of Queens

    // Constructor
    GeneticAlgorithm(int N) {
        this.population = null;
        this.occurrences = null;

        this.N = N;
    }

    Chromosome run(int populationSize, double mutationProbability, int maxSteps, int minFitness) {
        // Initialize the population
        this.initializePopulation(populationSize);

        Random r = new Random();

        for(int step = 0; step < maxSteps; step++) {
            // Initialize the new generated population
            ArrayList<Chromosome> newPopulation = new ArrayList<>();

            for(int i = 0; i < populationSize / 2; i++) {
                // Choose two chromosomes from the population
                // Due to how fitnessBounds ArrayList is generated, the probability of
                // selecting a specific chromosome depends on its fitness score
                int xIndex = this.occurrences.get(r.nextInt(this.occurrences.size()));
                Chromosome xParent = this.population.get(xIndex);

                int yIndex = this.occurrences.get(r.nextInt(this.occurrences.size()));
                while(xIndex == yIndex) {
                    yIndex = this.occurrences.get(r.nextInt(this.occurrences.size()));
                }
                Chromosome yParent = this.population.get(yIndex);

                // Generate the children of the two chromosomes
                Chromosome[] children = this.reproduce(xParent, yParent);

                // Probability of mutating the children
                if(r.nextDouble() < mutationProbability)
                {
                    children[0].mutate();
                    children[1].mutate();
                }

                // ...and finally add them to the new population
                newPopulation.add(children[0]);
                newPopulation.add(children[1]);
            }
            this.population = new ArrayList<>(newPopulation);

            // Sort the population, so the one with the greater fitness is first
            this.population.sort(Collections.reverseOrder());

            // If the chromosome with the best fitness is acceptable we return it
            if(this.population.get(0).getFitness() >= minFitness) return this.population.get(0);

            // Update the occurrences list
            this.updateOccurrences();
        }

        return this.population.get(0);
    }

    // Initialize the population by creating random chromosomes
    void initializePopulation(int populationSize) {
        this.population = new ArrayList<>();

        for(int i = 0; i < populationSize; i++) {
            this.population.add(new Chromosome(N));
        }
        this.updateOccurrences();
    }

    // Updates the list that contains indexes of the chromosomes in the population ArrayList
    void updateOccurrences() {
        this.occurrences = new ArrayList<>();

        for(int i = 0; i < this.population.size(); i++) {
            for(int j = 0; j < this.population.get(i).getFitness(); j++) {
                // Each chromosome's index exists in the list as many times as its fitness score
                // By creating this list this way, and choosing a random index from it,
                // the greater the fitness score of a chromosome, the greater chance it will be chosen.
                this.occurrences.add(i);
            }
        }
    }

    // Reproduces two chromosomes and generates their children
    Chromosome[] reproduce(Chromosome x, Chromosome y) {
        Random r = new Random();

        // Randomly choose the intersection point
        int intersectionPoint = r.nextInt(N);
        int[] firstChild = new int[N];
        int[] secondChild = new int[N];

        // The first child has the left side of the x chromosome up to the intersection point...
        // The second child has the left side of the y chromosome up to the intersection point...
        for(int i = 0; i < intersectionPoint; i++) {
            firstChild[i] = x.getGenes()[i];
            secondChild[i] = y.getGenes()[i];
        }

        // ... the right side of the y chromosome after the intersection point (for the first)
        // ... the right side of the x chromosome after the intersection point (for the second)
        for(int i = intersectionPoint; i < firstChild.length; i++) {
            firstChild[i] = y.getGenes()[i];
            secondChild[i] = x.getGenes()[i];
        }

        return new Chromosome[] { new Chromosome(firstChild), new Chromosome(secondChild) };
    }
}
