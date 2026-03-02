package ee.skev.rentalstore.dto;

import ee.skev.rentalstore.entity.FilmType;

public record FilmSaveDto(
        String title,
        FilmType type
) {
}