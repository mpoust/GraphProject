import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/*
 * Weighted Directed Graph
 * Adjacency Matrix
 * 
 * With this version you cannot add a vertex after creation
 */

public class WDGraph {

	private 		double[][] 	graph;
	private static	int 		size = 10;
	private			String		graphName;
	
	public WDGraph(int size){
		this(size, "WDGraph");
	}
	
	public WDGraph(int size, String graphName){
		this.size = size;
		this.graphName = graphName;
		graph = new double[size][size];
	}
	
	public WDGraph(){
		this(size);
	}
	
	public void addEdge(int from, int to, double weight){
		if(!checkBounds(from, to))
			throw new IllegalArgumentException("Invalid Vertex");
		if(weight < 0)
			throw new IllegalArgumentException("Invalid Weight");
		//should also consider if an edge already exists in the location
		graph[from][to] = weight;
	}
	
	//add edge without specified weight - sets weight to default of 1
	public void addEdge(int from, int to){
		addEdge(from, to, 1);
	}
	
	/*
	 * This method will return true if there exists an edge from vi to vj 
	 * otherwise returns false
	 */
	public boolean existsEdge(int vi, int vj){
		if(!checkBounds(vi, vj))
			throw new IllegalArgumentException("Invalid Vertex");
		return graph[vi][vj] != 0.0;
	}
	
	private boolean checkBounds(int from, int to){
		return !(from < 0 || from > size-1 || to < 0 || to > size-1);
	}
	
	public double getEdgeWeight(int vi, int vj){
		if(existsEdge(vi, vj))
			return graph[vi][vj];
		throw new IllegalArgumentException("Edge does not exist.");
	}
	
	/**
	 * returns the list of all the  neighbors of vi <-- neighbor is a vertex that an edge points to
	 * @return
	 */
	public ArrayList<Integer> adjacentTo(int vi){
		//validate vi
		ArrayList<Integer> list = new ArrayList<>();
		
		for(int i = 0; i < size; i++)
			if(graph[vi][i] != 0)
				list.add(i);
		return list;
	}
	
	/**
	 * 
	 * @param vi
	 */
	public void adjacentOutput(int vi){
		ArrayList<Integer> list = adjacentTo(vi);
		StringBuilder sb = new StringBuilder("The vertices that are adjacent to V" + vi + " are: ");
		for(int i = 0; i < list.size(); i++)
			if(i == (list.size() - 1))
				sb.append("V" + list.get(i));
			else
				sb.append("V" + list.get(i) + ", ");
		System.out.println(sb);
	}
	

	/**
	 * visit verticies in the graph using the BFS algorithm
	 * starting at vi. return the order of visitation in list
	 * @param vi
	 */
	public ArrayList<Integer> BFSVisit(int vi){
		//validate input (vi)
		boolean[] visited = new boolean[size];
		ArrayList<Integer> list = new ArrayList<>();
		Queue<Integer> queue = new Queue<Integer>();
		int tempVertex;
		
		visited[vi] = true;
		queue.enqueue(vi);
		while(!queue.isEmpty()){
			tempVertex = queue.dequeue();
			list.add(tempVertex);
			for(int i : adjacentTo(tempVertex))
				if(!visited[i]){
					visited[i] = true;
					queue.enqueue(i);
				}
		}
		return list;
	}
	
	//implement depth first search
	public ArrayList<Integer> DFSVisit(int vi){
		return null;
	}
	
	/**
	 * returns true if the number of components is 1 - therefore connected graph
	 * @return
	 */
	public boolean isConnected(){
		return numComponents() == 1;
	}
	
	/**
	 * 
	 * @return number of components in the graph
	 */
	public int numComponents(){
		int 		count 	= 0;
		boolean[] 	visited = new boolean[size]; //a set would work better

		int			vertex;
		ArrayList<Integer> list;
		while(true){
			vertex = firstUnvisited(visited);
			if(vertex == -1)
				break;
			
			count++;
			list = BFSVisit(vertex);
			for(int i : list)
				visited[i] = true;
		}
		
		return count;
	}
	
	/**
	 * helper method to numComponents
	 * return the first unvisited vertex. return -1 if all visited
	 * @param visited
	 * @return 
	 */
	private int firstUnvisited(boolean[] visited){
		for(int i = 0; i < visited.length; i++)
			if(!visited[i])
				return i;
		return -1;
	}
	
	
	
	/**
	 * 
	 * @param vi
	 * @param vj
	 * @return return true iff there is a path from vi to vj
	 */
	public boolean existPath(int vi, int vj){

		return BFSVisit(vi).contains(vj);
	}
	
