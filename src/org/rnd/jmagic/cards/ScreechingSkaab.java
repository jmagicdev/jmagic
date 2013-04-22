package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Screeching Skaab")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class ScreechingSkaab extends Card
{
	public static final class ScreechingSkaabAbility0 extends EventTriggeredAbility
	{
		public ScreechingSkaabAbility0(GameState state)
		{
			super(state, "When Screeching Skaab enters the battlefield, put the top two cards of your library into your graveyard.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(millCards(You.instance(), 2, "Put the top two cards of your library into your graveyard."));
		}
	}

	public ScreechingSkaab(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// When Screeching Skaab enters the battlefield, put the top two cards
		// of your library into your graveyard.
		this.addAbility(new ScreechingSkaabAbility0(state));
	}
}
