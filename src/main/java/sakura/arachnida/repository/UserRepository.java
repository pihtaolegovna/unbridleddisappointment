package sakura.arachnida.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import sakura.arachnida.models.User;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import sakura.arachnida.models.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    // Find user by login
    Optional<User> findByLogin(String login);

    // Find all users with pagination and sorting
    Page<User> findAll(Pageable pageable);

    // Filter users based on role or deleted status (or both)
    @Query("SELECT u FROM User u WHERE " +
            "(:role IS NULL OR u.role = :role) AND " +
            "(:isDeleted IS NULL OR u.isDeleted = :isDeleted)")
    Page<User> findAllWithFilters(String role, Boolean isDeleted, Pageable pageable);

    // Soft delete (logical deletion) for a single user
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.isDeleted = true WHERE u.id = :userId")
    void softDeleteById(Integer userId);

    // Soft delete for multiple users
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.isDeleted = true WHERE u.id IN :userIds")
    void softDeleteByIds(List<Integer> userIds);

    // Restore a logically deleted user
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.isDeleted = false WHERE u.id = :userId")
    void restoreById(Integer userId);

    // Restore multiple logically deleted users
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.isDeleted = false WHERE u.id IN :userIds")
    void restoreByIds(List<Integer> userIds);

    // Physical delete for a single user
    @Transactional
    void deleteById(Integer userId);

    // Physical delete for multiple users
    @Transactional
    void deleteAllByIdIn(List<Integer> userIds);

    Page<User> findAll(Specification<User> userSpecification, Pageable pageable);

}