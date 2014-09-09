package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Daring Skyjek")
@Types({Type.CREATURE})
@SubTypes({SubType.KNIGHT, SubType.HUMAN})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class DaringSkyjek extends Card
{
	public static final class DaringSkyjekAbility0 extends EventTriggeredAbility
	{
		public DaringSkyjekAbility0(GameState state)
		{
			super(state, "Whenever Daring Skyjek and at least two other creatures attack, Daring Skyjek gains flying until end of turn.");
			this.addPattern(battalion());
			this.addEffect(addAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.Flying.class, "Daring Skyjek gains flying until end of turn."));
		}
	}

	public DaringSkyjek(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		// Battalion \u2014 Whenever Daring Skyjek and at least two other
		// creatures attack, Daring Skyjek gains flying until end of turn.
		this.addAbility(new DaringSkyjekAbility0(state));
	}
}
