
public class DGraph extends WDGraph {
	
	public DGraph(){
		super();
	}
	
	public DGraph(int size){
		super(size, "DGraph");
	}
	
	@Override
	public void addEdge(int vi, int vj){
		super.addEdge(vi, vj, 1);
	}

}
