package market.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Formatter;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.springframework.util.ObjectUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AsciiTable {
  
  public static final String SPACE = "-";
  
  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Column {
    private String name;
    private Integer width;
  }
  
  private static List<Object[]> toArrayData(List<?> list) {
    List<Object[]> result = list.stream()
        .map(item -> {
          Object[] values = null;
          Field[] fields = item.getClass().getDeclaredFields();
          values = new Object[fields.length];
          for (int i=0; i<fields.length; i++) {
            String name = fields[i].getName();
            String getterName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
            try {
              Method getter = item.getClass().getDeclaredMethod(getterName);
              values[i] = getter.invoke(item);
              if (values[i] == null) {
                values[i] = "[NULL]";
              }
            } catch (NoSuchMethodException e) {
              log.error("{}", e.getMessage());
            } catch (SecurityException e) {
              log.error("{}", e.getMessage());
            } catch (IllegalAccessException e) {
              log.error("{}", e.getMessage());
            } catch (IllegalArgumentException e) {
              log.error("{}", e.getMessage());
            } catch (InvocationTargetException e) {
              log.error("{}", e.getMessage());
            }
          }
          return values;
        })
        .toList();
    return result;
  }
  
  private static <K, V> List<Object[]> toArrayData(Map<K, V> map) {
    List<Object[]> result = new ArrayList<>();
    map.forEach((key, value) -> result.add(new Object[] {key, ObjectUtils.nullSafeConciseToString(value)}));
    return result;
  }
  
  public static <K, V> String getTable(Map<K, V> map) {
    if (map.size() == 0) {
      return "";
    }
    
    List<Object[]> rows = AsciiTable.toArrayData(map);
    List<String> colNames = Collections.emptyList();
    return AsciiTable.getTable(rows, colNames);
  }
  
  public static String getTable(List<?> list, String ... cols) {
    if (list.size() == 0) {
      return "";
    }
    
    List<Object[]> rows = AsciiTable.toArrayData(list);
    List<String> colNames = Arrays.asList(cols);
    return AsciiTable.getTable(rows, colNames);
  }
  
  public static String getTable(List<Object[]> rows, List<String> colNames) {
    int colCnt = rows.stream().findFirst().get().length;
    Integer[] maxWidth = IntStream.range(0, colCnt)
        .mapToObj(idx -> {
          Integer mwidth = rows.stream()
              .map(item -> item[idx].toString().length())
              .max(Integer::compareTo)
              .orElse(0);
          return mwidth;
        })
        .toArray(Integer[]::new);
    Formatter fmt = new Formatter();
    
    if (colNames.size() > 0) {
      AsciiTable.header(maxWidth, fmt, colNames);
    }
    
    AsciiTable.space(maxWidth, fmt); // space
    rows.stream().forEach(row -> {
      StringBuffer f = new StringBuffer();
      for (int i=0; i<colCnt; i++) {
        f.append("| %-" + maxWidth[i] + "s ");
      }
      f.append("|%n");
      fmt.format(f.toString(), row);
    });
    AsciiTable.space(maxWidth, fmt); // space
    return fmt.toString();
  }
  
  
  public static void header(Integer[] maxWidth, Formatter fmt, List<String> colNames) {
    List<AsciiTable.Column> columns = IntStream.range(0, colNames.size())
        .mapToObj(idx -> {
          return new AsciiTable.Column(colNames.get(idx), maxWidth[idx]);
        })
        .toList();
    AsciiTable.space(maxWidth, fmt); // space
    
    StringBuffer f = new StringBuffer();
    columns.stream().forEach(col -> {
      f.append("| %-" + col.getWidth() + "s ");
    });
    f.append("|%n");
    Object[] row = columns.stream().map(col -> col.getName()).toArray(String[]::new);
    fmt.format(f.toString(), row);
  }
  
  
  public static void space(Integer[] maxWidth, Formatter fmt) {
    StringBuffer f = new StringBuffer();
    Arrays.stream(maxWidth).forEach(width -> {
      f.append("+-%-" + width + "s-");
    });
    f.append("+%n");
    Object[] space = Arrays.stream(maxWidth).map(width -> SPACE.repeat(width)).toArray(String[]::new);
    fmt.format(f.toString(), space);
  }
}
