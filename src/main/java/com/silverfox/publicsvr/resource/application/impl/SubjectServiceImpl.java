package com.silverfox.publicsvr.resource.application.impl;

import com.silverfox.publicsvr.resource.application.SubjectService;
import com.silverfox.publicsvr.resource.domain.EntitySubject;
import com.silverfox.publicsvr.resource.infrastructure.SubjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 功能描述：学科应用实现类
 *
 * @DATE 2023/12/3
 * @AUTHOR Jing.Li
 */
@Service
@Slf4j
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;
    @Override
    public Flux<EntitySubject> findAll() {
        return subjectRepository.findAll();
    }

    @Override
    public Mono<EntitySubject> findById(Long id) {
        log.info("测试链路");
        return subjectRepository.findById(id);
    }
}
