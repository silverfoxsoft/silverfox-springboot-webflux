package com.silverfox.publicsvr.resource.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * 功能描述：
 *
 * @DATE 2023/12/3
 * @AUTHOR Jing.Li
 */
@Table("entity_subject")
@Data
public class EntitySubject {
    @Id
    private Long id;
    private String name;
    private String fullname;
    private Long stageId;
}
