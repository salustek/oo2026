package ee.skev.veebipood.dto;

public record OrderRowDto(
        Long productId,
        int quantity
) {
}
