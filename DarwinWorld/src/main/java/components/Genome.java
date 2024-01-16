package components;
import java.util.*;

public class Genome {
    private int len;
    private int[] genes;
    private final int maxMutations;
    private final int minMutations;
    private final boolean slowFlag;
    public Genome(SimulationInformation info) {
        this.len = info.genomeLength();
        this.genes = new int[len];
        this.slowFlag = info.slowEvolvingFlag();
        this.maxMutations = info.maxMutation();
        this.minMutations = info.minMutation();
        initRandomGenes();
    }
    public Genome(SimulationInformation info,Genome g1,Genome g2,int g1Energy, int g2Energy) {
        int borderGene=len*g1Energy/(g1Energy+g2Energy);
        this.len = info.genomeLength();
//        this.genes = Arrays.copyOfRange(g1.getGenes(), 0, borderGene) ++ Arrays.copyOfRange(g2.getGenes(), borderGene, len);
        this.slowFlag = info.slowEvolvingFlag();
        this.maxMutations = info.maxMutation();
        this.minMutations = info.minMutation();
    }
    private void initRandomGenes() {
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            genes[i] = random.nextInt(8);
        }
    }

    private void mutate(){
        Random random = new Random();
        //generate number of mutaion
        ArrayList<Boolean> genesToMutate = new ArrayList<>();
        int numberOfMutaions= minMutations+ random.nextInt(maxMutations-minMutations+1);
        for(int i=0; i<len;i++)
        {
            genesToMutate.add((numberOfMutaions>0) ? Boolean.TRUE:Boolean.FALSE);
            numberOfMutaions--;
        }
        Collections.shuffle(genesToMutate);

        //mutating genes

        for(int i=0; i<len;i++) {
            if( genesToMutate.get(i)==Boolean.TRUE){
                this.genes[i]= (slowFlag) ? this.genes[i]+ (random.nextInt(3)-1)%8 : random.nextInt(8);
            }

        }

    }

    public int[] getGenes(){
        return genes;
    }
}