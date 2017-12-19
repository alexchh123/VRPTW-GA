package GA;

import java.util.List;

import GA.Const.CONST;

public class CrossoverUnit {
	
	public Gen Crossover(List<Gen> list1) {
		
		try {
		double rnd = Math.random();

		if (list1!=null && list1.size()>0 && list1.get(0)!=null) {
			if (rnd <=CONST.CROSSOVER_RATE) {
				
					int a2=0;
	
						a2=list1.size()-1;
	
					
					int b1=0;
					int b2=0;
					int total=0;
					while (b1==b2 && total<20) {
						b1=(int)Math.ceil(Math.random()*(a2));
						b2=(int)Math.ceil(Math.random()*(a2));
						total++;
					}
					return Onepoint(list1.get(b1),list1.get(b2));
			}else {
				return list1.get(0);
			}
		} 
		return list1.get(0);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	private Gen Onepoint(Gen i1, Gen i2) {

		int loc=CONST.CROSSOVER_DGT;
		
		double[] indivdials1=i1.indivaual;
		double[] indivdials2=i2.indivaual;
		
		double[] indivdials3 = new double[indivdials2.length];
		
		for (int i=0;i<indivdials3.length;i++) {
			if (i<loc) {
				indivdials3[i] = indivdials1[i];
			} else {
				indivdials3[i] = indivdials2[i];
			}
		}
		
		Gen newone= new Gen(indivdials3,i1.fu);
		newone.calFitness();

		return newone;
	}

}
