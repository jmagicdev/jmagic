package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to any {@link GameObject} in a {@link GameState} which has a
 * {@link ManaSymbol} with some quality in its cost. The way symbols are
 * compared is that any quality in the check symbol is used as a positive
 * quality to check for in any symbol in {@link GameObject#getManaCost()}. For
 * example, if the {@link ManaSymbol#isSnow} is set in the check symbol, at
 * least one {@link ManaSymbol} in {@link GameObject#getManaCost()} must also
 * have {@link ManaSymbol#isSnow} set. If {@link ManaSymbol#isSnow} is not set,
 * however, it doesn't matter if {@link ManaSymbol#isSnow} is set in any of
 * {@link GameObject#getManaCost()}.
 */
public final class HasInManaCost extends SetGenerator
{
	public static SetGenerator instance(ManaSymbol symbol)
	{
		return new HasInManaCost(symbol);
	}

	private ManaSymbol checkAgainst;

	private HasInManaCost(ManaSymbol symbol)
	{
		this.checkAgainst = symbol;
	}

	private boolean checkSymbol(ManaSymbol symbol)
	{
		// According to our judge, we don't care that searching for (1) won't
		// find (2) or (2/R)
		boolean checkColorless = (0 < this.checkAgainst.colorless);
		if(checkColorless && (symbol.colorless != this.checkAgainst.colorless))
			return false;

		boolean checkColors = !this.checkAgainst.colors.isEmpty();
		if(checkColors && !symbol.colors.containsAll(this.checkAgainst.colors))
			return false;

		if(this.checkAgainst.isPhyrexian && !symbol.isPhyrexian)
			return false;

		if(this.checkAgainst.isSnow && !symbol.isSnow)
			return false;

		if(this.checkAgainst.isX && !symbol.isX)
			return false;

		return true;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		for(GameObject o: state.getAllObjects())
		{
			for(ManaPool manaCost: o.getManaCost())
			{
				if(null == manaCost)
					continue;

				for(ManaSymbol s: manaCost)
					if(checkSymbol(s))
					{
						ret.add(o);
						break;
					}
			}
		}
		return ret;
	}
}