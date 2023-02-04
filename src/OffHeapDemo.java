
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystems;
import java.nio.file.StandardOpenOption;


//https://blogs.oracle.com/javamagazine/post/creating-a-java-off-heap-in-memory-database
public class OffHeapDemo {
    private static final String BTCUSDT = "./large_file";




    static void demo() throws IOException {
        long startTime = System.currentTimeMillis();
        RandomAccessFile file = new RandomAccessFile(BTCUSDT, "rw");
        FileChannel channel = file.getChannel();

        // Read file into mapped buffer
        MappedByteBuffer mbb =
                channel.map(FileChannel.MapMode.READ_WRITE,
                        0,          // position
                        channel.size());

        System.out.println("Reading content and printing ... ");
//        for (int i = 0; i < channel.size(); i++) {
//            System.out.print((char) mbb.get());
//        }

        channel.close();
        file.close();
        System.out.println("Total read and print time: " + (System.currentTimeMillis() - startTime));
    }

    static void createFile_1(String path,long initialSize) throws IOException {
        // Method 1: Using RandomAccessFile
        // recreate a different size file
        RandomAccessFile raFile =
                new RandomAccessFile(path, "rw");
        raFile.setLength(initialSize);
        FileChannel fc = raFile.getChannel();
        MappedByteBuffer mbb =
                fc.map(FileChannel.MapMode.READ_WRITE,
                        0,          // position
                        fc.size()); // size

        mbb.position(0);
        for(int i = 0 ;  i < initialSize;i++){
            mbb.put((byte)i);
        }
    }
    static void createFile_2(String path,long initialSize) throws IOException {
        // Method 2: Using File
        // not recreate a different size file

        File file = new File(path);
        file.createNewFile();
        FileInputStream fis = new FileInputStream(file);
        FileChannel readChannel = fis.getChannel();
        MappedByteBuffer readMap =
                readChannel.map(FileChannel.MapMode.READ_ONLY,
                        0,                   // position
                        readChannel.size()); // size

    }

    static void createFile_3(String path,long initialSize) throws IOException {
        // Method 3: Creating a FileChannel
        // not recreate a different size file

        FileChannel fc =
                FileChannel.open( FileSystems.getDefault().getPath(path),
                        StandardOpenOption.WRITE,
                        StandardOpenOption.READ);
        MappedByteBuffer mbb =
                fc.map(FileChannel.MapMode.READ_WRITE,
                        0,          // position
                        fc.size()); // size

        mbb.position(0);
        for(int i = 0 ;  i < initialSize;i++){
            mbb.put((byte)i);
        }
    }

    static void parallelProcess(String path, long initialSize) throws IOException {

        RandomAccessFile raFile =
                new RandomAccessFile(path, "rw");
        raFile.setLength(initialSize);
        FileChannel fc = raFile.getChannel();
        MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_WRITE, 0,    fc.size());
//        ByteBuffer bb = ByteBuffer.allocateDirect(10_000);

        Runnable r = new Runnable() {
            @Override
            public void run() {
                ByteBuffer localBB = bb.duplicate();

                String name = Thread.currentThread().getName();
                Integer threadNum = Integer.valueOf(name);
                int start = threadNum * Long.BYTES;
                localBB.position(start);
                localBB.mark();
                long startTime = System.currentTimeMillis();


                while ( true ) {
                    // Make sure the first digit is the thread number. This
                    // way when it's read and printed out, it will prove
                    // that each thread is writing to its own buffer position
                    // with no interference from the other threads
//                    Long val = Long.valueOf(""+(threadNum+1)+System.nanoTime());
                    Long val = Long.valueOf(""+(threadNum+1)+(System.currentTimeMillis() - startTime));


                    localBB.putLong(val);
                    localBB.reset();
                    Thread.yield();
                    if((System.currentTimeMillis() - startTime) > 1000){
                        System.out.print("thread exit " + name);

                        break;
                    }
                }
            }

        };

        int maxThreads = 12;

// Start the writer threads, where each writes to the buffer
        for ( int t = 0; t < maxThreads; t++ ) {
            Thread thread = new Thread(r);
            thread.setName(""+t);
            thread.start();
        }

// read the values from the different parts of the buffer and
// output them over and over
        long startTime = System.currentTimeMillis();

        while ( true ) {
            for ( int t = 0; t < maxThreads; t++ ) {
                bb.position(t*Long.BYTES);
                Long val = bb.getLong();
                System.out.print(val+", ");
            }
            System.out.println();
            Thread.yield();
            if((System.currentTimeMillis() - startTime) > 1000){
                System.out.print("timeout, exit while loop");

                break;
            }
        }
        System.out.print("exit while loop");

    }
    public static void main(String[] args) throws IOException {

//        createFile_1("a.txt",100);
        parallelProcess("a.txt",100);

    }
}