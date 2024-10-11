package sakura.arachnida.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sakura.arachnida.models.Client;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    List<Client> findAll();

    Optional<Client> findById(Integer id);

    Client save(Client client);

    void deleteById(Integer id);
}