package tables;

import operands.Literal;

import java.util.Collection;
import java.util.HashMap;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase II : SIC Assembler
 * Created by Bishoy N. Gendy (programajor) on Tuesday 25/04/2017.
 */
public class LiteralTableSingleton implements LiteralTable {

    private static LiteralTableSingleton instance;

    private HashMap<String, LiteralData> map;

    private LiteralTableSingleton() {
        this.map = new HashMap<>();
    }

    @Override
    public void addLiteral(String literalName, String literalValue, int location) {
        this.map.put(literalName, new LiteralData(literalValue, location));
    }

    @Override
    public LiteralData addLiteral(String literalName, String literalValue) {
        LiteralData literalData = new LiteralData(literalName, literalValue);
        this.map.put(literalName, literalData);
        return literalData;
    }

    @Override
    public boolean exists(String literalName) {
        return this.map.containsKey(literalName);
    }

    @Override
    public int getLiteralLocation(String literalName) {
        return this.map.get(literalName).getLocation();
    }

    @Override
    public void setLiteralLocation(String literalName, int location) {
        this.map.get(literalName).setLocation(location);
    }

    @Override
    public int getLiteralsTotalLength() {
        int ret = 0;
        Collection<LiteralData> set = map.values();
        for(LiteralData literalData : set) {
            ret += literalData.getLength();
        }
        return ret;
    }

    @Override
    public void resets() {
        this.map.clear();
    }

    public static LiteralTable getInstance() {
        if(instance == null) {
            instance = new LiteralTableSingleton();
        }
        return instance;
    }

    public class LiteralData {

        private String literalName;
        private String value;
        private int length;
        private int location;
        private Literal literal;

        /**
         * Getter for property 'literal'.
         *
         * @return Value for property 'literal'.
         */
        public Literal getLiteral() {
            return literal;
        }

        /**
         * Setter for property 'literal'.
         *
         * @param literal Value to set for property 'literal'.
         */
        public void setLiteral(Literal literal) {
            this.literal = literal;
        }

        public int getLength() {
            return length;
        }

        LiteralData(String literalName, int location) {
            this.literalName = literalName;
            this.location = location;
        }

        LiteralData(String literalName, String value) {
            this.literalName = literalName;
            this.value = value;
            this.length = (int) Math.ceil(value.length() / 2);
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Getter for property 'literalName'.
         * @return Value for property 'literalName'.
         */
        public String getLiteralName() {
            return literalName;
        }

        /**
         * Setter for property 'literalValue'.
         * @param literalName Value to set for property 'literalValue'.
         */
        public void setLiteralName(String literalName) {
            this.literalName = literalName;
        }

        /**
         * Getter for property 'location'.
         * @return Value for property 'location'.
         */
        int getLocation() {
            return location;
        }

        /**
         * Setter for property 'location'.
         *
         * @param location Value to set for property 'location'.
         */
        public void setLocation(int location) {
            this.location = location;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            LiteralData that = (LiteralData) o;

            return getValue().equals(that.getValue());
        }

        @Override
        public int hashCode() {
            return getValue().hashCode();
        }
    }

}
