package cs475.matrix;

import java.io.Serializable;

/**
 * Created by nx on 3/3/14.
 */
public class Matrix implements Serializable {
    private static final int ROW=100;
    private static final int COL=100;
    private int r;
    private int c;
    private double matrix[][];
    public Matrix()
    {
        //100*100 as default；
        matrix=new double[ROW][COL];
        this.r=ROW;
        this.c=COL;
        for(int i=0;i<ROW;i++)
            for(int j=0;j<COL;j++)
                matrix[i][j]=0;
    }

    //initialize
    public Matrix(int row,int col)
    {
        matrix=new double[row][col];
        this.r=row;
        this.c=col;
        for(int i=0;i<row;i++)
            for(int j=0;j<col;j++)
                matrix[i][j]=0;
    }
    public boolean set(int row, int col, double value)
    {
        //check if the position is correct；
        if(row<0||row>this.r||col<0||col>this.r)
            return false;
        else
        {
            matrix[row][col]=value;
            return true;
        }

    }
    public double get(int row,int col)
    {
        return matrix[row][col];
    }
    public int width()
    {
        return this.c;
    }
    public int height()
    {
        return this.r;
    }

    //return the added matrix
    public Matrix add(Matrix b)
    {
        if(this.r!=b.r||this.c!=b.c)
        {
            System.out.println("ERROR!");
            return null;
        }
        Matrix m=new Matrix(r,c);
        for(int i=0;i<r;i++)
            for(int j=0;j<c;j++)
                m.matrix[i][j]=this.matrix[i][j]+b.matrix[i][j];
        return m;
    }

    public String toString()
    {
        String str="";
        for(int i=0;i<this.r;i++)
        {
            for(int j=0;j<this.c;j++)
                str+=(matrix[i][j]+"\t");
            str+="\n";
        }
        return str;
    }
}