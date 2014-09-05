package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Veilborn Ghoul")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("4B")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class VeilbornGhoul extends Card
{
	public static final class VeilbornGhoulAbility1 extends EventTriggeredAbility
	{
		public VeilbornGhoulAbility1(GameState state)
		{
			super(state, "Whenever a Swamp enters the battlefield under your control, you may return Veilborn Ghoul from your graveyard to your hand.");

			SimpleZoneChangePattern pattern = new SimpleZoneChangePattern(null, Battlefield.instance(), HasSubType.instance(SubType.SWAMP), You.instance(), false);
			this.addPattern(pattern);

			this.addEffect(youMay(putIntoHand(ABILITY_SOURCE_OF_THIS, You.instance(), "Return Veilborn Ghoul from your graveyard to your hand.")));

			this.triggersFromGraveyard();
		}
	}

	public VeilbornGhoul(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(1);

		// Veilborn Ghoul can't block.
		this.addAbility(new org.rnd.jmagic.abilities.CantBlock(state, this.getName()));

		// Whenever a Swamp enters the battlefield under your control, you may
		// return Veilborn Ghoul from your graveyard to your hand.
		this.addAbility(new VeilbornGhoulAbility1(state));
	}
}
