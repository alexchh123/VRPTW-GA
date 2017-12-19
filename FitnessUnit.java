package GA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import GA.Const.CONST;
import GA.Const.CONST_PARA;

public class FitnessUnit {
	private double[][] depot = { { 0, 0, 0, 0, 0, 0 } };
	private double[][] data = CONST.DATA;
	private CONST_PARA CR;
	
//	public FitnessUnit() {
//		CR = new CONST_PARA();
//	}
	public FitnessUnit(CONST_PARA CONST) {
		CR = CONST;
	}
	
	public double FitnessCal(Gen inv) {
		FitnessUnit f1 = new FitnessUnit(CR);
		return f1.fitnessVRP(inv);
	}
	


	
	

	private double fitness13(Gen inv) {

		String x1[] = new String[CR.VARIABLE_NUM];
		for (int m = 0; m < CR.VARIABLE_NUM; m++) {
			x1[m] = "";
			for (int j = 0; j < CR.DIGITS_NUM; j++) {
				x1[m] += Integer.toString(
						(int) inv.indivaual[m * CONST.DIGITS_NUM + j]);
			}
		}

		int[] i1 = new int[CR.VARIABLE_NUM];
		for (int n = 0; n < CR.VARIABLE_NUM; n++) {
			i1[n] = Integer.parseInt(x1[n], 2);
		}

		switch (CR.VARIABLE_NUM) {

		case 1:
			// return fitness4(i1[0]);
		case 2:
			return fitness3(i1[0], i1[1]);
		case 3:
			return fitness4(i1[0], i1[1], i1[2]);
		}
		return -100000;
	}

	private double fitness12(Gen inv) {

		String x1 = "";
		String x2 = "";
		for (int j = 0; j < 10; j++) {
			x1 += Integer.toString((int) inv.indivaual[j]);
		}
		for (int m = 10; m < inv.indivaual.length; m++) {
			x2 += Integer.toString((int) inv.indivaual[m]);
		}

		int i1 = Integer.parseInt(x1, 2);
		int i2 = Integer.parseInt(x2, 2);

		return fitness3(i1, i2);
	}

	private double fitness2(int x1, int x2) {

		double f = -100000.0;
		if (x1 + x2 >= 5 && x1 + 2 * x2 >= 6) {
			f = -1 * (x1 + x2);
		}

		return f;

	}

	private double fitness3(double x1, double x2) {

		double f = -1 * (2 * x1 + 3 * x2);
		;

		if (x1 <= 300 || x2 <= 600) {
			// f+=200;
			if (x1 + x2 >= 350 && 2 * x1 + x2 <= 600) {
				f += 600;
			} else {
				f -= 1000;
			}

		}

		// double f=-100000.0;
		// if (x1+x2>=5 && x1+2*x2>=6) {
		// f=-1*(x1+x2);
		// }

		return f;

	}

	private double fitness4(double x1, double x2, double x3) {

		double f = -1 * (x1 * x1 * x1 + 2 * x2 * x2 * x3 + 2 * x3);

		if (x1 * x1 - x2 + 2 * x3 <= 2 && x1 * x1 + x2 + x3 == 4) {
			f += 500;
		} else {
			f -= 1000;
		}

		return f;

	}

	private double fitnessVRP(Gen inv) {

		double[] sera=inv.indivaual;
		
		double BigF = -10000.0;
		double fitness = 0;

		double a1 = 1;
		double a2 = 1;
		double a3 = 1;

		// 0. constraints
		// Check number of vehicle<max number
		if (numOfVechile(inv.indivaual) > CR.K_MAX_V) {
			fitness = BigF*(numOfVechile(inv.indivaual)-CR.K_MAX_V);
			// return fitness;
		}

		// Check impossible route and service times for one node
		double co=checkOnceEachNode(inv.indivaual);
		if (co != 0) {
			fitness = BigF*(co/10);
			// return fitness;
		}

//		// cover all customers
//		if (!isCoverAll(inv.indivaual)) {
//			fitness = BigF;
//			// return fitness;
//		} 

		// Capacity Check;
		double ov=getCapacity(inv.indivaual);
		if (ov != 0) {
			fitness = BigF*(ov/CR.CARRYING_CAPACITY);
			// return fitness;
		} 

		// satisfaction Check;(soft)
		if (!checkSatisfication(inv.indivaual)) {
			fitness += BigF*calSatisfactionCost(inv.indivaual);
			// return fitness;
		} 
		
		// constraint variance <0.05
		double con=constraint_variance(inv.indivaual);
		if (con>0) {
			fitness += BigF*con;
		}
		

		double cost = 0;

		inv.numofVehicles=this.numOfVechile(inv.indivaual);
		inv.route=getRoute(inv.indivaual);
		inv.satisfication=this.getAverageStc(inv.indivaual);
		inv.variance=con;
		inv.distance=getTotalDistance(inv.route);
		double tt= calDistance(inv.indivaual,1, inv.indivaual.length - 1,1);
		
		if (fitness == 0) {
			
			cost += a1
					* calDistanceCost(inv.indivaual, inv.indivaual.length - 1);
			cost += a2 * calVehicleCost(inv.indivaual);
			cost += a3 * calSatisfactionCost(inv.indivaual);
			

			fitness = -cost / 100.0;
		} 
		inv.cost=cost;

		return fitness;

	}

