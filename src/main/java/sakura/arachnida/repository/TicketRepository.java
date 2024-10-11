package sakura.arachnida.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sakura.arachnida.models.Ticket;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    // You can define custom query methods here if needed

    // Example: Find tickets by clientId
    List<Ticket> findByClientId(Integer clientId);

    // Example: Find tickets by flight ID
    List<Ticket> findByFlight_Id(Integer flightId);
}