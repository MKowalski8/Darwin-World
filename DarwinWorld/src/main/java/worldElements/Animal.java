package worldElements;

import components.AnimalInformation;
import components.Genome;
import components.MapDirection;

import java.rmi.server.UID;
import java.util.*;

public class Animal{
    private final AnimalInformation info;
    private final Genome genome;
    private int energy;
    private int daysSurvived=0;
    private MapDirection facing;
    private int numberOfGrasses=0;
    private int birtheDate=0;
    private Animal leftParent = null;
    private Animal rightParent = null;
    private final List<Animal> descendants = new ArrayList<>();
    boolean wasCountedInGetNumberOfDescendants = false;
    private final UID id = new UID();
    private int numberOfChilds=0;

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
        Animal newborn= new Animal(info,this.facing,childGenome,birtheDate+daysSurvived);
        //acttualizing parents stats
        updateGenealogicalStats(anotherAnimal, newborn);
        return newborn;
    }

    private void updateGenealogicalStats(Animal anotherAnimal, Animal newborn) {
        this.descendants.add(newborn);
        anotherAnimal.descendants.add(newborn);
        newborn.leftParent=this;
        newborn.rightParent= anotherAnimal;
        this.numberOfChilds++;
        anotherAnimal.numberOfChilds++;
    }

    public boolean isDead() {
        return energy <= 0;
    }
    public int getLifeTime(){
        return daysSurvived;
    }
    public int getEnergy(){
        return energy;
    }


    public int getNumberOfUniqueDescendants() {
        // Reset the flag for each traversal
        resetWasCountedFlag();

        // Use a set to keep track of unique descendants
        Set<Animal> uniqueDescendants = new HashSet<>();

        // Perform DFS starting from the current object
        dfs(this, uniqueDescendants);

        // Return the count of unique descendants
        return uniqueDescendants.size()-1;
    }

    private void resetWasCountedFlag() {
        // Reset the flag for each traversal
        wasCountedInGetNumberOfDescendants = false;

        for (Animal descendant : descendants) {
            descendant.resetWasCountedFlag();
        }
    }


    private void dfs(Animal current, Set<Animal> uniqueDescendants) {
        // Mark the current object as visited
        current.wasCountedInGetNumberOfDescendants = true;

        // Add the current object to the set of unique descendants
        uniqueDescendants.add(current);

        // Recursively visit all unvisited descendants
        for (Animal descendant : current.descendants) {
            if (!descendant.wasCountedInGetNumberOfDescendants) {
                dfs(descendant, uniqueDescendants);
            }
        }
    }

    public void fixFamilyTree(){
        for (Animal potentialDescendant:descendants){
            if (!leftParent.descendants.contains(potentialDescendant)){
                leftParent.descendants.add(potentialDescendant);
            }
            if (!rightParent.descendants.contains(potentialDescendant)){
                rightParent.descendants.add(potentialDescendant);
            }
        }
        leftParent.descendants.remove(this);
        rightParent.descendants.remove(this);
    }

    public int getGeneIterator(){
        return genome.getGeneIterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Animal animal)) return false;
        return Objects.equals(id, animal.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    //    public int deathDate(){
//        return birtheDate+daysSurvived;
//    }
//

    public UID getId(){
        return id;
    }

    public int getNumberOfGrasses() {
        return numberOfGrasses;
    }
}
