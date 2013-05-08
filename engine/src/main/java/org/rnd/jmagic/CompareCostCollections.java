package org.rnd.jmagic;

import org.rnd.jmagic.sanitized.*;

public class CompareCostCollections implements java.util.Comparator<SanitizedCostCollection>, java.io.Serializable
{
	private static final CompareManaPools manaPoolComparator = new CompareManaPools();
	private static final long serialVersionUID = 1L;

	@Override
	public int compare(SanitizedCostCollection a, SanitizedCostCollection b)
	{
		// Fewer events before more events
		if(a.events.size() != b.events.size())
			return b.events.size() - a.events.size();

		return manaPoolComparator.compare(a.manaCost, b.manaCost);
	}
}
