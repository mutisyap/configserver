package tech.meliora.configserver.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link tech.meliora.configserver.domain.RudishaConfig} entity.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RudishaConfigDTO implements Serializable {

    private Long id;

    @NotNull
    private String key;

    @NotNull
    private String value;

    private String digest;

    private Long lastUpdatedOn;


    private Long profileId;

    private String profileName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public Long getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLastUpdatedOn(Long lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RudishaConfigDTO rudishaConfigDTO = (RudishaConfigDTO) o;
        if (rudishaConfigDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rudishaConfigDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RudishaConfigDTO{" +
            "id=" + getId() +
            ", key='" + getKey() + "'" +
            ", value='" + getValue() + "'" +
            ", digest='" + getDigest() + "'" +
            ", lastUpdatedOn=" + getLastUpdatedOn() +
            ", profileId=" + getProfileId() +
            ", profileName='" + getProfileName() + "'" +
            "}";
    }
}
