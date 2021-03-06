/*
       Title: hw5_2.java
       Abstract: Determines best route for TSP or prints -1 if there is not one.
       ID: 5051
       Date: 03/02/21
       Website: https://www.w3resource.com/java-exercises/array/java-array-exercise-68.php
 */
import java.util.*;

public class hw5_2 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        //Map to hold cities as <key,value> pairs
        Map<String, Integer> cityMap = new HashMap<>();
        //List to hold the resulting tsp costs
        List<Integer> tspCosts = new ArrayList<>();
        //List to hold name of cities
        List<String> cityList = new ArrayList<>();
        //Ints to hold vertices and edge information
        int vrtx, edges;

        //Input vertices
        vrtx = input.nextInt();

        //Create 2D Array with number of vertices as rows and columns
        int [][] tspValues = new int[vrtx][vrtx];
        for(int i = 0; i < vrtx; i++){
            //Set values to -1 to start
            for(int j = 0; j < vrtx; j++){
                tspValues[i][j] = -1;
            }
        }

        //For the number of vertices add cities
        for (int i = 0; i < vrtx; i++) {
            //Save city in String array to pull out zeroth index later
            String city = input.next();
            cityList.add(city);
            //Add cities to the cityMap
            cityMap.put(cityList.get(i), i);
        }
        //Create list to hold cityMap values
        List<Integer> mapVals = new ArrayList<>(cityMap.values());
        //Remove the first element 0 for permutations
        mapVals.removeAll(Collections.singleton(0));
        //Sort mapVals list for permutations
        Collections.sort(mapVals);
        //Create 2D list to store return value of permutation
        List<List<Integer>> result = new hw5_2().permute(mapVals);

        //Enter the number of edges
        edges = input.nextInt();

        //Adding input edges and corresponding values to indices
        for(int i = 0; i < edges; i++) {
           tspValues[cityMap.get(input.next())][cityMap.get(input.next())] = input.nextInt();
        }
        //Variables to decide the cost and paths
        int apath = -1, cost = Integer.MAX_VALUE;
        //for the number of permutations check the cost of the path and add to tspCosts
        for(int i = 0; i < result.size(); i++){
            tspCosts.add(checkPathCost(result.get(i), tspValues));
            //Finding the minimum cost path and if there is one.
            if(tspCosts.get(i) != -1 && cost > tspCosts.get(i)){
                cost = tspCosts.get(i);
                apath = i;
            }
        }
        System.out.print("Path:");
        //There is not a path for TSP.
        if(apath==-1){
            System.out.println();
            //Default cost to -1
            System.out.println("Cost:" + -1);
        }
        else{
            //Print the zeroth city as origin city
            System.out.print(cityList.get(0)+"->");
            //Find the path in result that is the minimum
            for(int i = 0; i < result.get(apath).size(); i++){
                //From city list go and get the permutation from result list that is the answer.
                System.out.print(cityList.get(result.get(apath).get(i))+"->");
            }
            //Print the zeroth city as return to origin city
            System.out.println(cityList.get(0));
            //Print cost associated with the path
            System.out.println("Cost:" + cost);
        }
    }


    public List<List<Integer>> permute(List<Integer> nums) {
        List<List<Integer>> result = new ArrayList<>();
        Permutation(0, nums, result);
        return result;
    }

    private void Permutation(int i, List<Integer> nums, List<List<Integer>> result) {
        if (i == nums.size() - 1) {
            List<Integer> list = new ArrayList<>();
            for (int n : nums) list.add(n);
            result.add(list);
        } else {
            for (int j = i, l = nums.size(); j < l; j++) {
                int temp = nums.get(j);
                nums.set(j, nums.get(i));
                nums.set(i, temp);
                Permutation(i + 1, nums, result);
                temp = nums.get(j);
                nums.set(j, nums.get(i));
                nums.set(i, temp);
            }
        }
    }
    private static int checkPathCost(List<Integer> tspCosts, int [][] tspValues){
        //Create variables for cost and indices
        int cost = 0;
        int u = 0, v;
        //Loop through costs
        for (Integer tspPath : tspCosts) {
            //Set v as index
            v = tspPath;
            //If the value is -1 return -1
            if (tspValues[u][v] == -1) {
                return -1;
            }
            //Add the cost of the column
            cost += tspValues[u][v];
            //update variable to look at next element
            u = tspPath;
        }
        //Reset variable
        v = 0;
        //Result in no cost
        if(tspValues[u][v]==-1){
            return -1;
        }
        //Results in cost
        cost+=tspValues[u][v];
        return cost;
    }
}
