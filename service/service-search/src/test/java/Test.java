import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author kuanggong
 * @date 2023/7/24 16:10
 * @project_name atguigu-ssyx-parent
 */
public class Test {
    public static void main(String[] args) {


        ArrayList<Integer> integers = new ArrayList<>();
        ArrayList<Integer> integers2 = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);
        integers2.add(2);

        List<Integer> filteredList = integers.stream()
                // filter里面只返回为true的元素
                .filter(element -> !integers2.contains(element))
                .collect(Collectors.toList());
        System.out.println(filteredList);
    }
}
