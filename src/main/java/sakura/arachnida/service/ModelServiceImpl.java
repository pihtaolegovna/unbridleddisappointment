package sakura.arachnida.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sakura.arachnida.models.Model;
import sakura.arachnida.repository.ModelRepository;

import java.util.List;
import java.util.UUID;

@Service
public class ModelServiceImpl implements ModelService {

    private final ModelRepository modelRepository;

    @Autowired
    public ModelServiceImpl(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    @Override
    public List<Model> findAll() {
        return modelRepository.findAll();
    }

    @Override
    public Model create(Model model) {
        return modelRepository.save(model);
    }

    @Override
    public Model update(Model model) {
        return modelRepository.save(model);
    }

    @Override
    public Model findById(Integer id) {
        return modelRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Integer id) {
        modelRepository.deleteById(id);
    }

    @Override
    public Page<Model> findAllWithPagination(Pageable pageable) {
        return modelRepository.findAll(pageable);
    }

    @Override
    public Page<Model> findAllWithPaginationAndFilters(Pageable pageable, String filterName, Boolean isDeleted, String sortField, String sortDirection) {
        return modelRepository.findAllWithPaginationAndFilters(pageable, filterName, isDeleted, sortField, sortDirection);
    }

    @Override
    public void logicDelete(List<Integer> ids) {
        for (Integer id : ids) {
            Model model = findById(id);
            if (model != null) {
                model.setIsDeleted(!(model.isDeleted()));
                modelRepository.save(model);
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