import javafx.util.Pair;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

public class BinarySymmetricalMatrix {

    @Getter
    @Setter
    private int size;

    @Getter
    private Integer[][] elements;

    public void printArray(){
        for(Integer[] row: elements){
            for(int element: row){
                System.out.print(element+",");
            }
            System.out.println();
        }
    }

    private void initSize(int m){
        this.elements = new Integer[m][m];
    }

    public void initEmptyMatrixWithDefaultValue(int size, int initVal){
        this.size = size;
        initSize(size);
        for (Integer[] row: this.elements){
            Arrays.fill(row, initVal);
        }
    }



//    public void generateRandSymmetrixBinaryOfSize(int size){
//        this.size = size;
//
//        initSize(size);
//
//        for(int i=0;i<size;i++){
//            for(int j=0;j<size;j++){
//                int randval = (Math.random()>0.5?1:0);
//                if(i==j){
//                    this.elements[i][j]=0;
//                }else if(i>j){
//                    this.elements[i][j] = this.elements[j][i] = randval;
//                }else{
//                    continue;
//                }
//            }
//        }
//    }

    public List<Pair<Integer,Integer>> generatePositionsFromBitset(MyBitSet mb){
        List<Pair<Integer,Integer>> result = new ArrayList<Pair<Integer, Integer>>();
        for(int i=0;i<mb.getLogicalLength();i++){
            if(mb.get(i)){
                int acc=i+1;
                int lineIndex = (int)Math.ceil(Math.sqrt(2*acc+0.25)-1.5)+1;
                int colIndex = acc-lineIndex*(lineIndex-1)/2-1;

                Pair<Integer,Integer> pos = new Pair<Integer, Integer>(lineIndex, colIndex);
                result.add(pos);
            }
        }

        return result;
    }


//    public List<List<Pair<Integer, Integer>>> pickHalfPositionsFromN(int n){
//        assert n%2==0;
//
//        List<List<Pair<Integer, Integer>>> poslist = new ArrayList<List<Pair<Integer, Integer>>>();
//
//        List<MyBitSet> combs = Permutation.combinator(n,n/2);
//
//        for(MyBitSet mb : combs){
//            // each bit set represent a position list, which represent a symmetrix matrix
//            List<Pair<Integer,Integer>> positions = generatePositionsFromBitset(mb);
//            poslist.add(positions);
//        }
//
//        return poslist;
//    }

    public BinarySymmetricalMatrix generateCandidateMatrixWithCoordinates(List<Pair<Integer, Integer>> coordinates, int size){
      BinarySymmetricalMatrix mt = new BinarySymmetricalMatrix();
      mt.initEmptyMatrixWithDefaultValue(size, 0);
      for(Pair<Integer, Integer> position: coordinates){
          mt.getElements()[position.getKey()][position.getValue()] = 1;
          mt.getElements()[position.getValue()][position.getKey()] = 1;
      }

      return mt;
    }

//    /**
//     * pick half of elements under diagonal, if not possible(odd), return null
//     * @param size
//     * @return
//     */
//    public List<BinarySymmetricalMatrix> generateCandidateSelfComplementsMatrix(int size){
//        // C(mt.size, mt.size/2)
//        List<BinarySymmetricalMatrix> result = new ArrayList<BinarySymmetricalMatrix>();
//        if(size*(size-1)%4!=0){
//            // there is no possibility to generate self complements
//            return result;
//        }else{
//            // generate all combinations with have ones and half zeros, and check them one by one if they are self complement
//            int elementSize = size*(size-1)/2;
//            // get list of coordinates
//            List<List<Pair<Integer, Integer>>> coordinates = pickHalfPositionsFromN(elementSize);
//
//            for(List<Pair<Integer,Integer>> coordinate: coordinates){
//                BinarySymmetricalMatrix mtt = generateCandidateMatrixWithCoordinates(coordinate, size);
//                result.add(mtt);
//            }
//        }
//        return result;
//    }

    public boolean isComplement(BinarySymmetricalMatrix mt){
        if(mt.getSize()!=this.size || mt.getSize()<1){
            return false;
        }
        for(int i=0;i<mt.getSize();i++){
            for(int j=0;j<mt.getSize();j++){
                if(i<=j){
                    continue;
                }else{
                    if(mt.getElements()[i][j].equals(this.elements[i][j])){
                        return false;
                    }
                }
            }
        }

        return true;
    }


