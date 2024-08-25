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

import lombok.extern.slf4j.Slf4j;
import market.api.product.dto.ProductDetailRES;
import market.api.product.dto.ProductREQ;
import market.api.product.dto.ProductRES;
import market.api.product.service.ProductService;

@Slf4j
@RestController
public class ProductController {
  
  private ProductService service;
  
  public ProductController(ProductService service) {
    this.service = service;
  }
  
  @GetMapping("/product/v1/list")
  public List<ProductRES> list() {
    List<ProductRES> res = service.list();
    return res;
  }
  
  @GetMapping("/product/v1/{productId}")
  public ProductDetailRES detail(
      @PathVariable(name = "productId", required = true) Integer productId) {
    ProductDetailRES res = null;
    res = service.detail(productId);
    return res;
  }
  
  @PutMapping("/product/v1/save-product-list")
  public List<ProductRES> saveProductList(@RequestBody List<ProductREQ> req) {
    List<ProductRES> resList = service.saveProductList(req);
    return resList;
  }
  
  @PutMapping(value = "/product/v1/save-product",
      consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
  public ProductRES saveProduct(@RequestPart(name = "req") ProductREQ req,
      @RequestPart(name = "imgThumb", required = false) MultipartFile imgThumb) throws Exception {
    ProductRES res = service.saveProduct(req, imgThumb);
    return res;
  }
  
  @DeleteMapping(value = "/product/v1/delete-product",
      consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
  public ProductRES deleteProduct(@RequestBody ProductREQ req) throws Exception {
    ProductRES res = service.deleteProduct(req);
    return res;
  }
  
  @PostMapping("/product/v1/init")
  public void init() {
    service.init();
  }
  
  @PostMapping("/product/v1/add")
  public void add() {
    service.add();
  }
}
