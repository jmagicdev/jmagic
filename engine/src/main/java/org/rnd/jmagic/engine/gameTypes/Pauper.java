package org.rnd.jmagic.engine.gameTypes;

import org.rnd.jmagic.engine.*;

@Name("Pauper")
@Description("All rares and uncommons are disallowed.")
public class Pauper extends GameType.SimpleGameTypeRule
{
	@Override
	public boolean checkCard(String card)
	{
		for(Expansion expansion: Expansion.list())
		{
			Rarity rarity = expansion.getRarity(card);
			if(rarity != null && rarity.ordinal() <= Rarity.COMMON.ordinal())
				return true;
		}

		return false;
	}
}
