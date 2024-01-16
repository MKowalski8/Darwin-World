package components;
import java.util.*;

public class Genome {
    private int len;
    private int[] genes;
    private final GenomeInformation info;
    private int geneIterator=0;
    public Genome(GenomeInformation info) {
        this.info=info;
        this.len = info.genomeLength();
        this.genes = new int[len];

        initRandomGenes();
    }
    public Genome(GenomeInformation info,Genome g1,Genome g2,int g1Energy, int g2Energy) {
        Random random=new Random();
        int borderGene=Math.round(len*g1Energy/(g1Energy+g2Energy));
        this.info=info;
        this.len = info.genomeLength();
        if(random.nextInt(1)==1){ //left or right
            for(int i =0;i<len;i++){
                genes[i]=(i<borderGene)?g1.genes[i]:g2.genes[i];
            }
        }
        else{
            if(random.nextInt(1)==1){
                for(int i =0;i<len;i++){
                    genes[i]=(i>borderGene)?g2.genes[i]:g1.genes[i];
                }
            }
        }

        performMutation();
    }
    private void initRandomGenes() {
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            genes[i] = random.nextInt(8);
        }
    }

    private void performMutation(){
        Random random = new Random();
        //generate number of mutaion
        ArrayList<Boolean> genesToMutate = new ArrayList<>();
        int numberOfMutaions= info.minMutation()+ random.nextInt(info.maxMutation()-info.minMutation()+1);
        for(int i=0; i<len;i++)
        {
            genesToMutate.add((numberOfMutaions>0) ? Boolean.TRUE:Boolean.FALSE);
            numberOfMutaions--;
        }
        Collections.shuffle(genesToMutate);

        //mutating genes

        for(int i=0; i<len;i++) {
            if( genesToMutate.get(i)==Boolean.TRUE){
                mutateOneGene(i);
            }

        }

    }
    private void mutateOneGene(int geneIndex){
        Random random = new Random();
        if (info.slowEvolvingFlag()){
            genes[geneIndex]+=(random.nextInt(2)==0)?-1:1;
        }
        else{
            genes[geneIndex]=(genes[geneIndex]+1+ random.nextInt(7))%8;
        }
    }

    public int getInstruction(){
        int res=genes[geneIterator];
        geneIterator=(geneIterator+1)%len;
        return res;
    }
}
