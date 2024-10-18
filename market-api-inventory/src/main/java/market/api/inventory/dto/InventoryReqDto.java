package market.api.inventory.dto;

import lombok.Data;

public class InventoryReqDto {
  
  private InventoryReqDto() {}
  
  @Data
  public static class UpdateQty {
    private Integer id;
    private Integer totalQty;
    private Integer soldQty;
  }
  
}
