package Enum;

public enum Etat {
        ACTIVE("Active"),
        FROZEN("Frozen"),
        CLOSED("Closed");

        private final String value;

        private Etat(String value) {
                this.value = value;
        }



    @Override
        public String toString() {
                return value;
        }
}
