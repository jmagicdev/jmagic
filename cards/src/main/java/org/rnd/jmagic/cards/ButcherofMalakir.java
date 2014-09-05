package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Butcher of Malakir")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.VAMPIRE})
@ManaCost("5BB")
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.RARE), @Printings.Printed(ex = Worldwake.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class ButcherofMalakir extends Card
{
	public static final class ButcherofMalakirAbility1 extends EventTriggeredAbility
	{
		public ButcherofMalakirAbility1(GameState state)
		{
			super(state, "Whenever Butcher of Malakir or another creature you control dies, each opponent sacrifices a creature.");
			this.addPattern(whenThisIsPutIntoAGraveyardFromTheBattlefield());
			this.addPattern(whenXIsPutIntoAGraveyardFromTheBattlefield(RelativeComplement.instance(CREATURES_YOU_CONTROL, AbilitySource.instance(This.instance()))));

			this.addEffect(sacrifice(OpponentsOf.instance(You.instance()), 1, CreaturePermanents.instance(), "Each opponent sacrifices a creature."));
		}
	}

	public ButcherofMalakir(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever Butcher of Malakir or another creature you control is put
		// into a graveyard from the battlefield, each opponent sacrifices a
		// creature.
		this.addAbility(new ButcherofMalakirAbility1(state));
	}
}
