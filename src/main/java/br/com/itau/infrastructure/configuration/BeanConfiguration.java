package br.com.itau.infrastructure.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.addConverter(MappingToModel.converterString_ListTemperament);
        modelMapper.addConverter(MappingToModel.converterImageResponseDTO_ListImage);
        modelMapper.addMappings(MappingToModel.mapBreedResponseDTO_Breed);

        return modelMapper;
    }

}
