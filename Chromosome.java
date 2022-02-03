import java.util.Random;

public class Chromosome implements Comparable<Chromosome> {
    private int N; // Number of Queens

    private int[] genes; // Each position shows the vertical position of a queen in the corresponding column

    private int fitness; // Fitness score of the chromosome

    // Constructs a randomly created chromosome
    Chromosome(int N) {
        this.N = N;
        this.genes = new int[N];
        Random r = new Random();

        for(int i = 0; i < this.genes.length; i++) {
            this.genes[i] = r.nextInt(N);
        }
        this.calculateFitness();
    }

    // Constructor for copying a chromosome
    Chromosome(int[] genes) {
        this.genes = new int[genes.length];

        for(int i = 0; i < this.genes.length; i++) {
            this.genes[i] = genes[i];
        }
        this.calculateFitness();
        this.N = genes.length;
    }

    // Calculates the fitness score of the chromosome as the number queen pairs that are NOT threatened
    // The maximum number of queen pairs that are NOT threatened is
    // (N-1) + (N-2) + ... + (N-N)
    // This is calculated in the main function
    void calculateFitness() {
        int nonThreats = 0;

        for(int i = 0; i < this.genes.length; i++) {
            for(int j = i+1; j < this.genes.length; j++) {
                if((this.genes[i] != this.genes[j]) && (Math.abs(i - j) != Math.abs(this.genes[i] - this.genes[j]))) {
                    nonThreats++;
                }
            }
        }

        this.fitness = nonThreats;
    }

    // Mutate by randomly changing the position of a queen
    void mutate() {
        Random r = new Random();
        this.genes[r.nextInt(N)] = r.nextInt(N);
        this.calculateFitness();
    }

    public int[] getGenes() {
        return this.genes;
    }

    public void setGenes(int[] genes) {
        this.genes = genes;
    }

    public int getFitness() {
        return this.fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public int getN() {
        return this.N;
    }

    // Function for printing the Chromosome
    void print() {
        System.out.println();
        System.out.print("Chromosome : |");
        for(int i = 0; i < this.genes.length; i++) {
            System.out.print(this.genes[i]);
            System.out.print("|");
        }
        System.out.print(", Fitness : ");
        System.out.println(this.fitness);

        System.out.println("------------------------------------");
        for(int i = 0; i < this.genes.length; i++) {
            for(int j=0; j < this.genes.length; j++) {
                if(this.genes[j] == i) {
                    System.out.print("|Q");
                } else {
                    System.out.print("| ");
                }
            }
            System.out.println("|");
        }
        System.out.println("------------------------------------");
    }

    // Overridden Function: sorting can be done according to fitness scores
    @Override
    public int compareTo(Chromosome x) {
        return this.fitness - x.fitness;
    }
}
