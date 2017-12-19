package GA;

import java.util.List;

import GA.Const.CONST;

public class SelectionUnit {

	public List<Gen> SelectionElite(List<Gen> individuals) {
		
		if (CONST.SELECT_ELIT_BST < individuals.size()) {
			individuals.removeAll(individuals.subList(CONST.SELECT_ELIT_BST, individuals.size()-1));
		}
		return individuals;
	};
}
