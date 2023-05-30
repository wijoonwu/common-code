package company.ejm.commoncode.api.repository;

import company.ejm.commoncode.api.entity.CommonCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommonCodeRepository extends JpaRepository<CommonCode, Long> {
    boolean existsByCode(String code);

    boolean existsByName(String name);

    List<CommonCode> findByCodeGroupName(String groupName);

    void deleteAllByCodeGroupId(long id);
}
