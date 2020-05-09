package province.management.service;

import province.management.model.Customer;
import province.management.model.Province;

import java.util.List;
import java.util.Optional;

public interface ProvinceService {
    List<Province> findAll();

    Province findById(Long id);

    void save(Province province);

    void remove(Long id);
}
