package ru.myastrebov.model;

/**
 * Execution order state
 *
 * @author Maxim
 */
public enum OrderState {
    WAIT(1), PROCESSED(2), READY(3);

    private final Integer dbIndex;

    OrderState(int dbIndex) {
        this.dbIndex = dbIndex;
    }

    public Integer getDbIndex() {
        return dbIndex;
    }

    public static OrderState getByDbIndex(Integer dataBaseIndex) {
        if (dataBaseIndex == null) {
            return null;
        }
        for (OrderState orderState : values()) {
            if (orderState.getDbIndex().equals(dataBaseIndex)) {
                return orderState;
            }
        }
        throw new IllegalArgumentException("Does not supported id " + dataBaseIndex + " for elements of " + OrderState.class.getSimpleName());
    }
}
