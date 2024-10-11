package sakura.arachnida.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sakura.arachnida.models.Client;
import sakura.arachnida.repository.ClientRepository;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client create(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Client update(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Client findById(Integer id) {
        return clientRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Integer id) {
        clientRepository.deleteById(id);
    }

    @Override
    public Page<Client> findAllWithPagination(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    @Override
    public void logicDelete(List<Integer> ids) {
        for (Integer id : ids) {
            Client client = findById(id);
            if (client != null) {
                client.setIsDeleted(!(client.isDeleted()));
                clientRepository.save(client);
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