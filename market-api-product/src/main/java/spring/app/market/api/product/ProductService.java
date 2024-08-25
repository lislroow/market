package spring.app.market.api.product;

import java.io.File;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import spring.app.market.api.product.dto.ProductDetailRES;
import spring.app.market.api.product.dto.ProductREQ;
import spring.app.market.api.product.dto.ProductRES;
import spring.app.market.api.product.entity.Product;
import spring.app.market.api.product.repository.ProductRepository;
import spring.app.market.common.Constant;
import spring.app.market.util.Uuid;

@Service
@Transactional(readOnly = true)
public class ProductService {
  
  private ProductRepository repository;
  
  @Autowired
  ModelMapper model;
  
  public ProductService(ProductRepository repository) {
    this.repository = repository;
  }
  
  public List<ProductRES> list() {
    List<ProductRES> res = repository.findAll().get().stream()
        .map(item -> model.map(item, ProductRES.class)).collect(Collectors.toList());
    return res;
  }
  
  public ProductDetailRES detail(Integer productId) {
    ProductDetailRES res = model.map(repository.findById(productId).get(), ProductDetailRES.class);
    return res;
  }
  
  @Transactional
  public List<ProductRES> saveProductList(List<ProductREQ> req) {
    List<Product> entityList = req.stream()
        .map(item -> {
          Product entity = model.map(item, Product.class);
          entity.setModifyDate(LocalDateTime.now());
          return entity;
        })
        .collect(Collectors.toList());
    entityList = repository.saveAll(entityList);
    List<ProductRES> res = entityList
        .stream().map(item -> model.map(item, ProductRES.class))
        .collect(Collectors.toList());
    return res;
  }
  
  @Transactional
  public ProductRES saveProduct(ProductREQ req,
      MultipartFile imgThumb) throws Exception {
    if (imgThumb != null) {
      String originName = imgThumb.getOriginalFilename();
      String originExt = originName.substring(originName.lastIndexOf(".")+1, originName.length());
      String imgThumbPath = Constant.UPLOAD_BASE + "/product/" + Uuid.create() + "." + originExt;
      if (System.getProperty("os.name").startsWith("Win")) {
        File dir = new File("C:"+Constant.UPLOAD_BASE + "/product/");
        if (!dir.exists()) dir.mkdirs();
        imgThumb.transferTo(new File("C:"+imgThumbPath));
      } else {
        File dir = new File(Constant.UPLOAD_BASE + "/product/");
        if (!dir.exists()) dir.mkdirs();
        imgThumb.transferTo(new File(imgThumbPath));
      }
      req.setImgThumbUrl(imgThumbPath);
    }
    Product entity = model.map(req, Product.class);
    entity.setModifyDate(LocalDateTime.now());
    entity.setCreateDate(LocalDateTime.now());
    entity = repository.save(entity);
    ProductRES res = model.map(entity, ProductRES.class);
    return res;
  }
  
  @Transactional
  public ProductRES deleteProduct(ProductREQ req) {
    Product entity = model.map(req, Product.class);
    entity = repository.delete(entity);
    ProductRES res = model.map(entity, ProductRES.class);
    return res;
  }
  
  public void init() {
    Product LG_17UD70P = repository.findByName("홍길동").orElse(Product.builder().id(-1).build());
    LG_17UD70P.setName("LG 17UD70P");
    LG_17UD70P.setImgThumbUrl("https://fastly.picsum.photos/id/26/300/200.jpg?hmac=I1gNv7SHHWumF_lPObGuXN05PIgqI0NueUSdPFnFcq0");
    LG_17UD70P.setModifyDate(LocalDateTime.now());
    
    Product APPLE_IPAD = repository.findByName("Apple iPad Air 5").orElse(Product.builder().id(-1).build());
    APPLE_IPAD.setName("Apple iPad Air 5");
    APPLE_IPAD.setImgThumbUrl("https://fastly.picsum.photos/id/365/300/200.jpg?hmac=_ZkyfESsHt2RteInYatkFqQ4-OAG2em4hYhUoIJYbD0");
    APPLE_IPAD.setModifyDate(LocalDateTime.now());
    
    repository.saveAll(Arrays.asList(APPLE_IPAD, LG_17UD70P));
  }
  
  @Transactional
  public void add() {
    String uid = Long.toString(ByteBuffer.wrap(UUID.randomUUID().toString().getBytes()).getLong(), Character.MAX_RADIX);
    Product dummy = repository.findByName(uid).orElse(Product.builder().id(-1).build());
    dummy.setName("product-"+uid);
    dummy.setImgThumbUrl("img-"+uid);
    dummy.setModifyDate(LocalDateTime.now());
    repository.save(dummy);
  }
  
}
