package com.shuimin.jtiny.model;

import java.io.InputStream;


final public class ModelField {

    protected ModelField(int t, String n) {
        type = t;
        name = n;
    }

    public final int type;
    public final String name;
    public Object value = null;

    public ModelField val(Object v) {
        value = v;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof ModelField)
            && (type == ((ModelField) obj).type
            && value.equals(((ModelField) obj).value));

    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }


    public static final int STR_UUID = -2;//varchar(64)
    public static final int STR_SHORT = -1; // varchar(20)
    public static final int STR_LONG = 2;//varchar (2048)
    public static final int STR_MED = 1; //varchar (256)
    public static final int TEXT = 0; // TEXT
    public static final int IMG = -6; //BLOB
    public static final int IMG_BASE64 = 3; //TEXT
    public static final int BLOB = 4; //BLOB
    public static final int NUM_DOUBLE = 5; //float(20,2)
    public static final int NUM_INT = 7; // int(11)
    public static final int TIME = 12; // bigInt
    public static final int STATUS = 13;//int (2)
    public static final int TYPE = 14;//int (2)
    public static final int BOOL = 15; //int (1)

    public static Class innerType(int type) {
        switch (type) {
            case STR_UUID:
            case STR_SHORT:
            case STR_MED:
            case STR_LONG:
            case IMG_BASE64:
            case TEXT:
                return String.class;
            case IMG:
            case BLOB:
                return InputStream.class;
            case STATUS:
            case TYPE:
            case BOOL:
            case NUM_INT:
                return Integer.class;
            case TIME:
                return Long.class;
            case NUM_DOUBLE:
                return Double.class;
            default:
                return String.class;
        }
    }

    public static String sqlType(int type) {
        switch (type) {
            case STR_UUID:
                return "VARCHAR(64)";
            case STR_SHORT:
                return "VARCHAR(20)";
            case STR_MED:
                return "VARCHAR(256)";
            case STR_LONG:
                return "VARCHAR(2048)";

            case IMG_BASE64:
            case TEXT:
                return "TEXT";
            case IMG:
            case BLOB:
                return "BLOB";
            case STATUS:
            case TYPE:
                return "INT(2)";

            case BOOL:
                return "INT(1)";
            case TIME:
                return "BIGINT(20)";
            case NUM_DOUBLE:
                return "FLOAT(20,2)";
            case NUM_INT:
                return "INT(11)";
            default:
                return "VARCHAR(1024)";
        }
    }
}
