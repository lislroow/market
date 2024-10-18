package market.api.product.controller;

import java.io.IOException;

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
import market.api.product.dto.ProductReqDto;
import market.api.product.dto.ProductResDto;
import market.api.product.service.ProductService;
import market.lib.aop.annotation.Login;
import market.lib.aop.annotation.UserInfo;
import market.lib.dto.ResponseDto;
import market.lib.enums.RESPONSE_CODE;
import market.lib.exception.MarketException;
import market.lib.vo.UserVo;

@RestController
@RequiredArgsConstructor
public class ProductController {
  
  private final ProductService productService;
  
  @GetMapping("/product/v1/list")
  //@Login
  //public ResponseDto<ProductResDto.ItemListRes> list(@UserInfo UserVo user) { // [O]
  //public ResponseDto<ProductResDto.ItemListRes> list(UserVo user) { // [X]
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
  @Login
  public ResponseDto<ProductResDto.ItemListRes> saveProducts(@UserInfo UserVo user,
      @RequestBody ProductReqDto.ItemListReq request) {
    ProductResDto.ItemListRes resDto = new ProductResDto.ItemListRes();
    resDto.setList(productService.saveProductList(request));
    return ResponseDto.body(resDto);
  }
  
  @PutMapping(value = "/product/v1/product",
      consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
  @Login
  public ResponseDto<ProductResDto.ItemRes> saveProduct(
      @UserInfo UserVo user,
      @RequestPart(name = "req") ProductReqDto.ItemReq request,
      @RequestPart(name = "imgThumb", required = false) MultipartFile imgThumb
      ) {
    ProductResDto.ItemRes resDto = null;
    try {
      resDto = productService.saveProduct(request, imgThumb);
    } catch (IllegalStateException|IOException e) {
      throw new MarketException(RESPONSE_CODE.C002);
    }
    return ResponseDto.body(resDto);
  }
  
  @DeleteMapping(value = "/product/v1/product",
      consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
  @Login
  public ResponseDto<ProductResDto.ItemRes> deleteProduct(
      @UserInfo UserVo user,
      @RequestBody ProductReqDto.ItemReq request) {
    ProductResDto.ItemRes resDto = productService.deleteProduct(request);
    return ResponseDto.body(resDto);
  }
  
  @PostMapping("/product/v1/add")
  public ResponseDto<Object> add() {
    productService.add();
    return ResponseDto.body();
  }
}
