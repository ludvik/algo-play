
// You do not need to implement the Dictionary interface.
// You can use it directly, the implementation is provided when testing your solution.
public class SolutionBinarySearch {
    public int search(Dictionary dict, int target) {

        // validation
        if(dict.get(0) == null){
            return -1;
        }

        // go exponentially to search for an upper bound.
        int lower = 0;
        int upper = 1;
        while(dict.get(upper) != null && target > dict.get(upper)){
            lower = upper;
            upper *= 10;
        }

        if(dict.get(upper) == null){
            upper = searchForUpper(dict, lower, upper);
        }

        return binarySearch(dict, target, lower, upper);
    }

    // saerch for the biggest valid upper index
    private int searchForUpper(Dictionary dict, int lower, int upper){
        while(dict.get(upper) == null){
            upper = lower + ( upper - lower ) / 2;
        }
        return upper;
    }

    // basic binary search for target value;
    private int binarySearch(Dictionary dict, int target, int lower, int upper){
        while(lower <= upper){
            int mid = lower + ( upper - lower ) / 2;
            int midNum = dict.get(mid);
            if(midNum == target){
                return mid;
            }

            if(midNum < target){
                lower = mid + 1;
            }

            if(target < midNum){
                upper = mid - 1;
            }
        }

        return -1;
    }
}
