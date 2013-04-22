package org.rnd.jmagic;

import org.rnd.jmagic.engine.*;

public class CompareManaPools implements java.util.Comparator<ManaPool>, java.io.Serializable
{
	private static final long serialVersionUID = 1L;

	@Override
	public int compare(ManaPool a, ManaPool b)
	{
		// bigger pools later
		if(a.converted() != b.converted())
			return a.converted() - b.converted();

		java.util.Iterator<ManaSymbol> x = a.iterator();
		java.util.Iterator<ManaSymbol> y = b.iterator();

		while(true)
		{
			// ensure equivalent pools compare properly
			if(!x.hasNext() && !y.hasNext())
				return 0;

			// Shorter before longer; i.e., "(6)" > "(4)(R)"
			if(!x.hasNext())
				return 1;
			if(!y.hasNext())
				return -1;

			ManaSymbol s = x.next();
			ManaSymbol t = y.next();
			if(s.toString().equals(t.toString()))
				continue;

			return s.compareTo(t);
		}
	}
}