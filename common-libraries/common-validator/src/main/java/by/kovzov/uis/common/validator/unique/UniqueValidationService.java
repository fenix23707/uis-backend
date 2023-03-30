package by.kovzov.uis.common.validator.unique;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

@Service
public interface UniqueValidationService {

    <T> void checkEntity(T entity, JpaSpecificationExecutor<T> jpaSpecificationExecutor);
}