	/**
	 * this method will use Dijkstra's Shortest Path Algorithm
	 * to find the shortest path form source vi to all other 
	 * verticies. The result will be placed in the array and returned
	 * @param vi
	 * @return
	 */
	public int[] singleSourceShortestPath(int vi){		//DOESNT WORK IF A DISCONNECTED GRAPH
		
		int[] 	paths = new int[size];			//predecessor vertex
		boolean[] visited = new boolean[size];	//visited vertex
		double[] distance = new double[size];	// distance to vertex
		
		for(int i = 0; i < size; i++)	//Set all distances to infinity
			distance[i] = Double.POSITIVE_INFINITY;
		distance[vi] = 0.0;	// source distance to zero
			
		for(int i = 0; i < size; i++){	//find minimum vertex to go to
			int next = minVertex(distance, visited);
			visited[next] = true;
			
			ArrayList<Integer> n = adjacentTo(next);
			
			for(int j = 0; j < n.size(); j ++){
				int v = n.get(j);
				double d = distance[next] + graph[next][v];
				if(d < distance[v]){
					distance[v] = d;
					paths[v] = next;
				}
			}
			
		}
		return paths;
	}
	
	public static int minVertex(double[] dist, boolean[] visited){
		double x = Double.MAX_VALUE;
		int y = -1;
		for(int i = 0; i < dist.length; i++){
			if(!visited[i] && dist[i] < x){
				y = i;
				x = dist[i];
			}
		}
		return y;
	}
	
	/**
	 * 
	 * @param vi
	 * @param vj
	 */
	public Stack<Integer> shortestPath(int vi, int vj){
		int[] pathArr = singleSourceShortestPath(vi);
		Stack<Integer> pathStack = new Stack<Integer>(size);  //largest it will need to be is size of graph
		int index = vj;
		int value;
		while(index != vi){
			value = pathArr[index];
			pathStack.push(index);
			index = value;
		}
		pathStack.push(index);
		
		return pathStack;
		

		
		//System.out.println("SHORTEST PATH TEST");
		//System.out.println(pathStack.toString());
	}
	
	/**
	 * 
	 * @param vi
	 * @param vj
	 */
	public void shortestPathOutput(int vi, int vj){
		
		Stack<Integer> pathStack = shortestPath(vi, vj);
		
		System.out.println("The shortest path from " + 
				   "V" + vi + " to V" +
					vj + " in the " + graphName + " is: ");
			while(!pathStack.isEmpty()){
				System.out.print("V" + pathStack.pop());
				if(!pathStack.isEmpty())
					System.out.print(" -> ");
			}
			System.out.println();
	}
	
	/**
	 * 
	 * @param lat1
	 * @param lon1
	 * @param el1
	 * @param lat2
	 * @param lon2
	 * @param el2
	 * @return
	 */
	public double getDistance(double lat1, double lon1, double el1, double lat2, double lon2, double el2){
		
		int R = 6371; //radius of the Earth
		Double latDistance = Math.toRadians(lat2 - lat1);
		Double lonDistance = Math.toRadians(lon2 - lon1);
		Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
				   + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
				   * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = R * c * 1000; //convert to meters
		double height = el1 - el2;
		distance = Math.pow(distance, 2) + Math.pow(height, 2);
		distance = Math.sqrt(distance);
		//System.out.println(distance);
		// 1 meter equals 3.28084 feet
		distance = (distance * 3.28084); //1609.34
		//System.out.println(distance);
		return distance;					//return distance in feet.
	}
	
