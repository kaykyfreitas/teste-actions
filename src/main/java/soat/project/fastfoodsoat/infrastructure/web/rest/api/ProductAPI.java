package soat.project.fastfoodsoat.infrastructure.web.rest.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soat.project.fastfoodsoat.domain.pagination.Pagination;
import soat.project.fastfoodsoat.infrastructure.web.model.*;
import soat.project.fastfoodsoat.infrastructure.product.model.request.CreateProductRequest;
import soat.project.fastfoodsoat.infrastructure.product.model.request.UpdateProductRequest;
import soat.project.fastfoodsoat.infrastructure.product.model.response.CreateProductResponse;
import soat.project.fastfoodsoat.infrastructure.product.model.response.GetProductResponse;
import soat.project.fastfoodsoat.infrastructure.product.model.response.ListProductByCategoryResponse;
import soat.project.fastfoodsoat.infrastructure.product.model.response.UpdateProductResponse;

@Tag(name="Products")
@RequestMapping(value="products")
public interface ProductAPI {

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create a new product")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Product created successfully"
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "A validation error was thrown",
                    content = @Content(schema = @Schema(implementation = DefaultApiError.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "An internal server error was thrown",
                    content = @Content(schema = @Schema(implementation = DefaultApiError.class))
            )
    })
    @SecurityRequirement(name = "bearerAuth")
    ResponseEntity<CreateProductResponse> create(@Valid @RequestBody CreateProductRequest request);

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Get product by ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found",
                    content = @Content(schema = @Schema(implementation = DefaultApiError.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = DefaultApiError.class))
            )
    })
    ResponseEntity<GetProductResponse> getById(@PathVariable Integer id);

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Update a product by ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product updated successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found",
                    content = @Content(schema = @Schema(implementation = DefaultApiError.class))
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "Validation error",
                    content = @Content(schema = @Schema(implementation = DefaultApiError.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = DefaultApiError.class))
            )
    })
    @SecurityRequirement(name = "bearerAuth")
    ResponseEntity<UpdateProductResponse> update(@PathVariable Integer id, @Valid @RequestBody UpdateProductRequest request);

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete a product by ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Product deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found",
                    content = @Content(schema = @Schema(implementation = DefaultApiError.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = DefaultApiError.class))
            )
    })
    @SecurityRequirement(name = "bearerAuth")
    ResponseEntity<Void> delete(@PathVariable Integer id);

    @GetMapping(
            value = "/category/{categoryId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "List products by category with pagination")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Products retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = DefaultApiError.class))
            )
    })
    ResponseEntity<Pagination<ListProductByCategoryResponse>> listByCategory(
            @PathVariable(name = "categoryId") Integer categoryId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "per_page", defaultValue = "10") int perPage,
            @RequestParam(name = "sort", defaultValue = "name") String sort,
            @RequestParam(name = "dir", defaultValue = "asc") String dir,
            @RequestParam(name = "search", defaultValue = "") String search
    );

}



