package province.management.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import province.management.model.Customer;
import province.management.model.Province;
import province.management.repository.ProvinceRepository;
import province.management.service.ProvinceService;

import java.util.List;
import java.util.Optional;

public class ProvinceServiceImpl implements ProvinceService {

    @Autowired
    private ProvinceRepository provinceRepository;

    @Override
    public List<Province> findAll() {
        return (List<Province>) provinceRepository.findAll();
    }

    @Override
    public Province findById(Long id) {
        Optional<Province> optionalProvince = provinceRepository.findById(id);
        if (optionalProvince.isPresent()){
            return optionalProvince.get();
        }
        throw new RuntimeException("Not found province has which id is "+ id);
    }

    @Override
    public void save(Province province) {
        provinceRepository.save(province);
    }

    @Override
    public void remove(Long id) {
        provinceRepository.deleteById(id);
    }
}
