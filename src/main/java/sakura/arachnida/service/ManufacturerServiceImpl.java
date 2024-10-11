package sakura.arachnida.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sakura.arachnida.models.Manufacturer;
import sakura.arachnida.repository.ManufacturerRepository;

import java.util.List;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    @Autowired
    public ManufacturerServiceImpl(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    public List<Manufacturer> findAll() {
        return manufacturerRepository.findAll();
    }

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        return manufacturerRepository.save(manufacturer);
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        return manufacturerRepository.save(manufacturer);
    }

    @Override
    public Manufacturer findById(Integer id) {
        return manufacturerRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Integer id) {
        manufacturerRepository.deleteById(id);
    }

    @Override
    public Page<Manufacturer> findAllWithPagination(Pageable pageable) {
        return manufacturerRepository.findAll(pageable);
    }

    @Override
    public Page<Manufacturer> findAllWithPaginationAndFilters(Pageable pageable, String filterName, Boolean isDeleted, String sortField, String sortDirection) {
        return manufacturerRepository.findAllWithPaginationAndFilters(pageable, filterName, isDeleted, sortField, sortDirection);
    }

    @Override
    public void logicDelete(List<Integer> ids) {
        for (Integer id : ids) {
            Manufacturer manufacturer = findById(id);

            if (manufacturer != null) {
                // Переключаем значение isDeleted: если true, то станет false, и наоборот
                manufacturer.setIsDeleted(!manufacturer.isDeleted());
                manufacturerRepository.save(manufacturer);
            }
        }
    }

    @Override
    public void delete(List<Integer> ids) {
        for (Integer id : ids) {
            deleteById(id);
        }
    }
}