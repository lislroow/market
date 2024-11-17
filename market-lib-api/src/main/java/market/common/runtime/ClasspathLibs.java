package market.common.runtime;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;

import market.common.vo.BootJarVo;

public class ClasspathLibs {
  
  public static final String REGEX_BOOT_JAR = "(?:nested:)(.*)(?:/!BOOT-INF/.*)";
  public static final String REGEX_CLASSPATH_IDX = "(?<=BOOT-INF/lib/)(.*)(?:-)(\\d+(\\.[a-zA-Z0-9]+)*(?:-[a-zA-Z]*)*)\\.jar";
  public static final String REGEX_JAVA_CLASSPATH = "(.*)(?:-)(\\d+(\\.[a-zA-Z0-9]+)*(?:-[a-zA-Z]*)*)\\.jar";
  
  public static final String FILE_CLASSPATH_IDX = "BOOT-INF/classpath.idx";
  
  public static List<BootJarVo> getBootJars() {
    List<BootJarVo> result = new ArrayList<>();
    URL jarUrl = ClasspathLibs.class
        .getProtectionDomain()
        .getCodeSource()
        .getLocation();
    String path = jarUrl.getPath();
    
    boolean isBootJar = Pattern.compile(REGEX_BOOT_JAR).matcher(path).matches();
    if (isBootJar) {
      Matcher matcher = Pattern.compile(REGEX_BOOT_JAR).matcher(path);
      String jarPath = null;
      if (matcher.find()) {
        jarPath = matcher.group(1);
      } else {
        System.err.println("jarUrlPath not matched. " + path);
      }
      try (JarFile jar = new JarFile(jarPath)) {
        ZipEntry entry = jar.getEntry(FILE_CLASSPATH_IDX);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(jar.getInputStream(entry)))) {
          String line;
          Pattern pattern = Pattern.compile(REGEX_CLASSPATH_IDX);
          while ((line = reader.readLine()) != null) {
            matcher = pattern.matcher(line);
            if (matcher.find()) {
              String library = matcher.group(1);
              String version = matcher.group(2);
              result.add(new BootJarVo(library, version));
            } else {
              System.err.println("jar(BOOT-INF/lib) format not matched. " + line);
            }
          }
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      String classPath = System.getProperty("java.class.path");
      List<String> list = Arrays.asList(classPath.split(System.getProperty("path.separator")));
      result = list.stream()
          .filter(str -> str.endsWith(".jar"))
          .map(str -> {
            String fileName = str.substring(str.lastIndexOf(File.separatorChar) + 1);
            Pattern pattern = Pattern.compile(REGEX_JAVA_CLASSPATH);
            Matcher matcher = pattern.matcher(fileName);
            if (matcher.find()) {
              String library = matcher.group(1);
              String version = matcher.group(2);
              return new BootJarVo(library, version);
            } else {
              System.err.println("jar(JAVA_CLASSPATH) format not matched. " + fileName);
            }
            return null;
          })
          .collect(Collectors.toList());
    }
    
    return result;
  }
}
