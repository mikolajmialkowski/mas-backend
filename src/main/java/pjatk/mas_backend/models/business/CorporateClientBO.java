package pjatk.mas_backend.models.business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import pjatk.mas_backend.models.entities.VisitEntity;
import pjatk.mas_backend.models.enums.ClientType;
import pjatk.mas_backend.models.enums.DiscountAmount;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@ToString
@Builder
@Validated
@AllArgsConstructor
@NoArgsConstructor
public class CorporateClientBO {

    @Nullable
    private Long id;

    @NonNull
    @NotBlank
    private String firstName;

    @NonNull
    @NotBlank
    private String lastName;

    @NonNull
    @NotBlank
    private String companyName;

    @NonNull
    @Enumerated(EnumType.STRING)
    private DiscountAmount discountAmount;

    @NonNull
    @Enumerated(EnumType.STRING)
    private ClientType clientType;

    private Set<VisitEntity> visitEntities;
}
