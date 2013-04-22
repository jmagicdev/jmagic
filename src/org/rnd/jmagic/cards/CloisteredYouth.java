package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Cloistered Youth")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
@BackFace(UnholyFiend.class)
public final class CloisteredYouth extends Card
{
	public static final class GetScary extends EventTriggeredAbility
	{
		public GetScary(GameState state)
		{
			super(state, "At the beginning of your upkeep, you may transform Cloistered Youth.");
			this.addPattern(atTheBeginningOfYourUpkeep());
			this.addEffect(youMay(transformThis("Cloistered Youth"), "You may transform Cloistered Youth."));
		}
	}

	public CloisteredYouth(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new GetScary(state));
	}
}
