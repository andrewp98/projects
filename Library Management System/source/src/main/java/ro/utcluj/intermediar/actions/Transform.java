package ro.utcluj.intermediar.actions;

import ro.utcluj.intermediar.RequestMessage;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Transform {

    public static String serialize(Object o) {
        String result = "";
        try {
            //get the class type of the object
            Class c = o.getClass();

            //get the fields of that class
            Field[] fields = c.getDeclaredFields();

            for (Field f : fields) {
                //set accessibility to true (such that they can be read even if private)
                f.setAccessible(true);
                //append the field to the result
                result += f.get(o) + "#";
                f.setAccessible(false);
            }
        } catch (IllegalAccessException e) {
//            LOGGER.error("", e);
        }

        return result;
    }

    public static RequestMessage desReqM(String msg){
        String[] objInfo = msg.split("#");
        RequestMessage rq = new RequestMessage();
        ArrayList<String> rqList = new ArrayList<>();

        rq.setRequestName(objInfo[0]);
        rq.setModelName(objInfo[1]);
        for(int i = 2; i < objInfo.length; i++){
            String aux = objInfo[i].replaceAll("\\[|\\]", "");
            String[] auxSplit = aux.split(",");
            for(String s : auxSplit){
                rqList.add(s.replaceAll("\\s+",""));
            }

        }
        rq.setArgs(rqList);

        return rq;
    }

    /**
     * Uses reflection to deserialize an object from String to the specified class.
     *
     * @param m     the serialized object
     * @param clazz the class into which to deserialize
     * @return deserialized object, instance of the Class clazz
     */
    public static <T> T deserialize(String m, Class<T> clazz) {
        //split the serialized String by the # separator
        String[] objInfo = m.split("#");

        try {
            //create a new instance of clazz Class
            T result = clazz.newInstance();
            //get the declared fields of that class
            Field[] fields = clazz.getDeclaredFields();

            for (int i = 0; i < objInfo.length && i < fields.length; i++) {
                fields[i].setAccessible(true);
                //set each field with values from the serialized String
                fields[i].set(result, toObject(fields[i].getType(), objInfo[i]));
                fields[i].setAccessible(false);
            }

            return result;
        } catch (InstantiationException | IllegalAccessException e) {
//            LOGGER.error("", e);
            System.out.println(e.getMessage());
        }

        return null;
    }

    private static Object toObject(Type type, String value) {
        if (Boolean.TYPE.equals(type)) {
            return Boolean.parseBoolean(value);
        }
        if (Byte.TYPE.equals(type)) {
            return Byte.parseByte(value);
        }
        if (Short.TYPE.equals(type)) {
            return Short.parseShort(value);
        }
        if (Integer.TYPE.equals(type)) {
            return Integer.parseInt(value);
        }
        if (Long.TYPE.equals(type)) {
            return Long.parseLong(value);
        }
        if (Float.TYPE.equals(type)) {
            return Float.parseFloat(value);
        }
        if (Double.TYPE.equals(type)) {
            return Double.parseDouble(value);
        }

        return value;
    }
}
