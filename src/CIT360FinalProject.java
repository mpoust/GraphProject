import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class CIT360FinalProject {
	
	public static int section = 0; //used to determine section letter

	public static void main(String[] args) throws NumberFormatException, IOException {
		
		//Problem C - WDGraph , creation -- 6 Verticies, 11 Edges
		WDGraph wdGraph = new WDGraph(6);
		wdGraph.addEdge(1, 0, 2);  //edge 1 -> 0: 2
		wdGraph.addEdge(1, 2, 3);  //edge 1 -> 2: 3
		wdGraph.addEdge(1, 5, 2);  //edge 1 -> 5: 2
		wdGraph.addEdge(2, 1, 1);  //edge 2 -> 1: 1
		wdGraph.addEdge(2, 5, 4);  //edge 2 -> 5: 4
		wdGraph.addEdge(3, 2, 9);  //edge 3 -> 2: 9
		wdGraph.addEdge(3, 4, 3);  //edge 3 -> 4: 3
		wdGraph.addEdge(3, 5, 2);  //edge 3 -> 5: 2
		wdGraph.addEdge(4, 3, 2);  //edge 4 -> 3: 2
		wdGraph.addEdge(4, 5, 4);  //edge 4 -> 5: 4
		wdGraph.addEdge(5, 4, 4);  //edge 5 -> 4: 4
		
		//System.out.println("PROBLEM C - OUTPUT");	//NEED TO CLEAN UP OUTPUT
		sectionOutput();
		wdGraph.adjacentOutput(2);
		wdGraph.adjacentOutput(5);
		//************END OF PROBLEM C********************
		
		//Problem D - DGraph , creation -- no weights -- 6 Verticies, 11 Edges
		DGraph dGraph = new DGraph(6);
		dGraph.addEdge(1, 0);	//edge 1 -> 0
		dGraph.addEdge(1, 2);	//edge 1 -> 2
		dGraph.addEdge(1, 5); 	//edge 1 -> 5
		dGraph.addEdge(2, 1);	//edge 2 -> 1
		dGraph.addEdge(2, 5);	//edge 2 -> 5
		dGraph.addEdge(3, 2);	//edge 3 -> 2
		dGraph.addEdge(3, 4); 	//edge 3 -> 4
		dGraph.addEdge(3, 5); 	//edge 3 -> 5
		dGraph.addEdge(4, 3); 	//edge 4 -> 3
		dGraph.addEdge(4, 5); 	//edge 4 -> 5
		dGraph.addEdge(5, 4);   //edge 5 -> 4
		
		//System.out.println("PROBLEM D - OUTPUT");  //NEED TO CLEAN UP OUTPUT
		sectionOutput();
		dGraph.adjacentOutput(2);
		dGraph.adjacentOutput(5);
		
		//************END OF PROBLEM D********************
		
		//Problem E - WGraph, creation -- no direction -- 6 Verticies, 8 Edges
		WGraph wGraph = new WGraph(6);
		wGraph.addEdge(0, 1, 2);  //edge 0 <-> 1: 2
		wGraph.addEdge(1, 2, 1);  //edge 1 <-> 2: 1
		wGraph.addEdge(1, 5, 2);  //edge 1 <-> 5: 2
		wGraph.addEdge(2, 3, 9);  //edge 2 <-> 3: 9
		wGraph.addEdge(2, 5, 4);  //edge 2 <-> 5: 4
		wGraph.addEdge(3, 4, 2);  //edge 3 <-> 4: 2
		wGraph.addEdge(3, 5, 2);  //edge 3 <-> 5: 2
		wGraph.addEdge(5, 4, 4);  //edge 5 <-> 4: 4
		
		//System.out.println("PROBLEM E - OUTPUT");  //NEED TO CLEAN UP OUTPUT
		sectionOutput();
		wGraph.adjacentOutput(2);
		wGraph.adjacentOutput(5);
		//************END OF PROBLEM E********************

		//Problem F - Graph, creation -- no direction & no weight -- 6 Verticies, 8 Edges
		Graph graph = new Graph(6);
		graph.addEdge(0, 1); //edge 0 <-> 1
		graph.addEdge(1, 2); //edge 1 <-> 2
		graph.addEdge(1, 5); //edge 1 <-> 5
		graph.addEdge(2, 3); //edge 2 <-> 3
		graph.addEdge(2, 5); //edge 2 <-> 5
		graph.addEdge(3, 4); //edge 3 <-> 4
		graph.addEdge(3, 5); //edge 3 <-> 5
		graph.addEdge(5, 4); //edge 5 <-> 4
		
		//System.out.println("PROBLEM F - OUTPUT"); //NEED TO CLEAN UP OUTPUT
		sectionOutput();
		graph.adjacentOutput(2);
		graph.adjacentOutput(5);
		//************END OF PROBLEM F********************

		//Problem G - WDGraph - shortest path from 2 -> 4
		//System.out.println("PROBLEM G - OUTPUT");  //NEED TO CLEAN UP OUTPUT
		sectionOutput();
		//int[] gPath = wdGraph.singleSourceShortestPath(2);
		//System.out.println(Arrays.toString(gPath));	
		wdGraph.shortestPathOutput(2, 4);
		
		//Problem H - DGraph - shortest path from 2 -> 4
		sectionOutput();
		dGraph.shortestPathOutput(2, 4);
		
		//Problem I - WGraph - shortest path from 2 -> 4
		sectionOutput();
		wGraph.shortestPathOutput(2, 4);
		
		//Problem J - Graph - shortest path from 2 -> 4
		sectionOutput();
		graph.shortestPathOutput(2, 4);   //determine why its not 2, 5, 4
	
		//FILE READING TESTING
		sectionOutput();
		ArrayList<String> vertices;

		WDGraph wmspt = new WDGraph(25);
		vertices = wmspt.generateFromFile("final.data");
		wmspt.directions(vertices, 17, 2);
		wmspt.directions(vertices, 19, 2);
		//System.out.println(vertices.toString());
		//System.out.println(wmspt.adjacentTo(3));
		
	}
	
	//section output
	public static void sectionOutput(){
		//int section = 0;
		char[] sectLetters = {'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'M'}; //may need to modify case m for sections
		char curSection = sectLetters[section++];
		//section++;
		System.out.println("\n**********************************************************\n"
						 + "************** Program Output for Section " + curSection + " **************\n"
						 + "**********************************************************");
	}
}




















