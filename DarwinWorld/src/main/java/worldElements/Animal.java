package worldElements;

import components.AnimalInformation;
import components.Genome;
import components.MapDirection;
import components.Vector2d;

import java.util.Random;

public class Animal{
    private final AnimalInformation info;
    private final Genome genome;
    private int energy;
    private int daysSurvived=0;
    private MapDirection facing;
    private int numberOfGrasses=0;
    private int birtheDate=0;

    public Animal(AnimalInformation info) {
        Random random = new Random();
        this.genome = new Genome(info.genomeInfo());
        this.info=info;
        energy= info.startingEnergy();
        this.facing = MapDirection.NORTH;
    }

    public Animal(AnimalInformation info,MapDirection strongerParentFacing,Genome genome,int birtheDate) {
        this.genome = genome;
        this.info=info;
        energy= info.energyUsedByReproduction();// *2 ?
        facing=strongerParentFacing;
        this.birtheDate=birtheDate;
    }



    public void eatPlant(){
        this.energy+=info.energyProvidedByEating();
        numberOfGrasses++;
    }

    public boolean canReproduce(){
        return energy>=info.energyRequiredForReproduction();
    }

    public MapDirection getFacing(){
        return facing;
    }

    public void rotate(){
        facing=MapDirection.intToMoveDirection((facing.toInt()+genome.getInstruction())%8);
    }
    public void emergencyRotation(){
        facing=MapDirection.intToMoveDirection ((facing.toInt()+4)%8);
    }

    private void consumeEnergy(int energyQuantity){
        energy-=energyQuantity;
    }
    public void skipDay(){
        consumeEnergy(info.energyUsedToSurviveNextDay());
        daysSurvived++;
    }
    public void consumeEnergyToReproduce(){
        consumeEnergy(info.energyUsedByReproduction());
    }
    public Animal reproduce(Animal anotherAnimal){
        consumeEnergyToReproduce();
        anotherAnimal.consumeEnergy(info.energyUsedByReproduction());
        Genome childGenome = new Genome(info.genomeInfo(),this.genome,anotherAnimal.genome,energy,anotherAnimal.energy);
        return new Animal(info,this.facing,childGenome,birtheDate+daysSurvived);
    }

    public boolean isDead() {
        return energy <= 0;
    }
    public int getLifetime(){
        return daysSurvived;
    }
    public int getEnergy(){
        return energy;
    }

    public void getGeneIterator(){
        genome.getGeneIterator();
    }

//    public int deathDate(){
//        return birtheDate+daysSurvived;
//    }
//
}
