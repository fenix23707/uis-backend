package by.kovzov.uis.academic.rest.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

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
