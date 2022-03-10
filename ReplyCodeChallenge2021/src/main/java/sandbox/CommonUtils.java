package sandbox;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class CommonUtils {
    public static final Integer NOT_INIT_INT_VALUE = Integer.MIN_VALUE;

    @SafeVarargs
    public static <T> Object[] asObjectsArray(T... a) {
        Object[] objects = new Object[a.length];
        int i = 0;
        for(T elem : a) {
            objects[i++] = elem;
        }
        return objects;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class IntegerWrapper {
        @NotNull
        private Integer value;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            IntegerWrapper that = (IntegerWrapper) o;
            return value.equals(that.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        @Override
        public String toString() {
            return value.toString();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class FloatWrapper {
        @NotNull
        private Float value;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            FloatWrapper that = (FloatWrapper) o;
            return value.equals(that.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        @Override
        public String toString() {
            return value.toString();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class DoubleWrapper {
        @NotNull
        private Double value;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DoubleWrapper that = (DoubleWrapper) o;
            return value.equals(that.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        @Override
        public String toString() {
            return value.toString();
        }
    }

}
