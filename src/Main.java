
import com.github.sh0nk.matplotlib4j.NumpyUtils;
import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import com.github.sh0nk.matplotlib4j.builder.ContourBuilder;
import com.github.sh0nk.matplotlib4j.builder.HistBuilder;
import com.github.sh0nk.matplotlib4j.builder.ScaleBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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


    public static void main(String[] args) throws PythonExecutionException, IOException {
        testPlot();

        System.out.println("Hello world!");
    }
}