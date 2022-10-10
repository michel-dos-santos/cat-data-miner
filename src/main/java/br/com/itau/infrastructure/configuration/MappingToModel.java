package br.com.itau.infrastructure.configuration;

import br.com.itau.domain.entity.Breed;
import br.com.itau.domain.entity.Image;
import br.com.itau.domain.entity.Temperament;
import br.com.itau.infrastructure.service.thecatapi.output.BreedResponseDTO;
import br.com.itau.infrastructure.service.thecatapi.output.ImageResponseDTO;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MappingToModel {

    public static final PropertyMap<BreedResponseDTO, Breed> mapBreedResponseDTO_Breed = new PropertyMap<BreedResponseDTO, Breed>() {
        protected void configure() {
            map().setExternalId(source.getId());
            using(converterString_ListTemperament).map(source.getTemperament()).setTemperaments(null);
            using(converterImageResponseDTO_ListImage).map(source.getImage()).setImages(null);
        }
    };

    public static final Converter<String, List<Temperament>> converterString_ListTemperament = new AbstractConverter<String, List<Temperament>>() {
        @Override
        protected List<Temperament> convert(String source) {
            if (Objects.nonNull(source)) {
                return Arrays.stream(source
                        .split(","))
                        .map(temperament -> new Temperament(null, temperament.trim()))
                        .collect(Collectors.toList());
            }
            return null;
        }
    };

    public static final Converter<ImageResponseDTO, List<Image>> converterImageResponseDTO_ListImage = new AbstractConverter<ImageResponseDTO, List<Image>>() {
        @Override
        protected List<Image> convert(ImageResponseDTO source) {
            if (Objects.nonNull(source)) {
                return Arrays.asList(new Image(null, source.getId(), source.getUrl()));
            }
            return null;
        }
    };
    //public static final PropertyMap<DE, PARA> mapDE_PARA = new PropertyMap<DE, PARA>() {
    //    protected void configure() {
    //
    //    }
    //};

}