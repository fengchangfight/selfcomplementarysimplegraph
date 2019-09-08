import java.util.*;

public class Permutation<T> {

    /**
     *
     * @param original
     * @param <T>
     * @return
     */

    private static<T> List<T> clone(List<T> original){
        List<T> copy = new ArrayList<T>(original);
        return copy;
    }

    private static <T> void switchElements(List<T> elements, int start, int end){
        T tmp = elements.get(start);
        elements.set(start, elements.get(end));
        elements.set(end, tmp);
    }

    private static <T> void reverse(List<T> elements, int start, int end){
        int i = start;
        int j = end;
        while(j>i){
            T tmp = elements.get(i);
            elements.set(i, elements.get(j));
            elements.set(j, tmp);
            j--;
            i++;
        }
    }

    private static <T> List<T> getNextPermutation(List<T> elements, Comparator<T> comparator){

        List<T> cloneElements = clone(elements);

        int size = cloneElements.size();
        if(size<=1){
            return null;
        }

        for(int j = size-1; j>=0;){
            if(comparator.compare(cloneElements.get(j-1),cloneElements.get(j) )>=0){
                j--;
                if(j==1 && comparator.compare(cloneElements.get(j-1),cloneElements.get(j))>0){
                    // already the last element, no need to do anything
                    return null;
                }
                continue;
            }else{
                reverse(cloneElements, j, size-1);
                for(int i=j;i<size;i++){
                    if(comparator.compare(cloneElements.get(i), cloneElements.get(j-1))>0){
                        switchElements(cloneElements, i, j-1);
                        break;
                    }
                }
                break;
            }
        }

        return cloneElements;
    }

    private static boolean allZerosBeforeOnes(MyBitSet bt){
        for(int i=0;i<bt.getLogicalLength()-1;i++){
            if(bt.get(i)==true && bt.get(i+1)==false){
                return false;
            }
        }
        return true;
    }

    private static void putAllOnes2Left(MyBitSet bt, int start, int end){
        int oneCnt = 0;
        for(int i=start; i<=end; i++){
            if(bt.get(i)){
                oneCnt++;
            }
        }
        bt.set(0, oneCnt);
        bt.set(oneCnt,end+1, false);
    }

    public static boolean getInplaceNextCombinator(MyBitSet origin){
        // do not have next operator
        if(allZerosBeforeOnes(origin)){
            return false;
        }

        for(int i =0 ;i<origin.getLogicalLength()-1;i++){
            // find first 10, this is guarantee to happen
            if(origin.get(i)==true && origin.get(i+1)==false){
                // do the thing and then break: 1: 10->01, 2: all bits before the swithing part, put all ones to the left
                origin.set(i, false);
                origin.set(i+1, true);

                putAllOnes2Left(origin, 0, i-1);

                return true;
            }
        }
        return false;
    }

//    private static MyBitSet getNextCombinator(MyBitSet origin){
//        // do not have next operator
//        if(allZerosBeforeOnes(origin)){
//            return null;
//        }
//
//        // new object by cloning
//        MyBitSet clonedBitSet = null;
//        try {
//            clonedBitSet = (MyBitSet) origin.clone();
//            for(int i =0 ;i<clonedBitSet.getLogicalLength()-1;i++){
//                // find first 10, this is guarantee to happen
//                if(clonedBitSet.get(i)==true && clonedBitSet.get(i+1)==false){
//                    // do the thing and then break: 1: 10->01, 2: all bits before the swithing part, put all ones to the left
//                    clonedBitSet.set(i, false);
//                    clonedBitSet.set(i+1, true);
//
//                    putAllOnes2Left(clonedBitSet, 0, i-1);
//
//                    return clonedBitSet;
//                }
//            }
//        } catch (CloneNotSupportedException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }


//    public static List<MyBitSet> combinator(int n, int r){
//        assert n>0;
//        assert r>0;
//        assert n>r;
//        List<MyBitSet> result = new ArrayList<MyBitSet>();
//
//        MyBitSet origin = new MyBitSet(n);
//        origin.set(0, r);
//        origin.set(r, n, false);
//
//        result.add(origin);
//
//        MyBitSet next = getNextCombinator(origin);
//
//        while(next!=null){
//            result.add(next);
//            next = getNextCombinator(next);
//        }
//
//        return result;
//    }

    public static <T> List<List<T>> permutate(List<T> elements, Comparator<T> comparator){

        List<List<T>> result = new ArrayList<List<T>>();

        // 排序，获得从小大大的第一个全排列作为基底
        Collections.sort(elements, comparator);

        List<T> firstPermutation = clone(elements);

        List<T> next = getNextPermutation(firstPermutation, comparator);
        // add self(start) permutation
        result.add(next);
        while(next!=null){
            result.add(next);
            next = getNextPermutation(next, comparator);
        }
        return result;
    }

//    public static void main(String[] args) {
//        List<MyBitSet> combs = combinator(6,3);
//
//        System.out.println(combs.size());
//
//        for(MyBitSet bt: combs){
//            System.out.println(bt.toBinaryString());
//        }
//    }

}