	private int numOfVechile(double[] ind) {

		int total = 0;
		if (ind != null) {
			for (int i = 0; i < ind.length - 1; i++) {
				if (ind[i] == 0 && ind[i + 1] != 0) {
					total++;
				}
			}
		}
//		if (ind[0]==0) {
//			total--;
//		}

		return total;
	}

	private boolean isImp(double[] ind) {

		boolean total = false;
		for (int i = 0; i < ind.length - 1; i++) {
			// for (int j = i + 1; j < ind.length; j++) {
			if (ind[i] == ind[i + 1] && ind[i] != 0) {
				total = true;
				// }
			}
			// if (ind[i] == ind[i+ 1] && ind[i] == 0) {
			// total = true;
			// }
		}
		return total;
	}

	private boolean isCoverAll(double[] ind) {

		boolean total = false;
		for (int i = 1; i < CR.IND.length; i++) {
			total = false;
			for (int j = 0; j < ind.length; j++) {
				if (CR.IND[i] == ind[j]) {
					total = true;
					break;
				}
			}
			if (!total) {
				break;
			}
		}

		return total;
	}

	private double getCapacity(double[] ind) {

		boolean total = true;
		double weight = 0;
		
		double overw=0;

		int vs = 1;
		ArrayList tl = new ArrayList();
		if (ind != null) {
			for (int i = 1; i < ind.length - 1; i++) {
				if (ind[i] == 0 && ind[i + 1] != 0) {
					vs++;
					if (weight > CR.CARRYING_CAPACITY) {
						// total =false;
						overw+=weight-CR.CARRYING_CAPACITY;
					}
					weight = 0;
					tl.clear();
				} else {
					tl.add(data[(int) ind[i]][3]);
					weight += data[(int) ind[i]][3];
				}
				
			}
		}
//		
		if (weight > CR.CARRYING_CAPACITY) {
			// total =false;
			overw+=weight-CR.CARRYING_CAPACITY;
		}

		return overw;
	}

	private double checkOnceEachNode(double[] ind) {

		double flg = 0;
		int num = 0;
		for (double j : CR.IND) {
			num = 0;
			if (j != 0) {
				for (int i = 0; i < ind.length; i++) {
					if (j == ind[i]) {
						num++;
					}
				}
			}
			if (j != 0 && num != 1) {
				flg++;
			}
		}

		return flg;
	}
	
	private HashMap<Integer, ArrayList> getRoute(double[] ind) {
		
		HashMap<Integer, ArrayList> rs = new HashMap<Integer, ArrayList>();
		int vs = 0;
		ArrayList tl = new ArrayList();
		if (ind != null) {
			for (int i = 0; i < ind.length - 1; i++) {
				if (ind[i] == 0 && ind[i + 1] != 0) {
					if (vs!=0) {
						tl.add(0);
						rs.put(vs++, (ArrayList) tl.clone());
						tl = new ArrayList();
						tl.add(0);
					} else {
						tl.add(0);
						vs++;
					}

				} else {
					if (data[(int) ind[i]][0]!=0)
						tl.add((int)data[(int) ind[i]][0]);
				}
			}
		}
		

		if (!tl.isEmpty()) {
			
			if ((int)data[(int) ind[ind.length - 1]][0]!=0) {
				tl.add((int)data[(int) ind[ind.length - 1]][0]);
			}
			tl.add(0);
			rs.put(vs++, (ArrayList) tl.clone());
		}
//		rs.get(vs-1).add(0);
		return rs;
	}

	
	private boolean checkSatisfication(double[] ind) {

		boolean total = false;
		double ts = 0;
		int add = 0;

		HashMap<Integer, ArrayList<Double>> vmap = new HashMap<Integer, ArrayList<Double>>();
		int vv = 1;
		// vmap.put(vv, value)
		int offshet = 1;
		for (int i = 0; i < ind.length; i++) {
			int id = (int) ind[i];
			// add=0;
			if (id <= CR.IND.length - 1 && id > 0) {
				double dis = this.calDistance(ind, offshet, i, 0);
				double time = (int) dis / CR.SPEED;

				double delaytime = time - (data[id][4] + CR.TWN);

				if (delaytime < 0) {
					delaytime = 0;
				}

				ts += (CR.TWN / (CR.TWN + delaytime));
				add++;
			} else {
				offshet = i + 1;
			}
		}

		double st = ts / add;
		if (st > CR.RATE_SAT) {
			total = true;
		}
		return total;
	}

