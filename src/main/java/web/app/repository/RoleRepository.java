package web.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.app.model.ERole;
import web.app.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(ERole role);
}
