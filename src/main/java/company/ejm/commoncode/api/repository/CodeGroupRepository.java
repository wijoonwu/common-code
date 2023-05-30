package company.ejm.commoncode.api.repository;

import company.ejm.commoncode.api.entity.CodeGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CodeGroupRepository extends JpaRepository<CodeGroup, Long> {
    boolean existsByName(String groupName);
    Optional<CodeGroup> findByName(String groupName);
    void deleteAllByName(String groupName);

}
