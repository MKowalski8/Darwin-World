package components;

import MapStatisticsAndInformations.GenomeInformation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GenomeTest {

    private int numOfDifferences(int startingIndex, int finalIndex, int[] g1, int[] g2) {
        int res = 0;

        for (int i = startingIndex; i < finalIndex; i++) {
            if (g1[i] != g2[i]) {
                res++;
            }
        }

        return res;
    }

    @Test
    public void fullMutation() {
        //given
        GenomeInformation info = new GenomeInformation(100, 100, false, 100);
        Genome g1 = new Genome(info);
        Genome g2 = new Genome(info);

        //when
        Genome childGenome = new Genome(info, g1, g2, 100, 0);
        //then
        Assertions.assertTrue(numOfDifferences(0, 100, childGenome.getGenes(), g1.getGenes()) == 100 ||
                numOfDifferences(0, 100, childGenome.getGenes(), g2.getGenes()) == 100);

    }

    @Test
    public void noMutation() {
        //given
        GenomeInformation info = new GenomeInformation(0, 0, false, 100);
        Genome g1 = new Genome(info);
        Genome g2 = new Genome(info);

        //when
        Genome childGenome = new Genome(info, g1, g2, 100, 0);
        //then
        Assertions.assertTrue(numOfDifferences(0, 100, childGenome.getGenes(), g1.getGenes()) == 0 ||
                numOfDifferences(0, 100, childGenome.getGenes(), g2.getGenes()) == 0);

    }

    @Test
    public void splitGenes() {
        //given
        GenomeInformation info = new GenomeInformation(0, 0, false, 100);
        Genome g1 = new Genome(info);
        Genome g2 = new Genome(info);

        //when
        Genome childGenome = new Genome(info, g1, g2, 1, 1);
        //then
        Assertions.assertTrue((numOfDifferences(0, 50, childGenome.getGenes(), g1.getGenes()) == 0 &&
                numOfDifferences(50, 100, childGenome.getGenes(), g2.getGenes()) == 0)
                ||
                (numOfDifferences(0, 50, childGenome.getGenes(), g2.getGenes()) == 0 &&
                        numOfDifferences(50, 100, childGenome.getGenes(), g1.getGenes()) == 0)
        );

    }

    @Test
    public void checkGetGeneIterator(){
        //given
        GenomeInformation info = new GenomeInformation(4,4,true,5);
        Genome genome=new Genome(info);
        int indexFollower=genome.getGeneIterator()+1;//-1 because we will cal methon "getInstruction" before assertion

        //when
        for (int i =0; i<100;i++ ){
            genome.getInstruction();

            //then
            Assertions.assertEquals((indexFollower+i)% info.genomeLength(),genome.getGeneIterator());
        }

    }
}