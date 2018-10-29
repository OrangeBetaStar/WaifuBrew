package start.CustomObjects;

    public enum Origin {
        LEFT_TOP(0),
        MIDDLE_TOP(1),
        RIGHT_TOP(2),
        LEFT_CENTRE(3),
        MIDDLE_CENTRE(4),
        RIGHT_CENTRE(5),
        LEFT_BOTTOM(6),
        MIDDLE_BOTTOM(7),
        RIGHT_BOTTOM(8);

        public final static int length = Origin.values().length;

        private int value;

        private Origin(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }