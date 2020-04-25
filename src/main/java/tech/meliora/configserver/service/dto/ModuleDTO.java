package tech.meliora.configserver.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link tech.meliora.configserver.domain.Module} entity.
 */
public class ModuleDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    private String description;


    private Long applicationId;

    private String applicationName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ModuleDTO moduleDTO = (ModuleDTO) o;
        if (moduleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), moduleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ModuleDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", applicationId=" + getApplicationId() +
            ", applicationName='" + getApplicationName() + "'" +
            "}";
    }
}
