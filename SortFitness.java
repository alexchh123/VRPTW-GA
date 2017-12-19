package GA;

import java.util.Comparator;

public class SortFitness<T> implements Comparator<T> {

	@Override
	public int compare(T o1, T o2) {
		// TODO Auto-generated method stub
		
		int rs=0;
		if (!(o1.getClass().isInstance(Gen.class) && o2.getClass().isInstance(Gen.class))) {
			Gen i1= (Gen)o1;
			Gen i2= (Gen)o2;
			rs= (int)(i2.fitness-i1.fitness);
		}
		return rs;
	}


}
