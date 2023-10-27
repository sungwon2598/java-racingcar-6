package racingcar.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import racingcar.util.BlankValidator;

public class Cars {
    private static final String PLEASE_INPUT_CAR_NAME_LENGTH_5 = "의 이름 5자를 초과했습니다";
    private static final String PLEASE_INPUT_NOT_DUPLICATE_CAR_NAMES = "중복된 자동차 이름이 있습니다.";
    private static final int CAR_NAME_LENGTH_LIMIT = 5;
    private static final int CAR_INIT_COUNT = 0;
    private final BlankValidator blankValidator;
    private final HashMap<String, Integer> cars = new HashMap<>();

    public Cars(BlankValidator blankValidator) {
        this.blankValidator = blankValidator;
    }

    public void addCars(String carNames) {
        blankValidator.isEmpty(carNames);

        carNames = removeSpaces(carNames);
        List<String> carNameList = splitByComma(carNames);

        validateNameLength(carNameList);
        validateUniqueNames(carNameList);
        initializeCars(carNameList);
    }

    private String removeSpaces(String carNames) {
        return carNames.replaceAll(" ", "");
    }

    private List<String> splitByComma(String carNames) {
        return Arrays.asList(carNames.split(","));
    }

    private void validateNameLength(List<String> carNameList) {
        carNameList.stream()
                .filter(name -> name.length() > CAR_NAME_LENGTH_LIMIT)
                .findFirst()
                .ifPresent(name -> {
                    throw new IllegalArgumentException(name + PLEASE_INPUT_CAR_NAME_LENGTH_5);
                });
    }

    private void validateUniqueNames(List<String> carNameList) {
        HashSet<String> uniqueNames = new HashSet<>(carNameList);
        if (uniqueNames.size() < carNameList.size()) {
            throw new IllegalArgumentException(PLEASE_INPUT_NOT_DUPLICATE_CAR_NAMES);
        }
    }

    private void initializeCars(List<String> carNameList) {
        for (String name : carNameList) {
            cars.put(name, CAR_INIT_COUNT);
        }
    }

    public HashMap<String, Integer> getCars() {
        return this.cars;
    }

    public void moveCar(String carName) {
        Integer position = cars.get(carName);
        if (position != null) {
            cars.put(carName, position + 1);
        }
    }

    public ArrayList<String> determineFinalWinner() {
        int highestPosition = this.cars.values().stream()
                .max(Integer::compareTo)
                .orElse(-1);

        return this.cars.entrySet().stream()
                .filter(entry -> entry.getValue() == highestPosition)
                .map(Map.Entry::getKey)
                .collect(Collectors.toCollection(ArrayList::new));
    }

}
