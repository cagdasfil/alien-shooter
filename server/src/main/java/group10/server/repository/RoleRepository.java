package group10.server.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import group10.server.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
