package GA;

import java.util.ArrayList;
import java.util.List;

import GA.Abs.AbsGeneration;
import GA.Const.CONST;
import GA.Const.CONST_PARA;

public class Generation extends AbsGeneration {

	public List<Gen> individuals;
	public List<Gen> selectionIndividuals;
	public List<Gen> newIndividuals;
	public double bestFitness=-1000000000;
	public Gen bestGen;
	public FitnessUnit fu;
	
	public Generation(CONST_PARA cr) {
		this.fu=new FitnessUnit(cr);
		
		// Initialization
		this.individuals = new ArrayList<Gen>();
		this.selectionIndividuals = new ArrayList<Gen>();
		this.newIndividuals = new ArrayList<Gen>();
	}
	
	public Generation() {
		
		// Initialization
		this.individuals = new ArrayList<Gen>();
		this.selectionIndividuals = new ArrayList<Gen>();
		this.newIndividuals = new ArrayList<Gen>();
	}
	
	public void crossover() {
		
		for (int i=0;i<CONST.POP_SIZE-CONST.SELECT_ELIT_BST;i++) {
			this.newIndividuals.add(new CrossoverUnit().Crossover(selectionIndividuals));
		}
	};
	
	public void selection(){
		
		try {
			SortFitness sf = new SortFitness<Gen>();
			this.individuals.sort(sf);	
			this.selectionIndividuals = new SelectionUnit().SelectionElite(this.individuals);
			newIndividuals.addAll(this.selectionIndividuals);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			this.selectionIndividuals = this.individuals.subList(0, CONST.SELECT_ELIT_BST-1);
			newIndividuals.addAll(this.selectionIndividuals);
		} finally {

		}
	}
	
	public Generation generate(){
	
		Generation gen= new Generation();
		
		List<Gen> res = new ArrayList<Gen>();
		gen.init(res);
		gen.NO=this.NO++;
		res.addAll(newIndividuals);
		
		return gen;
	}

	@Override
	public void init(List<Gen> indvs) {
		// TODO Auto-generated method stub
		this.individuals=indvs;
	}

	@Override
	public void mutation() {
		// TODO Auto-generated method stub
		for (Gen i:individuals) {
			i.mutation();
		}
	}
	
	public void calFitness() {
		individuals.get(0).calFitness();
		bestFitness=individuals.get(0).fitness;
		this.bestGen = individuals.get(0);
		for (Gen i:individuals) {
			i.calFitness();
			if (bestFitness<i.fitness) {
				this.bestFitness= i.fitness;
				this.bestGen = i;
			}
		}		
	}

	@Override
	public AbsGeneration repuduct() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void outputBest(){
		System.out.print("Generation No");
		System.out.println(this.NO);
		bestGen.output();
		
	}

}
	

