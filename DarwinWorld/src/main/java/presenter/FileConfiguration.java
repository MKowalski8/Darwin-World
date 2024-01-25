package presenter;

import MapStatisticsAndInformations.AnimalInformation;
import MapStatisticsAndInformations.Boundary;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;

public class FileConfiguration {

    private StartPresenter startPresenter;

    private AnimalInformation animalInfo;

    private Boundary bounds;
    private String mapVariant;
    private String toSave;
    private int startPlantNumber;
    private int plantGrowingDaily;
    private int startAnimalNumber;

    public FileConfiguration(StartPresenter startPresenter) {
        this.startPresenter = startPresenter;
    }

    public FileConfiguration(AnimalInformation animalInfo, Boundary bounds, String mapVariant, String toSave, int startPlantNumber, int plantGrowingDaily, int startAnimalNumber) {
        this.animalInfo = animalInfo;
        this.bounds = bounds;
        this.mapVariant = mapVariant;
        this.toSave = toSave;
        this.plantGrowingDaily = plantGrowingDaily;
        this.startPlantNumber = startPlantNumber;
        this.startAnimalNumber = startAnimalNumber;
    }

    public void saveActualConfiguration() {
        Optional<File> file = saveConfigurationFile();
        if (file.isPresent()) {
            try (FileWriter writer = new FileWriter(file.get(), false)) {
                writer.write(toSave + "\n" +
                        mapVariant + "\n" +
                        animalInfo.genomeInfo().slowEvolvingFlag() + "\n" +
                        bounds.getHeight() + "\n" +
                        bounds.getWidth() + "\n" +
                        startPlantNumber + "\n" +
                        plantGrowingDaily + "\n" +
                        animalInfo.energyProvidedByEating() + "\n" +
                        animalInfo.startingEnergy() + "\n" +
                        startAnimalNumber + "\n" +
                        animalInfo.energyRequiredForReproduction() + "\n" +
                        animalInfo.energyUsedByReproduction() + "\n" +
                        animalInfo.energyUsedToSurviveNextDay() + "\n" +
                        animalInfo.genomeInfo().maxMutation() + "\n" +
                        animalInfo.genomeInfo().minMutation() + "\n" +
                        animalInfo.genomeInfo().genomeLength());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void loadSavedConfiguration() {
        Optional<File> file = loadConfigurationFile();

        if (file.isPresent()) {
            try (Scanner scanner = new Scanner(file.get())) {
                startPresenter.setToSave(scanner.nextLine());
                startPresenter.setMapVariant(scanner.nextLine());
                startPresenter.setEvolutionVariant(scanner.nextBoolean());
                startPresenter.setMapHeight(scanner.nextInt());
                startPresenter.setMapWidth(scanner.nextInt());
                startPresenter.setStartPlantNumber(scanner.nextInt());
                startPresenter.setPlantGrowingDaily(scanner.nextInt());
                startPresenter.setEnergyFromPlant(scanner.nextInt());
                startPresenter.setStartAnimalEnergy(scanner.nextInt());
                startPresenter.setStartAnimalNumber(scanner.nextInt());
                startPresenter.setEnergyForReproduction(scanner.nextInt());
                startPresenter.setEnergyUsedByReproduction(scanner.nextInt());
                startPresenter.setEnergyUsedToSurviveNextDay(scanner.nextInt());
                startPresenter.setMaxMutationNumber(scanner.nextInt());
                startPresenter.setMinMutationNumber(scanner.nextInt());
                startPresenter.setGenomeLength(scanner.nextInt());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private Optional<File> loadConfigurationFile() {
        FileChooser fileChooser = configureFileChooser();
        ;
        return Optional.ofNullable(fileChooser.showOpenDialog(new Stage()));
    }

    private Optional<File> saveConfigurationFile() {
        FileChooser fileChooser = configureFileChooser();
        return Optional.ofNullable(fileChooser.showSaveDialog(new Stage()));
    }

    private static FileChooser configureFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Conf file", "*.conf"));
        return fileChooser;
    }
}
