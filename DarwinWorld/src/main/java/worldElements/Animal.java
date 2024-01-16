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

    public Animal(AnimalInformation info) {
        Random random =new Random();
        this.genome = new Genome(info.genomeInfo());
        this.info=info;
        energy= info.startingEnergy();
    }

    public Animal(AnimalInformation info,MapDirection strongerParentFacing,Genome genome) {
        this.genome = genome;
        this.info=info;
        energy= info.energyUsedByReproduction();// *2 ?
        facing=strongerParentFacing;
    }
    public Animal reproduce(Animal anotherAnimal){
        Genome childGenome = new Genome(info.genomeInfo(),this.genome,anotherAnimal.genome,energy,anotherAnimal.energy);
        return new Animal(info,this.facing,childGenome);
    }


    public void eatPlant(){
        this.energy+=info.energyProvidedByEating();
        numberOfGrasses++;
    }
    public void consumeEnergy(int energyQuantity){
        energy-=energyQuantity;
    }
    public boolean canReproduce(){
        return energy>=info.energyRequiredForReproduction();
    }

    public void rotate(){
        facing=MapDirection.intToMoveDirection (genome.getInstruction()+facing.toInt());
    }
    public MapDirection getFacing(){
        return facing;
    }

    public MapDirection moveTo(){
        return MapDirection.intToMoveDirection(genome.getInstruction());
    }
    public void emergencyRotation(){
        facing=MapDirection.intToMoveDirection ((facing.toInt()+4)%8);
    }

    public void skipDay(){
        consumeEnergy(info.energyUsedToSurviveNextDay());
        daysSurvived++;
    }


}
