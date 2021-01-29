import com.google.common.collect.Lists;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class test {
    static List<Integer> list = Arrays.asList(8,9,10,11,12,13,14,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95);
    static int pageSize = 28;
    public static void main(String[] args) {

    }

    public static List<Integer> getItemStack(int index){
        if(list.size()<=pageSize){
            return list;
        }
        int firstIndex = (index-1)*pageSize;
        int lastIndex = firstIndex + pageSize;
        if(lastIndex >= list.size()){
            lastIndex = list.size();
        }
        return list.subList(firstIndex,lastIndex);
    }
}
