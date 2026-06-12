package tech.wetech.admin3.sys.repository;

import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tech.wetech.admin3.sys.model.Resource;

/**
 * @author cjbi
 */
@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {

  @Query("from Resource where id in (:resourceIds)")
  Set<Resource> findByIds(Set<Long> resourceIds);
}
