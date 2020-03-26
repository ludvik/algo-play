
import java.util.ArrayList;
import java.util.List;

public class SimpleDictionary implements Dictionary {

    private final List<Integer> dict = new ArrayList<Integer>();

    public SimpleDictionary(Integer[] array){
        for(Integer n : array){
            dict.add(n);
        }
    }

    public Integer get(int index) {
        try {
            Integer num = dict.get(index);
            return num;
        } catch ( IndexOutOfBoundsException e ){
            return null;
        }
    }
}
