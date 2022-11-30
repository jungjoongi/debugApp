package com.weolbu.admin.common.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * ObjectHelper 유틸리티
 */
public class ObjectHelper {

    private static Logger LOGGER = LoggerFactory.getLogger(ObjectHelper.class);


    /**
     * Object를 문자열로 변환
     * @param object
     * @return
     */
    public static String convertObjectToString(Object object) {
        String resultString = "";
        resultString = convertObjectToString(object, true);

        return resultString;
    }

    /**
     * Object를 문자열로 반환 - 출력 포맷 지정
     * @param object
     * @param stringStyle
     * @return
     */
    public static String convertObjectToString(Object object, boolean stringStyle) {
        StringBuffer objectString = new StringBuffer();
        String fieldName = "";
        String fieldType = "";

        if (object == null) {
            return "";
        }

        // LOG는 제외
        if (object instanceof Logger || object instanceof org.apache.commons.logging.Log) {
            return "";
        }

        // Locale은 내부에 또 Locale이 있어 StackOverflow 에러가 나므로 그냥 객체의 toString 반환으로 종료
        if(object instanceof Locale) {
            return object.toString();
        }

        // Object 타입 확인
        if (isValue(object)) {
            objectString.append(object);
        } else if (object.getClass().isArray()) {

            boolean isFirst = true;
            objectString.append("Array@");

            int arrayLength = Array.getLength(object);

            objectString.append(arrayLength).append("(");

            for (int i = 0; i < arrayLength; i++) {
                if (!isFirst) {
                    objectString.append(", ");
                }
                if (!isFirst && stringStyle) {
                    objectString.append("\n");
                }
                objectString.append(convertObjectToString(Array.get(object, i), stringStyle)).append("\n");
                isFirst = false;
            }
            objectString.append(")");
        } else if (object instanceof Map) {
            Map<?, ?> map = (Map<?, ?>) object;
            Set<?> mapKeySet = map.keySet();
            boolean isFirst = true;

            objectString.append("Map@");
            objectString.append(map.size()).append("{");

            for (Object key : mapKeySet.toArray()) {
                Object obj = map.get(key);

                if (!isFirst) {
                    objectString.append(", ");
                }
                if (!isFirst && stringStyle) {
                    objectString.append("\n");
                }

                objectString.append(key).append("=");

                if (isValue(obj)) {
                    objectString.append(obj);
                } else {
                    objectString.append(convertObjectToString(obj, stringStyle)).append("\n");
                }

                isFirst = false;
            }
            objectString.append("}");
        } else if (object instanceof List) {
            objectString.append("List@");
            objectString.append(((List<?>) object).size()).append("(");

            boolean isFirst = true;
            for (Object obj : ((List<?>) object).toArray()) {
                if (!isFirst) {
                    objectString.append(", ");
                }
                if (!isFirst && stringStyle) {
                    objectString.append("\n");
                }
                if (isValue(obj)) {
                    objectString.append(obj);
                } else {
                    objectString.append(convertObjectToString(obj, stringStyle)).append("\n");
                }

                isFirst = false;
            }
            objectString.append(")");
        } else if(object.getClass().isEnum()) {
            objectString.append(object);
        } else {
            objectString.append("[");
            boolean isFirst = true;
            for (Field fld : object.getClass().getDeclaredFields()) {
                fieldName = fld.getName();
                fieldType = fld.getType().getName();
                Class<?> clazz = fld.getType();

                fld.setAccessible(true);
                if (!isFirst) {
                    objectString.append(", ");
                }
                if (!isFirst && stringStyle) {
                    objectString.append("\n");
                }

                try {
                    // 필드명 추출, 필드 객체 추출 - 타입 확인
                    Object fieldObject = fld.get(object);
                    objectString.append(fieldName).append("=");

                    if (clazz.isPrimitive() || fieldType.startsWith("java.lang.String")) {
                        objectString.append(fieldObject);
                    } else {
                        objectString.append(convertObjectToString(fieldObject, stringStyle));
                    }
                } catch (IllegalArgumentException e) {
                    LOGGER.debug("[ObjectHelper] IllegalArgumentException : {}", e.getMessage());
                } catch (IllegalAccessException e) {
                    LOGGER.debug("[ObjectHelper] IllegalAccessException : {}", e.getMessage());
                }

                isFirst = false;
            }
            objectString.append("]");
        }

        return objectString.toString();
    }

