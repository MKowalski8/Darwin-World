package presenter;

import MapStatisticsAndInformations.AnimalInformation;
import MapStatisticsAndInformations.Boundary;
import javafx.scene.control.SpinnerValueFactory;
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
    private int plantNumber;
    private int plantGrowingDaily;
    private int startAnimalNumber;

    public FileConfiguration(StartPresenter startPresenter) {
        this.startPresenter = startPresenter;
    }

    public FileConfiguration(AnimalInformation animalInfo, Boundary bounds, String mapVariant, String toSave, int plantGrowingDaily, int plantNumber, int startAnimalNumbery) {
        this.animalInfo = animalInfo;
        this.bounds = bounds;
        this.mapVariant = mapVariant;
        this.toSave = toSave;
        this.plantGrowingDaily = plantGrowingDaily;
        this.plantNumber = plantNumber;
        this.startAnimalNumber = startAnimalNumbery;
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
                        plantNumber + "\n" +
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
                startPresenter.setMapHeight(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, scanner.nextInt()));
                startPresenter.setMapWidth(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, scanner.nextInt()));
                startPresenter.setPlantNumber(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 500, scanner.nextInt()));
                startPresenter.setPlantGrowingDaily(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 500, scanner.nextInt()));
                startPresenter.setEnergyFromPlant(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, scanner.nextInt()));
                startPresenter.setStartAnimalEnergy(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, scanner.nextInt()));
                startPresenter.setStartAnimalNumber(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 1000, scanner.nextInt()));
                startPresenter.setEnergyForReproduction(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, scanner.nextInt()));
                startPresenter.setEnergyUsedByReproduction(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, scanner.nextInt()));
                startPresenter.setEnergyUsedToSurviveNextDay(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, scanner.nextInt()));
                startPresenter.setMaxMutationNumber(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, scanner.nextInt()));
                startPresenter.setMinMutationNumber(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, scanner.nextInt()));
                startPresenter.setGenomeLength(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, scanner.nextInt()));
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
