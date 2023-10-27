package racingcar.domain;

import camp.nextstep.edu.missionutils.Randoms;
import java.math.BigInteger;
import java.util.HashMap;
import racingcar.util.BlankValidator;

public class Racing {
    private static final String PLEASE_NOT_INPUT_BETWEEN_NUMBER_BLANK = "숫자사이에 공백을 입력하지 마세요";
    private static final String PLEASE_INPUT_ONLY_NUMBER = "자연수만 입력해주세요";
    private static final String PLEASE_INPUT_CORRECT_RANGE = "숫자가 너무 크거나 작습니다";
    private static final String PLEASE_INPUT_NATURAL_NUMBER = "자연수를 입력해주세요";
    private final BlankValidator blankValidator;
    private final Cars cars;
    private long attempCount;

    public Racing(Cars cars, BlankValidator blankValidator) {
        this.cars = cars;
        this.blankValidator = blankValidator;
    }

    public void validateAttemptCount(String attemptCount) throws IllegalArgumentException {
        blankValidator.isEmpty(attemptCount);

        String trimmedCount = trimSpaces(attemptCount);
        checkForInternalSpaces(trimmedCount);
        long convertedCount = convertToLong(trimmedCount);
        validatePositive(convertedCount);
        this.attempCount = convertedCount;
    }

    private String trimSpaces(String input) {
        return input.trim();
    }

    private void checkForInternalSpaces(String input) throws IllegalArgumentException {
        if (input.contains(" ")) {
            throw new IllegalArgumentException(PLEASE_NOT_INPUT_BETWEEN_NUMBER_BLANK);
        }
    }

    private BigInteger convertToBigInteger(String input) throws IllegalArgumentException {
        try {
            return new BigInteger(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(PLEASE_INPUT_ONLY_NUMBER, e);
        }
    }

    private void validateRange(BigInteger bigIntValue) throws IllegalArgumentException {
        if (bigIntValue.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0
                || bigIntValue.compareTo(BigInteger.valueOf(Long.MIN_VALUE)) < 0) {
            throw new IllegalArgumentException(PLEASE_INPUT_CORRECT_RANGE);
        }
    }

    private long convertToLong(String input) throws IllegalArgumentException {
        BigInteger bigIntValue = convertToBigInteger(input);
        validateRange(bigIntValue);
        return bigIntValue.longValue();
    }


    private void validatePositive(long number) throws IllegalArgumentException {
        if (number <= 0) {
            throw new IllegalArgumentException(PLEASE_INPUT_NATURAL_NUMBER);
        }
    }

    public void race() {
        HashMap<String, Integer> carPositions = cars.getCars();
        carPositions.forEach((car, position) -> {
            if (isMovable()) {
                cars.moveCar(car);
            }
        });
    }

    private boolean isMovable() {
        return Randoms.pickNumberInRange(0, 9) > 3;
    }

    public long getAttempCount() {
        return attempCount;
    }

}
