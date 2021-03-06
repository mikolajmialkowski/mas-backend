package pjatk.mas_backend.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import pjatk.mas_backend.models.business.UndemandingPlantBO;
import pjatk.mas_backend.models.entities.NoviceGardenerEntity;
import pjatk.mas_backend.models.entities.SpeciesEntity;
import pjatk.mas_backend.models.entities.UndemandingPlantEntity;
import pjatk.mas_backend.models.enums.HealthState;
import pjatk.mas_backend.models.exceptions.BusinessException;
import pjatk.mas_backend.repositories.NoviceGardenerRepository;
import pjatk.mas_backend.repositories.SpeciesRepository;
import pjatk.mas_backend.repositories.UndemandingPlantRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UndemandingPlantService {

    private final static Logger LOGGER = LoggerFactory.getLogger(UndemandingPlantService.class);
    private final UndemandingPlantRepository undemandingPlantRepository;

    private final NoviceGardenerRepository noviceGardenerRepository;

    private final SpeciesRepository speciesRepository;

    public UndemandingPlantService(UndemandingPlantRepository undemandingPlantRepository
            , NoviceGardenerRepository noviceGardenerRepository, SpeciesRepository speciesRepository)
    {
        this.undemandingPlantRepository = undemandingPlantRepository;
        this.noviceGardenerRepository = noviceGardenerRepository;
        this.speciesRepository = speciesRepository;
    }


    public UndemandingPlantBO getUndemandingPlantById(Long id){
        UndemandingPlantEntity undemandingPlantEntity = undemandingPlantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No undemanding plant found, for id = " + id ));

        return entityToBusinessObject(undemandingPlantEntity);
    }

    public List<UndemandingPlantBO> getAllUndemandingPlants(){
        List<UndemandingPlantEntity> undemandingPlantEntityList = undemandingPlantRepository.findAll();

        if (undemandingPlantEntityList.isEmpty())
            throw new ResourceNotFoundException("No undemanding plants found");

        return undemandingPlantEntityList.stream().map(this::entityToBusinessObject).collect(Collectors.toList());
    }

    public UndemandingPlantBO saveUndemandingPlant(UndemandingPlantBO undemandingPlantBO){

        SpeciesEntity speciesEntity = speciesRepository
                .findById(undemandingPlantBO.getSpeciesEntity().getId())
                .orElseThrow(() -> new  BusinessException("No such species found"));

        NoviceGardenerEntity noviceGardenerEntity = noviceGardenerRepository
                .findById(undemandingPlantBO.getNoviceGardenerEntity().getId())
                .orElseThrow(() -> new  BusinessException("No such gardener found"));

        undemandingPlantBO.setSpeciesEntity(speciesEntity);
        undemandingPlantBO.setNoviceGardenerEntity(noviceGardenerEntity);

        UndemandingPlantEntity undemandingPlantEntity = undemandingPlantRepository.
                saveAndFlush(businessObjectToEntity(undemandingPlantBO));

        LOGGER.info("Saved new undemanding plant, as undemanding plant entity = " + undemandingPlantEntity);
        return entityToBusinessObject(undemandingPlantEntity);
    }
    public void deleteUndemandingPlant(long id){
        undemandingPlantRepository.deleteById(id);
        LOGGER.info("Deleted undemanding plant, as undemanding plant entity id = " + id);
    }

    private UndemandingPlantEntity businessObjectToEntity(UndemandingPlantBO undemandingPlantBO){
        return UndemandingPlantEntity.builder()
                .fertilizer(undemandingPlantBO.getFertilizer())
                .healthState(HealthState.HEALTHY_UNDEMANDING)
                .speciesEntity(undemandingPlantBO.getSpeciesEntity())
                .noviceGardenerEntities(undemandingPlantBO.getNoviceGardenerEntity())
                .build();
    }

    @SuppressWarnings("all")
    private UndemandingPlantBO entityToBusinessObject(UndemandingPlantEntity undemandingPlantEntity){
        return UndemandingPlantBO.builder()
                .id(undemandingPlantEntity.getId())
                .fertilizer(undemandingPlantEntity.getFertilizer())
                .speciesEntity(undemandingPlantEntity.getSpeciesEntity())
                .noviceGardenerEntity(undemandingPlantEntity.getNoviceGardenerEntities())
                .build();
    }
}
