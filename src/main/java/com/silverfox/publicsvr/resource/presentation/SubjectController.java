package com.silverfox.publicsvr.resource.presentation;

import com.silverfox.publicsvr.common.CommonResult;
import com.silverfox.publicsvr.resource.application.SubjectService;
import com.silverfox.publicsvr.resource.domain.EntitySubject;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 功能描述：
 *
 * @DATE 2023/12/3
 * @AUTHOR Jing.Li
 */
@RestController
public class SubjectController {
    @Autowired
    private SubjectService subjectService;
    @GetMapping("api/subject/findAll")
    public Mono<CommonResult<List<EntitySubject>>> findAll(){
        return CommonResult.ok(subjectService.findAll());
    }
    @GetMapping("api/subject/findById")
    public Mono<CommonResult<EntitySubject>> findById(@RequestParam @Validated @NotNull Long id){
        return CommonResult.ok(subjectService.findById(id));
    }
}
