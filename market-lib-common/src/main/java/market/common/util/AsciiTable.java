package market.common.util;

import java.util.Arrays;
import java.util.Formatter;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class AsciiTable {
  
  public static final String SPACE = "-";
  
  public static class ColumnLayout {
    public static final List<AsciiTable.Column> NAME_VALUE = Arrays.asList(
        new AsciiTable.Column("Name", 20),
        new AsciiTable.Column("Value", 40));
  }
  
  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Column {
    private String name;
    private Integer width;
  }
  
  public static Formatter header(List<Column> columns) {
    Formatter fmt = new Formatter();
    
    // space
    AsciiTable.space(columns, fmt);
    
    // header
    StringBuffer fRow = new StringBuffer();
    columns.stream().forEach(col -> {
      fRow.append("| %-" + col.getWidth() + "s ");
    });
    fRow.append("|%n");
    Object[] row = columns.stream().map(col -> col.getName()).toArray(String[]::new);
    fmt.format(fRow.toString(), row);
    
    // space
    AsciiTable.space(columns, fmt);
    
    return fmt;
  }
  
  public static Formatter space(List<Column> columns, Formatter fmt) {
    StringBuffer fSpace = new StringBuffer();
    columns.stream().forEach(col -> {
      fSpace.append("+-%-" + col.getWidth() + "s-");
    });
    fSpace.append("+%n");
    Object[] space = columns.stream().map(col -> SPACE.repeat(col.getWidth())).toArray(String[]::new);
    fmt.format(fSpace.toString(), space);
    return fmt;
  }
  
  public static Formatter body(List<Column> columns, Object[] row) {
    Formatter fmt = new Formatter();
    AsciiTable.body(columns, row, fmt);
    return fmt;
  }
  
  public static void body(List<Column> columns, Object[] row, Formatter fmt) {
    StringBuffer fRow = new StringBuffer();
    columns.stream().forEach(col -> {
      fRow.append("| %-" + col.getWidth() + "s ");
    });
    fRow.append("|%n");
    fmt.format(fRow.toString(), row);
  }
}
