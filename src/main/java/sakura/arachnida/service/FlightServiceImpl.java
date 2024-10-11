package sakura.arachnida.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sakura.arachnida.models.Flight;
import sakura.arachnida.repository.FlightRepository;

import java.util.List;
import java.util.UUID;

@Service
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    @Autowired
    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public List<Flight> findAll() {
        return flightRepository.findAll();
    }

    @Override
    public Flight create(Flight flight) {
        return flightRepository.save(flight);
    }

    @Override
    public Flight update(Flight flight) {
        return flightRepository.save(flight);
    }

    @Override
    public Flight findById(Integer id) {
        return flightRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Integer id) {
        flightRepository.deleteById(id);
    }

    @Override
    public Page<Flight> findAllWithPagination(Pageable pageable) {
        return flightRepository.findAll(pageable);
    }

    @Override
    public Page<Flight> findAllWithPaginationAndFilters(Pageable pageable, String filterDeparturePlace, String filterArrivalPlace, Boolean isDeleted, String sortField, String sortDirection) {
        return flightRepository.findAllWithPaginationAndFilters(pageable, filterDeparturePlace, filterArrivalPlace, isDeleted, sortField, sortDirection);
    }

    @Override
    public void logicDelete(List<Integer> ids) {
        for (Integer id : ids) {
            Flight flight = findById(id);
            if (flight != null) {
                flight.setIsDeleted(!(flight.isDeleted()));
                flightRepository.save(flight);
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