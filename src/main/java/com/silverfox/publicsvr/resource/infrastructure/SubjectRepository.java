package com.silverfox.publicsvr.resource.infrastructure;

import com.silverfox.publicsvr.resource.domain.EntitySubject;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 * 功能描述：学科repository
 *
 * @DATE 2023/12/3
 * @AUTHOR Jing.Li
 */
public interface SubjectRepository extends ReactiveCrudRepository<EntitySubject,Long> {
}
