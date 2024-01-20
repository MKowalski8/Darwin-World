package components;

import maps.MapCell;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Vector;

public class PlantGenerator {
    private final Boundary bounds;
    public PlantGenerator(Boundary bounds){
        this.bounds = bounds;
    }

    public void generatePlants(ArrayList<Vector2d> actualPlants , int growingPlantsNumber){
        int numberOfJunglePlants=(int)(growingPlantsNumber*0.8);
        int numberOfPlantsOutsideJungle=growingPlantsNumber-numberOfJunglePlants;
        ArrayList<Vector2d> jungleCandidates=new ArrayList<>();
        ArrayList<Vector2d> candidatesOutside=new ArrayList<>();

        prepareCandidatesToGrow(actualPlants, jungleCandidates, candidatesOutside);

        //choosePlantsToGrow
        if(numberOfJunglePlants>=jungleCandidates.size()){
            numberOfPlantsOutsideJungle+=(numberOfJunglePlants-jungleCandidates.size());
            actualPlants.addAll(jungleCandidates);
        }
        else{
            choosePlantsToGrow(numberOfJunglePlants,jungleCandidates,actualPlants);
        }

        if(numberOfPlantsOutsideJungle>=candidatesOutside.size()){
            actualPlants.addAll(candidatesOutside);
        }
        else{
            choosePlantsToGrow(numberOfPlantsOutsideJungle,candidatesOutside,actualPlants);
        }

    }

    private void prepareCandidatesToGrow(ArrayList<Vector2d> actualPlants, ArrayList<Vector2d> jungleCandidates, ArrayList<Vector2d> candidatesOutside) {
        for (int x=0;x<bounds.getWidth();x++){
            for (int y=0;y<bounds.getHeight();y++){

                Vector2d candidateToBecandidateToGrow = new Vector2d(x,y);

                if(!actualPlants.contains(candidateToBecandidateToGrow)){
                    if (y>=bounds.getLowerJoungleBound() && y< bounds.getUpperJoungleBound()){
                        jungleCandidates.add(candidateToBecandidateToGrow);
                    }
                    else{
                        candidatesOutside.add(candidateToBecandidateToGrow);
                    }
                }
            }
        }
    }

    private void choosePlantsToGrow(int numberOfPlantsToGrow,ArrayList<Vector2d> candidates,ArrayList<Vector2d> actualPlants){
        Collections.shuffle(candidates);
        for(int i=0;i<numberOfPlantsToGrow;i++){
            actualPlants.add(candidates.get(i));
        }
    }

}
