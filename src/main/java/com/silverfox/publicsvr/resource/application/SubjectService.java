package com.silverfox.publicsvr.resource.application;

import com.silverfox.publicsvr.resource.domain.EntitySubject;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 功能描述：学科应用
 *
 * @DATE 2023/12/3
 * @AUTHOR Jing.Li
 */
public interface SubjectService {
    /**
     * 功能描述：查询所有学科
     *
     * @return
     */
    Flux<EntitySubject> findAll();
    /**
     * 功能描述：根据ID查询学科
     *
     * @param id
     * @return
     */
    Mono<EntitySubject> findById(Long id);
}
