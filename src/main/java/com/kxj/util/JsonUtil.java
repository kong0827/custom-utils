package com.kxj.util;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xiangjin.kong
 * @date 2021/11/17 14:46
 */
@Slf4j
@UtilityClass
public final class JsonUtil {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.registerModule(new ParameterNamesModule());
        OBJECT_MAPPER.registerModule(new Jdk8Module());
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        // 对象字段全部列入
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 取消默认转换timestamps形式
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        // 忽略空bean转json的错误
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        // 忽略在json字符串中存在,但是在java对象中不存在对应属性的情况
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static void main(String[] args) {
        System.out.println(JsonUtil.toJsonString(LocalDateTime.now()));

    }

    public static String toJsonString(Object object) {
        if (object == null) {
            return null;
        }
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("parse json string error ", e);
        }
        return null;
    }

    public static Map<String, Object> objectToMap(Object object) {
        return OBJECT_MAPPER.convertValue(object, Map.class);
    }

    public static <T> T parseObject(String str, Class<T> clazz) {
        if (str == null) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(str, clazz);
        } catch (IOException e) {
            log.error("parse string to object error", e);
        }
        return null;
    }

    public static <T> List<T> parseArray(String str, Class<T> clazz) {
        if (str == null) {
            return Collections.emptyList();
        }
        try {
            JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructParametricType(List.class, clazz);
            return OBJECT_MAPPER.readValue(str, javaType);
        } catch (IOException e) {
            log.error("parse string to object error", e);
        }
        return Collections.emptyList();
    }

    public static <K, V> Map<K, V> parseMap(String str, Class<K> keyClazz, Class<V> valueClazz) {
        if (str == null) {
            return Collections.emptyMap();
        }
        try {
            JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructParametricType(Map.class, keyClazz, valueClazz);
            return OBJECT_MAPPER.readValue(str, javaType);
        } catch (IOException e) {
            log.error("parse string to object error", e);
        }
        return Collections.emptyMap();
    }

    public static List<Map<String, Object>> parseArray(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, new TypeReference<List<Map<String, Object>>>() {

            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public static Map<String, Object> parseMap(String str) {
        return parseMap(str, String.class, Object.class);
    }

    public static <T> T mapToObject(Map map, Class<T> clazz) {
        return OBJECT_MAPPER.convertValue(map, clazz);
    }


    /**
     * 将List<Map<String,Object>>转换为List<T>
     *
     * @param maps
     * @param clazz
     * @return
     */
    public static <T> List<T> mapsToObjects(List<Map<String, Object>> maps, Class<T> clazz) {
        if (maps != null && maps.size() > 0) {
            return maps.stream().map(x -> OBJECT_MAPPER.convertValue(x, clazz)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
