package pjatk.mas_backend.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.models.auth.In;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Proxy;
import pjatk.mas_backend.models.enums.HealthState;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Objects;
import java.util.Set;

@SuperBuilder
@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Proxy(lazy = false)
@ToString(callSuper = true)
public class DemandingPlantEntity extends PlantEntity {

    @Min(1)
    @Max(10)
    @NonNull
    private Double groundPH;

    @Min(1)
    @Max(365)
    @NonNull
    private Integer hydratingDays;

    @NonNull
    @Enumerated(EnumType.STRING)
    private HealthState healthState;

    @ToString.Exclude
    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    private CareEntity careEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DemandingPlantEntity that = (DemandingPlantEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
