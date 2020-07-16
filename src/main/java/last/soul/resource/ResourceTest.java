package last.soul.resource;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;

/**
 * 资源加载相关测试
 */
public class ResourceTest {
    public static void main(String[] args) throws IOException {
       // ClassLoader是从jar包根目录查找,即classpath的路径下，读取classpath下的文件不需要加"/"
        InputStream f1 = ResourceTest.class.getClassLoader().getResourceAsStream("1.txt");
        readLinesByStream(f1);

        //相对路径类类所在路径，class.getResourceAsStream是从last/soul/resource路径开始查找的
        //所以要从绝对路径开始找
        InputStream inputStream2 = ResourceTest.class.getResourceAsStream("/1.txt");
        readLinesByStream(inputStream2);

        //如果某个 Class 对象的 classLoader 属性值是 null，那么就表示这个类也是「根加载器」加载的。
        //Object类就是「根加载器」BootstrapClassLoader加载的，所以我们要用自己的Class，不要用Object等jdk提供的类。
//        System.out.println(Object.class.getClassLoader());
//        InputStream f3 = Object.class.getClassLoader().getResourceAsStream("1.txt");
//        readLinesByStream(f3);
        //无论何种方式也加载不到这个文件。后来发现是这个文件并没有编译到target目录，这个应该和maven有关，我们暂时先不研究它。
//        InputStream stream=ResourceTest.class.getResourceAsStream("2.txt");
//        System.out.println(stream);

    }

    private static void readLines(String path) throws IOException {
        List<String> list = Files.readAllLines(Paths.get(path));
        list.forEach(System.out::println);
    }

    public static void readLinesByStream(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            byte[] b = new byte[1024];
            int n = 0;
            while ((n = inputStream.read(b)) != -1) { // 当n不等于-1,则代表未到末尾
                stringBuilder.append(new String(b, 0, n));
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(stringBuilder);
    }
}
