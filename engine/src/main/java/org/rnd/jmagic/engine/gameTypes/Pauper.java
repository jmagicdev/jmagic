package org.rnd.jmagic.engine.gameTypes;

import org.rnd.jmagic.engine.*;

@Name("Pauper")
@Description("All rares and uncommons are disallowed.")
public class Pauper extends GameType.SimpleGameTypeRule
{
	@Override
	public boolean checkCard(Class<? extends Card> card)
	{
		Printings printings = card.getAnnotation(Printings.class);
		for(Printings.Printed printing: printings.value())
			if(printing.r().equals(Rarity.COMMON))
				return true;
		return false;
	}
}