    /**
     * Object가 Primitive Wrapper Class, String 인지 확인
     * @param obj
     * @return
     */
    private static boolean isValue(Object obj) {
        return obj instanceof String || obj instanceof Integer || obj instanceof Character
                || obj instanceof Double || obj instanceof Byte || obj instanceof Long
                || obj instanceof Float || obj instanceof Short || obj instanceof Boolean;
    }

    /**
     * Object의 필드에 값을 설정
     * @param object
     * @param fieldName
     * @param fieldValue
     * @return
     * @throws IllegalAccessException
     */
    public static boolean setFiledValue(Object object, String fieldName, Object fieldValue) throws IllegalAccessException {

        if (object == null || StringUtils.isBlank(fieldName)) {
            return false;
        }

        try {

            for (Field field : object.getClass().getDeclaredFields()) {
                String objFieldName = field.getName();

                // TODO 값을 설정할 object의 필드 타입과 설정할 value 의 타입 확인 및 변환
                if (fieldName.equals(objFieldName)) {
                    field.setAccessible(true);

                    if (field.getType() == String.class) {
                        field.set(object, fieldValue.toString());
                    } else {
                        field.set(object, fieldValue);
                    }
                    return true;
                }
            }
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (IllegalAccessException e1) {
            throw e1;
        } catch (SecurityException e2) {
            throw e2;
        }

        return false;
    }

    /**
     * Object의 필드 값을 반환
     * @param object
     * @param fieldName
     * @return
     * @throws IllegalAccessException
     */
    public static Object getFiledValue(Object object, String fieldName) throws IllegalAccessException {

        if (object == null || StringUtils.isBlank(fieldName)) {
            return null;
        }

        try {

            for (Field field : object.getClass().getDeclaredFields()) {
                String objFieldName = field.getName();
//				String objFieldType = field.getType().getName();

                if (fieldName.equals(objFieldName)) {
                    field.setAccessible(true);
                    return field.get(object);
                }
            }
//			Field field = object.getClass().getField(fieldName); // public 필드만 조회 가능함
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (IllegalAccessException e1) {
            throw e1;
        } catch (SecurityException e2) {
            throw e2;
        }

        return null;
    }

    /**
     * Object => Map
     * @method convertObjectToMap
     * @param obj
     * @return
     */
    public static Map convertObjectToMap(Object obj) {

        try {

            Field[] fields = obj.getClass().getDeclaredFields();
            Map resultMap = new HashMap();
            for (int i=0; i<=fields.length-1; i++) {
                fields[i].setAccessible(true);
                resultMap.put(fields[i].getName(), fields[i].get(obj));
            }
            return resultMap;

        } catch (Exception e) {
            LOGGER.info("[ObjectHelper] Exception :{} " , e.getMessage() ) ;
        }

        return null;
    }

    /**
     * Map => Object
     * @method convertMapToObject
     * @param map
     * @param objClass
     * @return
     */
    @SuppressWarnings({ "rawtypes"})
    public static Object convertMapToObject(Map map, Object objClass) {

        try {
            String keyAttribute = null;
            String setMethodString = "set";
            String methodString = null;

            Iterator itr = map.keySet().iterator();

            while (itr.hasNext()) {
                keyAttribute = (String) itr.next();
                methodString = setMethodString + keyAttribute.substring(0, 1).toUpperCase() + keyAttribute.substring(1);

                Method [] methods = objClass.getClass().getDeclaredMethods();

                for (int i=0; i <= methods.length-1; i++) {
                    if (methodString.equals(methods[i].getName())) {
                        methods[i].invoke(objClass, map.get(keyAttribute));
                    }
                }
            }

        } catch (Exception e) {
            LOGGER.info("[ObjectHelper] Exception : {} " , e.getMessage() ) ;
        }
        return objClass;
    }


}