	/**
	 * 
	 * @param file
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	public ArrayList<String> generateFromFile(String file) throws NumberFormatException, IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(
												new FileInputStream(file)));
		ArrayList<String> verticies = new ArrayList<String>();
		ArrayList<String> edges = new ArrayList<String>();
		ArrayList<String> cases = new ArrayList<String>();
		//String lineFetched = null;
		int vertexCount;
		int edgeCount;
		int caseCount;
		
		vertexCount = Integer.parseInt(br.readLine());
		for(int i = 0; i < vertexCount; i++)
			verticies.add(br.readLine());
		
		edgeCount = Integer.parseInt(br.readLine());
		for(int i = 0; i < edgeCount; i++)
			edges.add(br.readLine());
		
		caseCount = Integer.parseInt(br.readLine());
		for(int i = 0; i < caseCount; i++)
			cases.add(br.readLine());
		
		br.close();
		
		int from;
		int to;
		int weight;
		boolean oneWay;
		String edge;
		String[] tokens;
		
		String fromStr;
		String toStr;
		String[] fromTokens;
		String[] toTokens;
		double fromLat;
		double fromLon;
		double fromEl;
		double toLat;
		double toLon;
		double toEl;
		
		//int count = 0;
		
		for(int i = 0; i < edgeCount; i++){
			oneWay = true;
			edge = edges.get(i);
			tokens = edge.split("\\s+");
			from = Integer.parseInt(tokens[0]);
			to = Integer.parseInt(tokens[1]);
			if(Integer.parseInt(tokens[2]) == 2)
				oneWay = false;
			
			fromStr = verticies.get(from);
			toStr = verticies.get(to);
			
			fromTokens = fromStr.split("\\s*,\\s*"); //trim whitespaces
			toTokens = toStr.split("\\s*,\\s*"); //trim whitespaces
			
			fromLon = Double.parseDouble(fromTokens[1]);
			fromLat = Double.parseDouble(fromTokens[2]);
			fromEl = Double.parseDouble(fromTokens[3]);
			
			toLon = Double.parseDouble(toTokens[1]);
			toLat = Double.parseDouble(toTokens[2]);
			toEl = Double.parseDouble(toTokens[3]);
			
			weight = (int) getDistance(fromLat, fromLon, fromEl, toLat, toLon, toEl);
			
			//System.out.println(weight);

			
			addEdge(from, to, weight);
			//count++;
			if(oneWay == false){
				addEdge(to, from, weight);
				//count++;
			}
		}
		//System.out.println(count);
		
		return verticies;
	}
	
	/**
	 * 
	 * @param vertices
	 * @param vi
	 * @param vj
	 */
	public void directions(ArrayList<String> vertices, int vi, int vj){
		Stack<Integer> path = shortestPath(vi, vj);
		String start = vertices.get(vi);
		String end = vertices.get(vj);
		String[] startTokens = start.split("\\s*,\\s*");
		String[] endTokens = end.split("\\s*,\\s*");
		int[] compassArr = new int[path.size()];
	
		System.out.println("The shortest path from " + startTokens[4] + 
							" to " + endTokens[4] + " is: ");
		
		String[] steps = new String[path.size()];
		String temp;
		String[] tempTokens;
		int loc;
		int count = 0;
		while(!path.isEmpty()){
			loc = path.pop();
			compassArr[count] = loc;
			temp = vertices.get(loc);
			tempTokens = temp.split("\\s*,\\s*");
			steps[count] = tempTokens[4];
			count++;
		}
		
		StringBuilder sb = new StringBuilder("     ");
		
		for(int i = 0; i < steps.length; i++)
			if(i == (steps.length - 1))
				sb.append(steps[i]);
			else
				sb.append(steps[i] + " -> ");
		
		System.out.println(sb);	
		String vertex1;
		String vertex2;
		String[] vertexArr1;
		String[] vertexArr2;
		double totDistance = 0;
		StringBuilder dir = new StringBuilder("Start at " + startTokens[4]);
		for(int i = 0; i < compassArr.length - 1; i++){
			vertex1 = vertices.get(compassArr[i]);
			vertex2 = vertices.get(compassArr[i+1]);
			vertexArr1 = vertex2.split("\\s*,\\s*");
			vertexArr2 = vertex1.split("\\s*,\\s*");
			totDistance += getDistance(Double.parseDouble(vertexArr2[2]), Double.parseDouble(vertexArr2[1]), 0,
										Double.parseDouble(vertexArr1[2]), Double.parseDouble(vertexArr1[1]), 0);
			dir.append(", go " + compass(vertex1, vertex2));
			dir.append(" to " + vertexArr1[4]);
		}
		dir.append(" where you arrive at your destination.");
		System.out.println(dir);
		System.out.printf("%.2f total feet traveled.\n\n",totDistance);
	}
	
	/**
	 * 
	 * @param vertex1
	 * @param vertex2
	 * @return
	 */
	public String compass(String vertex1, String vertex2){
		String[] vTokens1 = vertex1.split("\\s*,\\s*");
		String[] vTokens2 = vertex2.split("\\s*,\\s*");
		
		double lat1 = Math.toRadians(Double.parseDouble(vTokens1[2]));
		double lon1 = Math.toRadians(Double.parseDouble(vTokens1[1]));
		double lat2 = Math.toRadians(Double.parseDouble(vTokens2[2]));
		double lon2 = Math.toRadians(Double.parseDouble(vTokens2[1]));
		
		double dx = lon2 - lon1;
		double x = Math.sin(dx)*Math.cos(lat2);
		double y = Math.cos(lat1)*Math.sin(lat2)-Math.sin(lat1)*Math.cos(lat2)*Math.cos(dx);
		double angle = Math.toDegrees(Math.atan2(y, x)+360) % 360;
		
		if(angle < 90)
			return "South";
		else if(angle > 90 && angle < 180)
			return "East";
		else if(angle > 180 && angle < 270)
			return "North";
		else
			return "West";
	}

}

























