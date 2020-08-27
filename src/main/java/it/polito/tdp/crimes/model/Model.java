package it.polito.tdp.crimes.model;

import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	
	private EventsDao dao;
	private Graph<String,DefaultWeightedEdge> grafo;
	
	public Model() {
		this.dao = new EventsDao();
	}
	
	public List<String> getCategory(){
		List<String> listaCat = new LinkedList<String>(this.dao.getCategoria());
		return listaCat;
	}
	
	public List<Integer> getMonth(){
		List<Integer> l = new LinkedList<Integer>(this.dao.getYear());
		return l;
	}
	
	public void creaGrafo(int m, String s) {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(this.grafo, this.dao.getVertex(s, m));
		
		for(Arco a : this.dao.getArchi(s, m)) {
			if(this.grafo.containsVertex(a.getReato()) && this.grafo.containsVertex(a.getReato2())) {
				DefaultWeightedEdge edge = this.grafo.getEdge(a.getReato(),a.getReato2());
				if(edge==null) {
					Graphs.addEdgeWithVertices(this.grafo, a.getReato(), a.getReato2(), a.getPeso()) ;
				}
			}
		}
	}
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public List<Arco> getPesoMax(){
		int max = 0;
		List<Arco> listaArchi = new LinkedList<Arco>();
		for(DefaultWeightedEdge a: this.grafo.edgeSet()) {
			if(this.grafo.getEdgeWeight(a)>max) {
				max = (int) this.grafo.getEdgeWeight(a);
				Arco n = new Arco (this.grafo.getEdgeSource(a), this.grafo.getEdgeTarget(a), (int) this.grafo.getEdgeWeight(a)) ;
				listaArchi.add(n);
			}
		}
		return listaArchi;
	}
	
}
