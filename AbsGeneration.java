package GA.Abs;

import java.util.List;

import GA.Gen;
import GA.Const.CONST;

public abstract class AbsGeneration implements CONST {

	public int NO=0;
	
	public abstract void init(List<Gen> indvs);
	
	public abstract void crossover();
	
	public abstract void mutation();

	public abstract void selection();

	public abstract AbsGeneration generate();
	
	public abstract AbsGeneration repuduct();

}
	

