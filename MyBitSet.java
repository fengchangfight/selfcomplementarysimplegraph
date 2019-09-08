import lombok.Getter;
import lombok.Setter;

import java.util.BitSet;

@Getter
@Setter
public class MyBitSet implements Cloneable {
    public MyBitSet(int n){
        this.logicalLength = n;
        this.value = new BitSet(n);
    }
    private BitSet value;
    private Integer logicalLength;

    public void set(int start, int end){
        value.set(start, end, true);
    }

    public void set(int index, boolean flag){
        value.set(index, flag);
    }

    public void set(int start, int end, boolean flag){
        value.set(start, end, flag);
    }

    public boolean get(int index){
        return value.get(index);
    }

    @Override
    public Object clone()throws CloneNotSupportedException{
        MyBitSet mbs = (MyBitSet) super.clone();
        mbs.value = (BitSet) value.clone();
        return mbs;
    }

    public String toBinaryString(){
        StringBuilder sb = new StringBuilder();


        for(int i=0;i<logicalLength;i++){
            sb.append(value.get(i)?"1":"0");
        }
        return sb.toString();
    }
}
