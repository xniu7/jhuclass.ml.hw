package cs475.matrix;

import cs475.FeatureVector;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by nx on 3/2/14.
 */
public class FeatureMatrix implements Serializable {
    private HashMap<Integer, FeatureVector> _row = new HashMap<Integer, FeatureVector>();
    private HashMap<Integer,FeatureVector> _col = new HashMap<Integer, FeatureVector>();
    private int _max_row=Integer.MIN_VALUE, _max_col = Integer.MIN_VALUE;
    private int _min_row=Integer.MAX_VALUE, _min_col = Integer.MAX_VALUE;

    public FeatureVector getRow(int row_id){
        return this._row.get(row_id);
    }

    public FeatureVector getCol(int col_id){
        return this._col.get(col_id);
    }

    public double get(int row_id, int col_id){
        FeatureVector row = getRow(row_id);
        if(row!=null)
            return row.get(col_id);
        else
            return 0;
    }

    /**
     * row update
     * @param row_id row id
     */
    private void row_change(int row_id){
        if(this._min_row>row_id) this._min_row=row_id;
        if(this._max_row<row_id) this._max_row=row_id;
    }

    /**
     * col update
     * @param col_id col id
     */
    private void col_change(int col_id){
        if(this._min_col>col_id) this._min_col=col_id;
        if(this._max_col<col_id) this._max_col=col_id;
    }

    /**
     * add value in row HashMap
     * @param row_id row id
     * @param col_id col id
     * @param value added value
     */
    private void addBlockFromRow(int row_id, int col_id, double value){
        if(value==0) return;
        FeatureVector row = getRow(row_id);
        if(row==null){
            row = new FeatureVector();
            this._row.put(row_id,row);
        }
        row.add(col_id,row.get(col_id)+value);
        row_change(row_id);
    }

    /**
     * add value in col HashMap
     * @param row_id row id
     * @param col_id col id
     * @param value added value
     */
    private void addBlockFromCol(int row_id, int col_id, double value){
        if(value==0) return;
        FeatureVector col = getCol(col_id);
        if(col==null){
            col = new FeatureVector();
            this._col.put(col_id,col);
        }
        col.add(row_id, col.get(row_id)+value);
        col_change(col_id);
    }

    /**
     * add value in two HashMaps. Both are needed to be updated
     * @param row_id row id
     * @param col_id col id
     * @param value added value
     */
    public void addBlock(int row_id, int col_id, double value){
        if(value==0) return;
        addBlockFromRow(row_id,col_id,value);
        addBlockFromCol(row_id, col_id, value);
    }

    /**
     * set a value in row HashMap
     * @param row_id row id
     * @param col_id col id
     * @param value value
     */
    private void setBlockFromRow(int row_id, int col_id, double value){
        if(value==0) return;
        FeatureVector row = getRow(row_id);
        if(row==null){
            row = new FeatureVector();
            this._row.put(row_id,row);
        }
        row.add(col_id,value);
        row_change(row_id);
    }

    /**
     * set value in col HashMap
     * @param row_id row id
     * @param col_id col id
     * @param value added value
     */
    private void setBlockFromCol(int row_id, int col_id, double value){
        if(value==0) return;
        FeatureVector col = getCol(col_id);
        if(col==null){
            col = new FeatureVector();
            this._col.put(col_id,col);
        }
        col.add(row_id,value);
        col_change(col_id);
    }

    /**
     * add value in two HashMaps. Both are needed to be updated
     * @param row_id row id
     * @param col_id col id
     * @param value added value
     */
    public void set(int row_id, int col_id, double value){
        if(value==0) return;
        setBlockFromRow(row_id, col_id, value);
    }
      
    /**
     * traverse all blocks of fm, add them to the same position of result, add fm to result
     * @param fm matrix
     * @param result result matrix
     */
    public static void sum(FeatureMatrix fm,FeatureMatrix result){
        Iterator<Map.Entry<Integer,FeatureVector>> it_row = fm._row.entrySet().iterator();
        //each row iteration
        while(it_row.hasNext()){
            Map.Entry<Integer,FeatureVector> entry_row = it_row.next();
            int row_id = entry_row.getKey();
            FeatureVector row_feature = entry_row.getValue();
            Iterator<Map.Entry<Integer,Double>> it_block = row_feature.getKeyValue();
            //given a row, each col iteration
            while(it_block.hasNext()){
                Map.Entry<Integer,Double> entry_block = it_block.next();
                int col_id = entry_block.getKey();
                double value = entry_block.getValue();
                result.addBlock(row_id,col_id,value);
            }
        }
    }

    /**
     * matrix transform, just switch the row and colun 
     * @param fm matrix
     */
    public static void transform(FeatureMatrix fm){
        HashMap<Integer,FeatureVector> temp = fm._row;
        fm._col = fm._row;
        fm._row = temp;
    }

    public String toString(){
        //double[][] matrix = new double[this._max_row-this._min_row][this._max_col-this._min_col];
        String str = "\t";
        for(int j=this._min_col;j<=this._max_col;j++){
            str+=j;
            str+="\t";
        }
        str+="\n";
        for(int i=this._min_row;i<=this._max_row;i++){
            str+=i;
            str+="\t";
            for(int j=this._min_col;j<=this._max_col;j++){
                str+=String.valueOf(this.get(i, j));
                str+="\t";
            }
            str+="\n";
        }
        return str;
    }
}
