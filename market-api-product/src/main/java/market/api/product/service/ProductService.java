package market.api.product.service;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import market.api.product.dto.ProductReqDto;
import market.api.product.dto.ProductResDto;
import market.api.product.entity.Product;
import market.api.product.repository.ProductRepository;
import market.common.constant.Constant;
import market.common.enumcode.RESPONSE_CODE;
import market.common.exception.MarketException;
import market.common.util.Uuid;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
  
  private final ProductRepository productRepository;
  private final ModelMapper modelMapper;
  
  public List<ProductResDto.ItemRes> list() {
    return productRepository.findAll().stream()
        .map(item -> modelMapper.map(item, ProductResDto.ItemRes.class))
        .toList();
  }
  
  public ProductResDto.ItemRes detail(Integer productId) {
    return modelMapper.map(productRepository.findById(productId).orElseThrow(), ProductResDto.ItemRes.class);
  }
  
  @Transactional
  public List<ProductResDto.ItemRes> saveProductList(ProductReqDto.ItemListReq request) {
    List<Product> entityList = request.getList().stream()
        .map(item -> {
          Product entity = modelMapper.map(item, Product.class);
          entity.setModifyDate(LocalDateTime.now());
          return entity;
        })
        .toList();
    entityList = productRepository.saveAll(entityList);
    return entityList.stream()
        .map(item -> modelMapper.map(item, ProductResDto.ItemRes.class))
        .toList();
  }
  
  @Transactional
  public ProductResDto.ItemRes saveProduct(ProductReqDto.ItemReq request,
      MultipartFile imgThumb) throws IllegalStateException, IOException {
    if (imgThumb != null) {
      String originName = imgThumb.getOriginalFilename();
      if (!StringUtils.hasLength(originName)) {
        throw new MarketException(RESPONSE_CODE.C001);
      }
      String originExt = originName.substring(originName.lastIndexOf(".")+1, originName.length());
      String imgThumbPath = Constant.File.UPLOAD_PRODUCT + File.pathSeparator + Uuid.create() + "." + originExt;
      if (System.getProperty("os.name").startsWith("Win")) {
        File dir = new File("C:"+Constant.File.UPLOAD_PRODUCT);
        if (!dir.exists()) dir.mkdirs();
        imgThumb.transferTo(new File("C:"+imgThumbPath));
      } else {
        File dir = new File(Constant.File.UPLOAD_PRODUCT);
        if (!dir.exists()) dir.mkdirs();
        imgThumb.transferTo(new File(imgThumbPath));
      }
      request.setImgThumbUrl(imgThumbPath);
    }
    Product entity = modelMapper.map(request, Product.class);
    entity.setModifyDate(LocalDateTime.now());
    entity.setCreateDate(LocalDateTime.now());
    entity = productRepository.save(entity);
    return modelMapper.map(entity, ProductResDto.ItemRes.class);
  }
  
  @Transactional
  public ProductResDto.ItemRes deleteProduct(ProductReqDto.ItemReq request) {
    Product entity = modelMapper.map(request, Product.class);
    entity = productRepository.delete(entity);
    return modelMapper.map(entity, ProductResDto.ItemRes.class);
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
