package market.lib.config.database.mybatis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;


@Data
@JsonFormat(shape = Shape.OBJECT)
public class PagedList<T> implements List<T> {

  private List<T> list;
  private Paged paged = new Paged();
  
  @Data
  class Paged {
    // param
    Integer page;
    Integer pageSize;
    
    // result
    Integer start;
    Integer end;
    Integer total = -1;
  }
  
  public void setPage(Integer page) {
    paged.page = page;
  }
  public void setPageSize(Integer pageSize) {
    paged.pageSize = pageSize;
  }
  public void setStart(Integer start) {
    paged.start = start;
  }
  public void setEnd(Integer end) {
    paged.end = end;
  }
  public void setTotal(Integer total) {
    paged.total = total;
  }
  
  @JsonIgnore
  public Integer getPage() {
    return paged.page;
  }
  @JsonIgnore
  public Integer getPageSize() {
    return paged.pageSize;
  }
  @JsonIgnore
  public Integer getStart() {
    return paged.start;
  }
  @JsonIgnore
  public Integer getEnd() {
    return paged.end;
  }
  @JsonIgnore
  public Integer getTotal() {
    return paged.total;
  }
  
  
  public PagedList() {
    list = new ArrayList<>();
  }
  
  @Override
  public int size() {
    return list.size();
  }

  @JsonIgnore
  @Override
  public boolean isEmpty() {
    return list.isEmpty();
  }

  @Override
  public boolean contains(Object o) {
    return list.contains(o);
  }

  @Override
  public Iterator<T> iterator() {
    return list.iterator();
  }

  @Override
  public Object[] toArray() {
    return list.toArray();
  }

  @SuppressWarnings("hiding")
  @Override
  public <T> T[] toArray(T[] a) {
    return list.toArray(a);
  }

  @Override
  public boolean add(T e) {
    return list.add(e);
  }

  @Override
  public boolean remove(Object o) {
    return list.remove(o);
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return list.containsAll(c);
  }

  @Override
  public boolean addAll(Collection<? extends T> c) {
    return list.addAll(c);
  }

  @Override
  public boolean addAll(int index, Collection<? extends T> c) {
    return list.addAll(index, c);
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    return removeAll(c);
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    return list.retainAll(c);
  }

  @Override
  public void clear() {
    list.clear();
  }

  @Override
  public T get(int index) {
    return list.get(index);
  }

  @Override
  public T set(int index, T element) {
    return list.set(index, element);
  }

  @Override
  public void add(int index, T element) {
    list.add(index, element);
  }

  @Override
  public T remove(int index) {
    return list.remove(index);
  }

  @Override
  public int indexOf(Object o) {
    return list.indexOf(o);
  }

  @Override
  public int lastIndexOf(Object o) {
    return list.lastIndexOf(o);
  }

  @Override
  public ListIterator<T> listIterator() {
    return list.listIterator();
  }

  @Override
  public ListIterator<T> listIterator(int index) {
    return list.listIterator(index);
  }

  @Override
  public List<T> subList(int fromIndex, int toIndex) {
    return list.subList(fromIndex, toIndex);
  }
}