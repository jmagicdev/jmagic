package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Marrow Bats")
@Types({Type.CREATURE})
@SubTypes({SubType.BAT, SubType.SKELETON})
@ManaCost("4B")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class MarrowBats extends Card
{
	public static final class MarrowBatsAbility1 extends ActivatedAbility
	{
		public MarrowBatsAbility1(GameState state)
		{
			super(state, "Pay 4 life: Regenerate Marrow Bats.");
			this.addCost(payLife(You.instance(), 4, "Pay 4 life."));
			this.addEffect(regenerate(ABILITY_SOURCE_OF_THIS, "Regenerate Marrow Bats."));
		}
	}

	public MarrowBats(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Pay 4 life: Regenerate Marrow Bats.
		this.addAbility(new MarrowBatsAbility1(state));
	}
}
