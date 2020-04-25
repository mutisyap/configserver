package tech.meliora.configserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A RudishaConfig.
 */
@Entity
@Table(name = "tbl_configs")
public class RudishaConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_key", nullable = false)
    private String key;

    @NotNull
    @Size(max = 7500)
    @Column(name = "value", length = 7500, nullable = false)
    private String value;

    @Column(name = "digest")
    private String digest;

    @Column(name = "last_updated_on")
    private Long lastUpdatedOn;

    @ManyToOne
    @JsonIgnoreProperties("rudishaConfigs")
    private Profile profile;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public RudishaConfig key(String key) {
        this.key = key;
        return this;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public RudishaConfig value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDigest() {
        return digest;
    }

    public RudishaConfig digest(String digest) {
        this.digest = digest;
        return this;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public Long getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public RudishaConfig lastUpdatedOn(Long lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
        return this;
    }

    public void setLastUpdatedOn(Long lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public Profile getProfile() {
        return profile;
    }

    public RudishaConfig profile(Profile profile) {
        this.profile = profile;
        return this;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RudishaConfig)) {
            return false;
        }
        return id != null && id.equals(((RudishaConfig) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RudishaConfig{" +
            "id=" + getId() +
            ", key='" + getKey() + "'" +
            ", value='" + getValue() + "'" +
            ", digest='" + getDigest() + "'" +
            ", lastUpdatedOn=" + getLastUpdatedOn() +
            "}";
    }
}