    public static void printInAnotherOrder(BinarySymmetricalMatrix mt, List<Integer> order){
        for(int i=0;i<mt.getSize();i++){
            for(int j=0;j<mt.getSize();j++){
                System.out.print(mt.getElements()[order.get(i)][order.get(j)]+",");
            }
            System.out.println();
        }
    }

    public static void printMatrixInAllIndexOrders(BinarySymmetricalMatrix mt){
        // generate permutations from 0..mt.size
        List<Integer> originOrder = new ArrayList<Integer>();
        for(int i = 0; i<mt.getSize();i++){
            originOrder.add(i);
        }

        Comparator<Integer> compInt = new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                return o1>o2?1:(o1.equals(o2)?0:-1);
            }
        };

        List<List<Integer>> allOrders = Permutation.permutate(originOrder,compInt);

        // for each permutation, generate a new BinarySymmetricalMatrix of the same size
        for(List<Integer> order: allOrders){
            printInAnotherOrder(mt, order);
            System.out.println("==========split==========");
        }
    }

    public static BinarySymmetricalMatrix getTransFormedMatrixByorder(BinarySymmetricalMatrix mt, List<Integer> order){
        BinarySymmetricalMatrix result = new BinarySymmetricalMatrix();
        result.setSize(mt.getSize());
        result.initEmptyMatrixWithDefaultValue(mt.getSize(), -1);

        for(int i=0;i<order.size();i++){
            for(int j=0;j<order.size();j++){
                result.getElements()[i][j] = mt.getElements()[order.get(i)][order.get(j)];
            }
        }

        return result;
    }

    public static List<BinarySymmetricalMatrix> getAllTransformedMatrix(BinarySymmetricalMatrix mt){
        List<BinarySymmetricalMatrix> endResult = new ArrayList<BinarySymmetricalMatrix>();

        List<Integer> originOrder = new ArrayList<Integer>();
        for(int i = 0; i<mt.getSize();i++){
            originOrder.add(i);
        }

        Comparator<Integer> compInt = new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                return o1>o2?1:(o1.equals(o2)?0:-1);
            }
        };

        List<List<Integer>> allOrders = Permutation.permutate(originOrder,compInt);

        // for each permutation, generate a new BinarySymmetricalMatrix of the same size
        for(List<Integer> order: allOrders){
            endResult.add(getTransFormedMatrixByorder(mt, order));
        }

        return endResult;
    }

    public static boolean isSelfComplement(BinarySymmetricalMatrix b1){
        List<BinarySymmetricalMatrix> transformedMatrix = getAllTransformedMatrix(b1);

        for(BinarySymmetricalMatrix bb: transformedMatrix){
            if(b1.isComplement(bb)){
                return true;
            }
        }

        return false;
    }

    public boolean checkBitSetSelfComplementIfTruePrint(MyBitSet origin){
        System.out.println("checking:"+origin.toBinaryString());

        List<Pair<Integer,Integer>> positions = generatePositionsFromBitset(origin);
        BinarySymmetricalMatrix mtt = generateCandidateMatrixWithCoordinates(positions, this.size);
        if(isSelfComplement(mtt)){
            mtt.printArray();
            System.out.println("========split==========");
            return true;
        }
        return false;
    }

    public void letsfindout(int sz){
        // get candidates
        if(sz*(sz-1)%4!=0){
            // there is no possibility to generate self complements
            System.out.println("There is nothing self complements of this size");
        }else{
            // generate all combinations with have ones and half zeros, and check them one by one if they are self complement
            int elementSize = sz*(sz-1)/2;
            // get list of coordinates
            MyBitSet origin = new MyBitSet(elementSize);
            origin.set(0, elementSize/2);
            origin.set(elementSize/2, elementSize, false);

            checkBitSetSelfComplementIfTruePrint(origin);

            boolean result = Permutation.getInplaceNextCombinator(origin);

            while(result){
                boolean flag = checkBitSetSelfComplementIfTruePrint(origin);

                // uncomment below line if you don't want to get one result only
//                if(flag){
//                    //get one result only
//                    return ;
//                }

                result = Permutation.getInplaceNextCombinator(origin);
            }
        }
    }


    public static void main(String[] args) {
        BinarySymmetricalMatrix mt = new BinarySymmetricalMatrix();

        int sz = 4;

        mt.initEmptyMatrixWithDefaultValue(sz,0);

        mt.letsfindout(sz);

    }
}
