package sakura.arachnida.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sakura.arachnida.models.Client;

import java.util.List;
import java.util.UUID;

public interface ClientService {
    List<Client> findAll();

    Client create(Client client);

    Client update(Client client);

    Client findById(Integer id);

    void deleteById(Integer id);

    Page<Client> findAllWithPagination(Pageable pageable);

    void logicDelete(List<Integer> ids);

    void delete(List<Integer> ids);
}