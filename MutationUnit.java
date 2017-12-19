package GA;

import GA.Const.CONST;

public class MutationUnit {

	public void Mutation(Gen ind) {

		double rnd = Math.random();
		// swapMutate(ind);

		if (rnd <= CONST.MUTATION_RATE) {
			// swapMutate(ind);
			// inverseMutate(ind);
			
			for (int i=0;i<100;i++){
				swapMutate(ind);
				
//				if (Math.random()<0.001) {
//					updateToZero(ind);
//				}
			}
			

		}
	}

	private void swapMutate(Gen ind) {

		int a2 = 0;
		a2 = ind.indivaual.length - 1;

		int b1 = 0;
		int b2 = 0;
		while (b1 == b2) {
			b1 = (int) Math.ceil(Math.random() * (a2));
			b2 = (int) Math.ceil(Math.random() * (a2));
		}
		double temp = ind.indivaual[b1];
		ind.indivaual[b1] = ind.indivaual[b2];
		ind.indivaual[b2] = temp;
	}

	private void updateMu(Gen ind) {

		int a2 = 0;
		a2 = ind.indivaual.length - 1;

		int b1 = 0;
		int b2 = 0;
		while (b1 == b2) {
			b1 = (int) Math.ceil(Math.random() * (a2));
			b2 = (int) Math.ceil(Math.random() * (a2));
		}
		ind.indivaual[b1] = getNum();
		ind.indivaual[b2] = getNum();

	}
	
	private void updateToZero(Gen ind) {

		int a2 = 0;
		a2 = ind.indivaual.length - 1;

		int b1 = 0;
		int b2 = 0;
		while (b1 == b2) {
			b1 = (int) Math.ceil(Math.random() * (a2));
			b2 = (int) Math.ceil(Math.random() * (a2));
		}
		ind.indivaual[b1] = 0;
//		ind.indivaual[b2] = 0;

	}
	
	private void muadd(Gen ind) {

		int a2 = 0;
		a2 = ind.indivaual.length - 1;

		int b1 = 0;
		int b2 = 0;
		while (b1 == b2) {
			b1 = (int) Math.ceil(Math.random() * (a2));
			b2 = (int) Math.ceil(Math.random() * (a2));
		}
		ind.indivaual[b1] = (ind.indivaual[b1]+ind.indivaual[b2])% CONST.IND.length;
		ind.indivaual[b2] = 0;

	}

	private double getNum() {
		int rx = 0;
		int len = CONST.IND.length;
		rx = (int) (Math.random() * (len));
		return CONST.IND[rx];

	}

	private void inverseMutate(Gen ind) {

		int a2 = 0;
		a2 = ind.indivaual.length - 1;

		int b1 = 0;
		int b2 = 0;
		while (b1 == b2) {
			b1 = (int) Math.ceil(Math.random() * (a2));
			b2 = (int) Math.ceil(Math.random() * (a2));
		}
		ind.indivaual[b1] = BinaryInverse(ind.indivaual[b1]);
		ind.indivaual[b2] = BinaryInverse(ind.indivaual[b2]);

	}

	private int BinaryInverse(double num) {
		if (num == 0.0) {
			return 1;
		} else {
			return 0;
		}
	}
}