	private double calDistance(double[] ind, int beg, int ex, int flg) {

		double dis = 0;

		int l1 = (int) ind[beg - 1];
		dis += Math.sqrt((data[l1][1] - data[0][1]) * (data[l1][1] - data[0][1])
				+ (data[l1][2] - data[0][2]) * (data[l1][2] - data[0][2]));

		for (int i = beg; i <= ex; i++) {
			int id1 = (int) ind[i];
			int id2 = (int) ind[i - 1];
			if (id1 < data.length) {
				dis += Math.sqrt((data[id1][1] - data[id2][1])
						* (data[id1][1] - data[id2][1])
						+ (data[id1][2] - data[id2][2])
								* (data[id1][2] - data[id2][2]));
			}
		}
		int l2 = (int) ind[ind.length - 1];
		if (flg == 1) {
			dis += Math.sqrt((data[l2][1] - data[0][1])
					* (data[l2][1] - data[0][1])
					+ (data[l2][2] - data[0][2]) * (data[l2][2] - data[0][2]));
		}

		return dis;
	}
	
	private double getTotalDistance(HashMap<Integer, ArrayList> route) {

		double total=0.0;
		for (Entry<Integer, ArrayList> r:route.entrySet()) {
			ArrayList<Integer> value=r.getValue();
			for (int i=0;i<value.size()-1;i++) {
				int x1=(int)value.get(i);
				int x2=(int)value.get(i+1);
				double[] a={data[x1][1],data[x1][2]};
				double[] b={data[x2][1],data[x2][2]};
				
				total += this.getDistance(a,b);
			}
						
		}
		
		return total;
	}
	
	private double getDistance(double[] a, double[] b) {
		
		return Math.sqrt(Math.pow((a[0] - b[0]), 2)
				+ Math.pow((a[1] - b[1]), 2));
		
	}
	

	private double calDistanceCost(double[] ind, int ex) {

		return calDistance(ind, 1, ex, 1) * CR.COST_DIS;
	}

	private double calVehicleCost(double[] ind) {

		double vs = numOfVechile(ind);

		return vs * CR.COST_DIS;
	}

	private double calSatisfactionCost(double[] ind) {

		double eariliess_time = 0;
		double tardiness_time = 0;

		int offsets = 1;
		for (int i = 0; i < ind.length; i++) {
			int id = (int) ind[i];
			if (id > 0 && id < data.length) {
				double dis = this.calDistance(ind, offsets, i, 0);
				double time = (int) dis / CR.SPEED;
				if (data[id][4] > time) {
					eariliess_time += time;
				} else if ((data[id][4] + CR.TWN) < time) {
					tardiness_time += time;
				}
			} else {
				offsets = i + 1;
			}
		}

		return eariliess_time * CR.EARLINESS_COST
				+ tardiness_time * CR.TARDINESS_COST;

	}

	private double constraint_variance(double[] ind) {
		
		int i=0;
		double ave=this.getAverageStc(ind);
		double flg=0.0;
		for (double j:ind)  {
			double sn= getSatisfaction(ind,i++);
			if (Math.pow((sn-ave), 2)>= CR.VARIANCEL_SATISFACTION) {
				flg+=Math.pow((sn-ave), 2)- CR.VARIANCEL_SATISFACTION;
			}
		}
		return flg;
	}
	
	
	private double getSatisfaction(double[] ind, int i) {
	
		double ts = 0;
		int offshet = 1;

		double dis = this.calDistance(ind, offshet, i, 0);
		double time = (int) dis / CR.SPEED;

		double delaytime = time - (data[(int)ind[i]][4] + CR.TWN);

		if (delaytime<0) {
			delaytime=0;
		}
		
		ts = (CR.TWN / (CR.TWN + delaytime));


		return ts;

	}
	
	private double getAverageStc(double[] ind) {
		
		double sum=0.0;
		int nodes=0;
		for (double v:ind) {
			if (v!=0) {
				sum+=getSatisfaction(ind,nodes++);
			}			
		}
	
		return sum/(nodes);
	}
	
	
	
	private static double RateOfSatisfaction;
	public static double getRateOfSatisfaction() {
		return RateOfSatisfaction;
	}

	public static void setRateOfSatisfaction(double rateOfSatisfaction) {
		RateOfSatisfaction = rateOfSatisfaction;
	}



	private static double TimeWindow;

	public static double getTimeWindow() {
		return TimeWindow;
	}

	public static void setTimeWindow(double timeWindow) {
		TimeWindow = timeWindow;
	}
	
	
	
}
