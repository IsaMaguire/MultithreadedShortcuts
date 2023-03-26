public class ShortcutThread implements Runnable{ 
    private float[][] readMatrixX;
    private float[][] readMatrixY;
    private float[][] writeMatrix;

    private int size;
    private int threadStart;
    private int threadEnd;

    // private int workPerThread;


    // constructor sets the matrices to read from and write to 
    public ShortcutThread (float[][] wmat, float[][] rmatx, float[][] rmaty, int tStart, int tEnd) {
	    readMatrixX = rmatx;
        readMatrixY = rmaty;
        writeMatrix =  wmat;

        size = rmatx.length;
        threadStart = tStart;
        threadEnd = tEnd;
        
    }

    // the run method required by the Runnable interface
    // this is the code that gets executed when we start the thread
    public void run () {
        // Get the values for each 


	    for (int i = threadStart; i < threadEnd + 1; ++i) {
            for (int j = 0; j < size; ++j) {
                float min = Float.MAX_VALUE;
                for (int k = 0; k < size; ++k) {
                    float x = readMatrixX[i][k];
                    // Here is the memory optimization. Instead of using the same matrix to get y,
                    // we use the flipped matrix. Usig the original gives us y = readMatrixX[k][j]
                    // which means that the inner for loop is accessing a new array with every iteration.
                    // This refreshes the cache each time we run which slows down our speed. Using a transposed array
                    // for y lets us access the same array each time through, just a different index. 
                    // Now the cache will be put to maximum use since the whole inner for loop uses the same arrays.
                    float y = readMatrixY[j][k];
                    float z = x + y;
                    if (z < min) {
                        min = z;
                    }
                }
                
                writeMatrix[i][j] = min;
            
                
                
            }
        }

    }

    
}
