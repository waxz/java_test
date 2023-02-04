
import static java.lang.Math.*;

import com.github.sh0nk.matplotlib4j.NumpyUtils;
import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import com.github.sh0nk.matplotlib4j.builder.ContourBuilder;
import com.github.sh0nk.matplotlib4j.builder.HistBuilder;
import com.github.sh0nk.matplotlib4j.builder.ScaleBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import java.awt.Color;
import java.io.IOException;
import com.github.plot.Plot.Line;

import io.javalin.Javalin;

import java.awt.Color;
import java.util.stream.Stream;

import smile.classification.KNN;
import smile.io.*;
import smile.plot.swing.*;
import smile.stat.distribution.*;
import smile.math.matrix.*;
import smile.validation.CrossValidation;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public class Main {
    public static void testPlot() throws IOException, PythonExecutionException {
        List<Double> x = NumpyUtils.linspace(-Math.PI, Math.PI, 256);
        List<Double> C = x.stream().map(xi -> Math.cos(xi)).collect(Collectors.toList());
        List<Double> S = x.stream().map(xi -> Math.sin(xi)).collect(Collectors.toList());

        Plot plt = Plot.create();
        plt.plot().add(x, C);
        plt.plot().add(x, S);
        plt.show();
    }
    static void testPlot_1() throws IOException {
        com.github.plot.Plot plot = new com.github.plot.Plot(null)    // setting data
                .series(null, com.github.plot.Plot.data().
                xy(1, 2).
                xy(3, 4), null);

// saving sample_minimal.png
        plot.save("sample_minimal", "png");


    }

    static void basicType(){
        int a = 9;
        long b = 1209;
        float c = 0.3f;
        double d = 11.3e10;
        byte e = 4;
        short f = 45;
        Boolean g = true;
        char h = 23;


    }

    static void testWeb(){
        Javalin app = Javalin.create().start(5000); // creating and launching the server

        app.get("/", ctx -> ctx.result("1 2 3 Start.")); // adding root endpoint

    }

    static void testML() throws IOException, URISyntaxException, ParseException, InterruptedException, InvocationTargetException {

        {
            var iris = Read.arff("data/weka/iris.arff");
            var canvas = ScatterPlot.of(iris, "sepallength", "sepalwidth", "class", '*').canvas();
            canvas.setAxisLabels("sepallength", "sepalwidth");


            canvas.window();

        }
        {
            var iris = Read.arff("data/weka/iris.arff");
            var canvas = ScatterPlot.of(iris, "sepallength", "sepalwidth", "petallength", "class", '*').canvas();
            canvas.setAxisLabels("sepallength", "sepalwidth", "petallength");
            canvas.window();
        }

        {


            var iris = Read.arff("data/weka/iris.arff");
            var x = iris.drop("class").toArray();
            var y = iris.column("class").toIntArray();
            CrossValidation.classification(10, x, y, (_x, _y) -> KNN.fit(_x, _y, 3));
        }

    }

    static void testFP(){
        final List<String> friends =
                Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott","Tom");
        final List<String> editors =
                Arrays.asList("Brian", "Jackie", "John", "Mike");
        final List<String> comrades =
                Arrays.asList("Kate", "Ken", "Nick", "Paula", "Zach");
        List<Integer> list = new ArrayList<Integer>();
        int a = 13;
        list = friends.stream().map(_a -> _a.length()+1).collect(Collectors.toList());
        list = friends.stream().map(_a -> _a.length()+1).collect(Collectors.toList());

        final long countFriendsStartN =
                friends.stream()
                        .filter(name -> name.startsWith("N"))
                        .count();
        final long countEditorsStartN1 =
                editors.stream()
                        .filter(name -> name.startsWith("N"))
                        .count();
        final long countFriendsStartN2 =
                comrades.stream()
                        .filter(name -> name.startsWith("N"))
                        .count();

        System.out.println("countFriendsStartN =  " + countFriendsStartN);
        System.out.println("countEditorsStartN1 =  " + countEditorsStartN1);
        System.out.println("countFriendsStartN2 =  " + countFriendsStartN2);
        System.out.println("list =  " + list);


    }
    public static void main(String[] args) throws PythonExecutionException, IOException, URISyntaxException, ParseException, InterruptedException, InvocationTargetException {

//        testPlot();

//        testML();
        testFP();
        System.out.println("Hello world!");
    }
}