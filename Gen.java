package GA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import GA.Abs.AbsGen;
import GA.Const.CONST_PARA;

public class Gen  extends AbsGen {

	public double indivaual[];
	
	public double fitness;
	
	public HashMap<Integer, ArrayList> route;
	
	public double cost;
	
	public double numofVehicles;
	
	public double satisfication;
	
	public double distance;

	public double variance;
	
	public  FitnessUnit fu;
	
//	public Gen() {
//		fu = new FitnessUnit();
//	}
	public Gen(CONST_PARA cr) {
		fu = new FitnessUnit(cr);
	}


 
	public void mutation(){
		
		MutationUnit mu = new MutationUnit();
		mu.Mutation(this);
		
	}

	
	public void output() {
		
		// title
//		System.out.print("Fitness");
//		System.out.print("\t");
//		System.out.print("Number Of Vehicles");
//		System.out.print("\t");
//		System.out.print("Cost");
//		System.out.print("\t");
//		System.out.print("Route");
//		
//		System.out.println();
		
		// Data
		System.out.print(this.fitness);
		System.out.print("\t");
		System.out.print(this.numofVehicles);
		System.out.print("\t");
		System.out.print(this.distance);
		System.out.print("\t");
		System.out.print(this.satisfication);
		System.out.print("\t");
		System.out.print(this.cost);
		System.out.print("\t");
		if (route!=null) {
			for (Entry<Integer, ArrayList> es: route.entrySet()) {
//				System.out.print("V_No:");
//				System.out.print(es.getKey());
//				System.out.print("\t");

				for (int dx=0;dx<es.getValue().size();dx++) {
					System.out.print(es.getValue().get(dx));
					if (dx!=es.getValue().size()-1)
						System.out.print("->");
					else 
						System.out.print("\t");
				}
			}

		}
		
//		System.out.print("\t");
//		System.out.print(this.variance);
		
	}
	
	
	public void output2() {
		
		System.out.print("fitness:");
		System.out.print(this.fitness);
		System.out.print("    ");
		
		System.out.print("NumofVehicles:");
		System.out.print(this.numofVehicles);
		System.out.print("    ");
		
		System.out.print("cost:");
		System.out.print(this.cost);
		System.out.println("    ");
		
		if (route!=null) {
			System.out.println("Route:");
			for (Entry<Integer, ArrayList> es: route.entrySet()) {
				System.out.print("V_No:");
				System.out.print(es.getKey());
				System.out.print("  ");

				for (int dx=0;dx<es.getValue().size();dx++) {
					System.out.print(es.getValue().get(dx));
					if (dx!=es.getValue().size()-1)
						System.out.print("->");
					else 
						System.out.println();
				}
			}

		}

	}
	
	public Gen(double indivaual[]) {
		
		this.indivaual=indivaual;
	}
	
	public Gen(double indivaual[],FitnessUnit cu) {
		this.fu=cu;
		this.indivaual=indivaual;
	}
	@Override
	public void calFitness() {
		// TODO Auto-generated method stub
		
		fitness=fu.FitnessCal(this);
	}


}
