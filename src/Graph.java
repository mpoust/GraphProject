
public class Graph extends WGraph {

	public Graph(){
		super();
	}
	
	public Graph(int size){
		super(size, "Graph");
	}
	
	@Override
	public void addEdge(int vi, int vj){
		super.addEdge(vi, vj, 1);
	}
	
}
