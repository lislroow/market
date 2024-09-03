package market.api.product.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import market.api.product.dto.ProductReqDto;
import market.api.product.dto.ProductResDto;
import market.api.product.service.ProductService;
import market.lib.dto.ResponseDto;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductController {
  
  private final ProductService productService;
  
  @GetMapping("/product/v1/list")
  public ResponseDto<ProductResDto.ItemListRes> list() {
    ProductResDto.ItemListRes resDto = new ProductResDto.ItemListRes();
    resDto.setList(productService.list());
    return ResponseDto.body(resDto);
  }
  
  @GetMapping("/product/v1/{productId}")
  public ResponseDto<ProductResDto.ItemRes> detail(
      @PathVariable Integer productId) {
    ProductResDto.ItemRes resDto = productService.detail(productId);
    return ResponseDto.body(resDto);
  }
  
  @PutMapping("/product/v1/products")
  public ResponseDto<ProductResDto.ItemListRes> saveProducts(
      @RequestBody ProductReqDto.ItemListReq request) {
    ProductResDto.ItemListRes resDto = new ProductResDto.ItemListRes();
    resDto.setList(productService.saveProductList(request));
    return ResponseDto.body(resDto);
  }
  
  @PutMapping(value = "/product/v1/product",
      consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseDto<ProductResDto.ItemRes> saveProduct(
      @RequestPart(name = "req") ProductReqDto.ItemReq request,
      @RequestPart(name = "imgThumb", required = false) MultipartFile imgThumb
      ) throws Exception {
    ProductResDto.ItemRes resDto = productService.saveProduct(request, imgThumb);
    return ResponseDto.body(resDto);
  }
  
  @DeleteMapping(value = "/product/v1/product",
      consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseDto<ProductResDto.ItemRes> deleteProduct(
      @RequestBody ProductReqDto.ItemReq request) throws Exception {
    ProductResDto.ItemRes resDto = productService.deleteProduct(request);
    return ResponseDto.body(resDto);
  }
  
  @PostMapping("/product/v1/init")
  public ResponseDto<?> init() {
    productService.init();
    return ResponseDto.body();
  }
  
  @PostMapping("/product/v1/add")
  public ResponseDto<?> add() {
    productService.add();
    return ResponseDto.body();
  }
}
