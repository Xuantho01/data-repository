package province.management.repository;

import org.springframework.data.repository.CrudRepository;
import province.management.model.Province;

public interface ProvinceRepository extends CrudRepository<Province, Long> {
}
