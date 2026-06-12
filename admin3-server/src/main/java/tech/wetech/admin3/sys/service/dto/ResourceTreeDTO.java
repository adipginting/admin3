package tech.wetech.admin3.sys.service.dto;

import java.util.List;
import tech.wetech.admin3.sys.model.Resource.Type;

/**
 * @author cjbi
 */
public record ResourceTreeDTO(
    Long id,
    String name,
    Type type,
    String permission,
    String url,
    String icon,
    List<ResourceTreeDTO> children,
    Long parentId,
    String parentName) {}
