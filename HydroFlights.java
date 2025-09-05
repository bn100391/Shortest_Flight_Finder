import java.util.*;
import java.io.*;

public class HydroFlights {
    static DirectedWeightedGraphAdjacency<String> network = new DirectedWeightedGraphAdjacency<String>();
    static Map<String, String> Queries = new HashMap<String, String>(); 

    public static void readFile(String hydroF) {
        try {
            File hydroFile = new File(hydroF);
            FileReader fileReader = new FileReader(hydroFile);
            BufferedReader reader = new BufferedReader(fileReader);

            int numCities = Integer.parseInt(reader.readLine());
            for (int i = 0; i < numCities; i++) {
                network.addVertex(reader.readLine());
            }

            int numFlights = Integer.parseInt(reader.readLine());
            for (int i = 0; i < numFlights; i++) {
                String line = reader.readLine();
                String[] parts = line.split("\t");
                network.addEdge(parts[0], parts[1], Double.parseDouble(parts[2].substring(1)));
            }

            int numQueries = Integer.parseInt(reader.readLine());
            for (int i = 0; i < numQueries; i++) {
                String line = reader.readLine();
                String[] parts = line.split("\t");
                Queries.put(parts[0], parts[1]); 
            }

            reader.close();
            fileReader.close();
        } catch (IOException e) {
            System.err.println("Read Failed.");
            e.printStackTrace();
        }
    }


    public static void computeThenWriteResults(String outputFile) {
        try {
            FileWriter writer = new FileWriter("outputFile");
            for (String startCity : Queries.keySet()) {
                StringBuilder sb = new StringBuilder();
                GraphPath<String> shortestPath = network.getShortestPath(startCity, Queries.get(startCity));
                double cost = shortestPath.getCost();
                List<String> allStops = shortestPath.getPath();
                sb.append("$" + " ");
                for (String city : allStops) {
                    sb.append(city + ", ");
                    writer.write(sb.toString() + "\n");
                }
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing results");
            System.exit(1);
        }
    }


    public static void main(String[] args) {
        if(args.length < 2){
            System.err.println("Ussage: [HYDRO-FILE-NAME] [OUTPUT-FILE-NAME]"); 
            System.exit(1); 
        }

        String hydroFile = args[0]; 
        String outputFileName = args[1]; 

        readFile(hydroFile);
      
    }
}
