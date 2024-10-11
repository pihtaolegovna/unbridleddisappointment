package sakura.arachnida.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sakura.arachnida.models.Ticket;
import sakura.arachnida.repository.TicketRepository;
import sakura.arachnida.service.TicketService;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    @Override
    public Ticket create(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket update(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket findById(Integer id) {
        return ticketRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Integer id) {
        ticketRepository.deleteById(id);
    }

    @Override
    public Page<Ticket> findAllWithPagination(Pageable pageable) {
        return ticketRepository.findAll(pageable);
    }

    @Override
    public Page<Ticket> findAllWithPaginationAndFilters(int page, int size, String sortField, String sortDirection, String filterStatus, Double filterPrice, Boolean isDeleted) {
        Pageable pageable = PageRequest.of(page, size);
        // Логика фильтрации по полям
        return ticketRepository.findAll(pageable);
    }

    @Override
    public void logicDelete(List<Integer> ids) {
        for (Integer id : ids) {
            Ticket ticket = findById(id);
            if (ticket != null) {
                ticket.setIsDeleted(true);
                update(ticket);
            }
        }
    }

    @Override
    public void restore(List<Integer> ids) {
        for (Integer id : ids) {
            Ticket ticket = findById(id);
            if (ticket != null) {
                ticket.setIsDeleted(false);
                update(ticket);
            }
        }
    }
}