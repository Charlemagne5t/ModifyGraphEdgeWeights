import org.junit.Test;

import java.util.Arrays;

public class SolutionTest {
    @Test
    public  void test1(){
        int n = 5;
        int[][] edges = {
                {1,4,1},
                {2,4,-1},
                {3,0,2},
                {0,4,-1},
                {1,3,10},
                {1,0,10},
        };
        int source = 0;
        int destination = 2;
        int target = 15;

        int[][] actual = new Solution().modifiedGraphEdges(n, edges, source, destination, target);

        Arrays.stream(actual).forEach(a -> System.out.println(Arrays.toString(a)));
    }
}
