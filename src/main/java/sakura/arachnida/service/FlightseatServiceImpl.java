package sakura.arachnida.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sakura.arachnida.models.Flightseat;
import sakura.arachnida.repository.FlightseatRepository;

import java.util.List;
import java.util.UUID;

@Service
public class FlightseatServiceImpl implements FlightseatService {

    private final FlightseatRepository flightseatRepository;

    @Autowired
    public FlightseatServiceImpl(FlightseatRepository flightseatRepository) {
        this.flightseatRepository = flightseatRepository;
    }

    @Override
    public List<Flightseat> findAll() {
        return flightseatRepository.findAll();
    }

    @Override
    public Flightseat create(Flightseat flightseat) {
        return flightseatRepository.save(flightseat);
    }

    @Override
    public Flightseat update(Flightseat flightSeat) {
        // Assuming you already have the ID in the flightSeat object
        if (flightseatRepository.existsById(flightSeat.getId())) {
            flightseatRepository.save(flightSeat); // This will update the existing flight seat
        } else {
            throw new EntityNotFoundException("FlightSeat not found for ID: " + flightSeat.getId());
        }
        return flightSeat;
    }


    @Override
    public Flightseat findById(Integer id) {
        return flightseatRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Integer id) {
        flightseatRepository.deleteById(id);
    }

    @Override
    public Page<Flightseat> findAllWithPagination(Pageable pageable) {
        return flightseatRepository.findAll(pageable);
    }

    @Override
    public Page<Flightseat> findAllWithPaginationAndFilters(Pageable pageable, String filterStatus, Double filterPrice, Boolean isDeleted, String sortField, String sortDirection) {
        return flightseatRepository.findAllWithPaginationAndFilters(pageable, filterStatus, filterPrice, isDeleted, sortField, sortDirection);
    }

    @Override
    public void logicDelete(List<Integer> ids) {
        for (Integer id : ids) {
            Flightseat flightseat = findById(id);
            if (flightseat != null) {
                flightseat.setIsDeleted(!(flightseat.isDeleted()));
                flightseatRepository.save(flightseat);
            }
        }
    }

    @Override
    public void delete(List<Integer> ids) {
        for (Integer id : ids) {
            deleteById(id);
        }
    }

    @Override
    public void restore(Long id) {
        Flightseat flightseat = findById(Math.toIntExact(id));
        if (flightseat != null) {
            flightseat.setIsDeleted(!(flightseat.isDeleted()));
            flightseatRepository.save(flightseat);
        }
    }
}