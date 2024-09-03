package market.api.product.service;

import java.io.File;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import market.api.product.dto.ProductReqDto;
import market.api.product.dto.ProductResDto;
import market.api.product.entity.Product;
import market.api.product.repository.ProductRepository;
import market.lib.constant.Constant;
import market.lib.util.Uuid;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
  
  private final ProductRepository productRepository;
  private final ModelMapper modelMapper;
  
  public List<ProductResDto.ItemRes> list() {
    List<ProductResDto.ItemRes> res = productRepository.findAll().get().stream()
        .map(item -> modelMapper.map(item, ProductResDto.ItemRes.class))
        .collect(Collectors.toList());
    return res;
  }
  
  public ProductResDto.ItemRes detail(Integer productId) {
    ProductResDto.ItemRes res = modelMapper.map(productRepository.findById(productId).get(), ProductResDto.ItemRes.class);
    return res;
  }
  
  @Transactional
  public List<ProductResDto.ItemRes> saveProductList(ProductReqDto.ItemListReq request) {
    List<Product> entityList = request.getList().stream()
        .map(item -> {
          Product entity = modelMapper.map(item, Product.class);
          entity.setModifyDate(LocalDateTime.now());
          return entity;
        })
        .collect(Collectors.toList());
    entityList = productRepository.saveAll(entityList);
    List<ProductResDto.ItemRes> res = entityList.stream()
        .map(item -> modelMapper.map(item, ProductResDto.ItemRes.class))
        .collect(Collectors.toList());
    return res;
  }
  
  @Transactional
  public ProductResDto.ItemRes saveProduct(ProductReqDto.ItemReq request,
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
      request.setImgThumbUrl(imgThumbPath);
    }
    Product entity = modelMapper.map(request, Product.class);
    entity.setModifyDate(LocalDateTime.now());
    entity.setCreateDate(LocalDateTime.now());
    entity = productRepository.save(entity);
    ProductResDto.ItemRes res = modelMapper.map(entity, ProductResDto.ItemRes.class);
    return res;
  }
  
  @Transactional
  public ProductResDto.ItemRes deleteProduct(ProductReqDto.ItemReq request) {
    Product entity = modelMapper.map(request, Product.class);
    entity = productRepository.delete(entity);
    ProductResDto.ItemRes res = modelMapper.map(entity, ProductResDto.ItemRes.class);
    return res;
  }
  
  public void init() {
    Product LG_17UD70P = productRepository.findByName("홍길동").orElse(Product.builder().id(-1).build());
    LG_17UD70P.setName("LG 17UD70P");
    LG_17UD70P.setImgThumbUrl("https://fastly.picsum.photos/id/26/300/200.jpg?hmac=I1gNv7SHHWumF_lPObGuXN05PIgqI0NueUSdPFnFcq0");
    LG_17UD70P.setModifyDate(LocalDateTime.now());
    
    Product APPLE_IPAD = productRepository.findByName("Apple iPad Air 5").orElse(Product.builder().id(-1).build());
    APPLE_IPAD.setName("Apple iPad Air 5");
    APPLE_IPAD.setImgThumbUrl("https://fastly.picsum.photos/id/365/300/200.jpg?hmac=_ZkyfESsHt2RteInYatkFqQ4-OAG2em4hYhUoIJYbD0");
    APPLE_IPAD.setModifyDate(LocalDateTime.now());
    
    productRepository.saveAll(Arrays.asList(APPLE_IPAD, LG_17UD70P));
  }
  
  @Transactional
  public void add() {
    String uid = Long.toString(ByteBuffer.wrap(UUID.randomUUID().toString().getBytes()).getLong(), Character.MAX_RADIX);
    Product dummy = productRepository.findByName(uid).orElse(Product.builder().id(-1).build());
    dummy.setName("product-"+uid);
    dummy.setImgThumbUrl("img-"+uid);
    dummy.setModifyDate(LocalDateTime.now());
    productRepository.save(dummy);
  }
  
}
