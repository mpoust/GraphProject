
public class WGraph extends WDGraph {

	public WGraph() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WGraph(int size) {
		super(size, "WGraph");
		// TODO Auto-generated constructor stub
	}
	
	public WGraph(int size, String graphName){
		super(size, graphName);
	}
	
	@Override
	public void addEdge(int vi, int vj, double weight){
		super.addEdge(vi, vj, weight);
		super.addEdge(vj, vi, weight);
	}
	
	@Override
	public void addEdge(int vi, int vj){
		addEdge(vi, vj, 1);
	}
	

}
