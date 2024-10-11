package sakura.arachnida.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sakura.arachnida.models.Ticket;

import java.util.List;

public interface TicketService {
    List<Ticket> findAll(); // Получить все билеты

    Ticket create(Ticket ticket); // Создать новый билет

    Ticket update(Ticket ticket); // Обновить билет

    Ticket findById(Integer id); // Найти билет по ID

    void deleteById(Integer id); // Удалить билет по ID

    Page<Ticket> findAllWithPagination(Pageable pageable); // Найти билеты с пагинацией

    Page<Ticket> findAllWithPaginationAndFilters(int page, int size, String sortField, String sortDirection, String filterStatus, Double filterPrice, Boolean isDeleted); // Найти билеты с пагинацией и фильтрами

    void logicDelete(List<Integer> ids); // Логическое удаление билетов

    void restore(List<Integer> ids); // Восстановить билеты
}