package spring.framework.config.modelmapper;

import java.util.List;

public interface GenericModel<D, E> {
  
  D toDTO(E e);
  E toEntity(D d);
  List<D> toDTOList(List<E> entityList);
  List<E> toEntityList(List<D> dtoList);
  
  //void updateFromDTO(D dto, @Mapp)
}
