package bg.softuni.mobilele.repository;

import bg.softuni.mobilele.model.entity.Role;
import bg.softuni.mobilele.model.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(UserRole name);

    boolean existsByName(UserRole name);
}