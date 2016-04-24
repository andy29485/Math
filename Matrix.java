public class Matrix {
  private double data[][];
  private int    rows;
  private int    cols;

  public Matrix() {
    this.data = new double[0][0];
    this.rows = 0;
    this.cols = 0;
  }

  public Matrix(int rows, int cols) {
    this.data = new double[rows][cols];
    this.rows = rows;
    this.cols = cols;
  }

  public Matrix(Matrix A) {
    this.data = new double[A.getRows()][A.getColumns()];
    this.rows = A.getRows();
    this.cols = A.getColumns();
    for(int i=0; i<this.rows; i++) {
      for(int j=0; j<this.cols; j++) {
        this.data[i][j] = A.get(i, j);
      }
    }
  }

  public void setSize(int rows, int cols) {
    this.data = new double[rows][cols];
    this.rows = rows;
    this.cols = cols;
  }

  public int getRows() {
    return this.rows;
  }

  public int getColumns() {
    return this.cols;
  }

  public void set(int i, int j, double x) {
    if(i >= this.rows || j >= this.cols)
      throw new RuntimeException("Illegal matrix coordinates");
    this.data[i][j] = x;
  }

  public double get(int i, int j) {
    if(i >= this.rows || j >= this.cols)
      throw new RuntimeException("Illegal matrix coordinates");
    return data[i][j];
  }

  //add A to this matrix
  public Matrix add(Matrix A) {
    if(A.getRows() != this.rows || A.getColumns() != this.cols)
      throw new RuntimeException("Illegal matrix dimensions");
    Matrix B = new Matrix(this.rows, this.cols);
    for(int i=0; i<this.rows; i++)
      for(int j=0; j<this.cols; j++)
        B.set(i, j, this.get(i, j) + A.get(i, j));
    return B;
  }

  //subtract A from this matrix
  public Matrix subtract(Matrix A) {
    if(A.getRows() != this.rows || A.getColumns() != this.cols)
      throw new RuntimeException("Illegal matrix dimensions");
    Matrix B = new Matrix(this.rows, this.cols);
    for(int i=0; i<this.rows; i++)
      for(int j=0; j<this.cols; j++)
        B.set(i, j, this.get(i, j) - A.get(i, j));
    return B;
  }

  //multiply this by A(matrix)
  public Matrix multiply(Matrix A) {
    //After reading the problem statement I realized that this was unnecessary
    if(this.rows != A.getColumns())
      throw new RuntimeException("Illegal matrix dimensions");
    Matrix B = new Matrix(this.rows, A.getColumns());
    for(int i=0; i<B.getRows(); i++)
      for(int j=0; j<B.getColumns(); j++)
        for(int k=0; k<this.cols; k++)
          B.set(i, j, B.get(i, j) + this.get(i, k)*A.get(k, j));
    return B;
  }

  //multiply this by k(scalar)
  public Matrix multiply(double k) {
    Matrix A = new Matrix(this.rows, this.cols);
    for(int i=0; i<this.rows; i++)
      for(int j=0; j<this.cols; j++)
        A.set(i, j, this.get(i, j) * k);
    return A;
  }

  public Matrix reducedRowEchelonForm() {
    if(this.rows != this.cols+1)
      throw new RuntimeException("Illegal matrix dimensions");
    Matrix A = new Matrix(this);
    for(int i=0; i<this.rows; i++) {
      for(int j=0; j<this.cols; j++) {
        A.set(i, j, A.get(i, j)/A.get(i, i));
      }
      for(int j=0; j<i; j++) {
        for(int k=0; k<this.cols; k++) {
          A.set(i, k, A.get(i, k) - (A.get(j, k) * A.get(i, j)));
        }
      }
    }
    for(int i=this.rows-1; i>=0; i--) {
      for(int j=this.cols-2; j>i; j--) {
        for(int k=i+1; k<this.cols; k++) {
          A.set(i, k, A.get(i, k) - (A.get(i+1, k) * A.get(i, j)));
        }
      }
    }

    return A;
  }

  //OUTPUT
  // print the matrix
  public void print() {
    for(int i=0; i<this.rows; i++) {
      System.out.print("  |");
      for(int j=0; j<this.cols; j++) 
        System.out.printf("%7.2f ", this.get(i, j));
      System.out.println("|");
    }
  }
}
