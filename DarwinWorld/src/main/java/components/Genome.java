package components;

import MapStatisticsAndInformations.GenomeInformation;

import java.util.*;

public class Genome {
    private final int len;
    private final int[] genes;
    private final GenomeInformation info;
    private int geneIterator;

    public Genome(GenomeInformation info) {
        this.info = info;
        this.len = info.genomeLength();
        this.genes = new int[len];
        this.geneIterator=new Random().nextInt(len);
        initRandomGenes();
    }

    public Genome(GenomeInformation info, Genome g1, Genome g2, int g1Energy, int g2Energy) {
        this.info = info;
        this.len = info.genomeLength();
        this.genes = new int[len];

        Random random = new Random();
        int borderGene = Math.round(len * g1Energy / (g1Energy + g2Energy));
        if (random.nextInt(1) == 1) { //left or right
            preformCombineGenes(g1, g2, borderGene);
        } else {
            preformCombineGenes(g2, g1, borderGene);
        }

        this.geneIterator=new Random().nextInt(len);
        performMutation();
    }

    private void preformCombineGenes(Genome g1, Genome g2, int borderGene) {
        for (int i = 0; i < len; i++) {
            genes[i] = (i < borderGene) ? g1.genes[i] : g2.genes[i];
        }
    }

    private void initRandomGenes() {
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            genes[i] = random.nextInt(8);
        }
    }

    private void performMutation() {
        Random random = new Random();
        ArrayList<Boolean> genesToMutate = generateIndexToMutation(random);
        mutateChoosenIndexes(genesToMutate);
    }


    private ArrayList<Boolean> generateIndexToMutation(Random random) {
        ArrayList<Boolean> genesToMutate = new ArrayList<>();
        int numberOfMutations = info.minMutation() + random.nextInt(info.maxMutation() - info.minMutation() + 1);
        for (int i = 0; i < len; i++) {
            genesToMutate.add((numberOfMutations > 0) ? Boolean.TRUE : Boolean.FALSE);
            numberOfMutations--;
        }
        Collections.shuffle(genesToMutate);
        return genesToMutate;
    }

    private void mutateChoosenIndexes(ArrayList<Boolean> genesToMutate) {
        for (int i = 0; i < len; i++) {
            if (genesToMutate.get(i) == Boolean.TRUE) {
                mutateOneGene(i);
            }
        }
    }

    private void mutateOneGene(int geneIndex) {
        Random random = new Random();
        if (info.slowEvolvingFlag()) {
            genes[geneIndex] = (random.nextInt(2) == 0) ? (genes[geneIndex] + 7) % 8 : (genes[geneIndex] + 1) % 8;
        } else {
            genes[geneIndex] = (genes[geneIndex] + 1 + random.nextInt(7)) % 8;
        }
    }

    public int getInstruction() {
        int res = genes[geneIterator];
        geneIterator = (geneIterator + 1) % len;
        return res;
    }

    public int getGeneIterator() {
        return geneIterator;
    }

    public int[] getGenes() {
        return genes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Genome genome)) return false;
        return len == genome.len && Arrays.equals(getGenes(), genome.getGenes()) && Objects.equals(info, genome.info);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(len, info);
        result = 31 * result + Arrays.hashCode(getGenes());
        return result;
    }
}
