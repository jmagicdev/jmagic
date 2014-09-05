package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Ruinous Minotaur")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.MINOTAUR})
@ManaCost("1RR")
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class RuinousMinotaur extends Card
{
	public static final class RuinYou extends EventTriggeredAbility
	{
		public RuinYou(GameState state)
		{
			super(state, "Whenever Ruinous Minotaur deals damage to an opponent, sacrifice a land.");

			this.addPattern(whenDealsDamageToAnOpponent(ABILITY_SOURCE_OF_THIS));

			this.addEffect(sacrifice(You.instance(), 1, LandPermanents.instance(), "Sacrifice a land"));
		}
	}

	public RuinousMinotaur(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(2);

		// Whenever Ruinous Minotaur deals damage to an opponent, sacrifice a
		// land.
		this.addAbility(new RuinYou(state));
	}
}
