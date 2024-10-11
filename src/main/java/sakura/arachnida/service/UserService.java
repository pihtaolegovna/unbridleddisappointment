package sakura.arachnida.service;

import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sakura.arachnida.models.User;
import sakura.arachnida.repository.UserRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));  // Шифрование пароля
        userRepository.save(user);  // Сохранение пользователя
        return user;
    }

    public void updateRoleForUsers(List<Integer> ids, String newRole) {
        List<User> users = userRepository.findAllById(ids);
        users.forEach(user -> user.setRole(newRole)); // Update role for each user
        userRepository.saveAll(users); // Save updated users
    }

    public User findByLogin(String login) {
        // Возвращаем пользователя или выбрасываем исключение
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public List<User> findAll() {
        return userRepository.findAll();  // Возвращаем список всех пользователей
    }

    public Page<User> getUsersWithPaginationAndFilters(String role, Boolean isDeleted, Pageable pageable) {
        return userRepository.findAllWithFilters(role, isDeleted, pageable);
    }

    public void softDeleteUser(Integer userId) {
        userRepository.softDeleteById(userId);
    }

    public void softDeleteMultipleUsers(List<Integer> userIds) {
        userRepository.softDeleteByIds(userIds);
    }

    public void restoreUser(Integer userId) {
        userRepository.restoreById(userId);
    }

    public void restoreMultipleUsers(List<Integer> userIds) {
        userRepository.restoreByIds(userIds);
    }

    public void deleteUserPermanently(Integer userId) {
        userRepository.deleteById(userId);
    }

    public void deleteMultipleUsersPermanently(List<Integer> userIds) {
        userRepository.deleteAllByIdIn(userIds);
    }

    public Page<User> findAllWithPaginationAndFilters(Pageable pageable, String filterRole, Boolean isDeleted) {
        return userRepository.findAll((Specification<User>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filtering by role
            if (filterRole != null && !filterRole.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("role"), filterRole));
            }

            // Filtering by deletion status
            if (isDeleted != null) {
                predicates.add(criteriaBuilder.equal(root.get("isDeleted"), isDeleted));
            }

            // Combining all predicates
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, pageable);
    }

}