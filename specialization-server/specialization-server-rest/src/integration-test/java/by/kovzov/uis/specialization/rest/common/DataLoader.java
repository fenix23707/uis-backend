package by.kovzov.uis.specialization.rest.common;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Component
@RequiredArgsConstructor
public class DataLoader {

    @Autowired
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public <T> List<T> loadJson(Class<T> tClass,String filePath) {
        File file = ResourceUtils.getFile("classpath:" + filePath);
        return objectMapper.readValue(file,
            objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, tClass));
    }
}
