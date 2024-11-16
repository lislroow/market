package market.common.modelmapper;

import java.util.List;

public interface GenericModel<D, E> {
  
  D toDto(E e);
  E toEntity(D d);
  List<D> toDtoList(List<E> entityList);
  List<E> toEntityList(List<D> dtoList);
}
