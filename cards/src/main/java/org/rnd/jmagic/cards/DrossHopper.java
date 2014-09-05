package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Dross Hopper")
@Types({Type.CREATURE})
@SubTypes({SubType.INSECT, SubType.HORROR})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class DrossHopper extends Card
{
	public static final class DrossHopperAbility0 extends ActivatedAbility
	{
		public DrossHopperAbility0(GameState state)
		{
			super(state, "Sacrifice a creature: Dross Hopper gains flying until end of turn.");
			this.addCost(sacrificeACreature());
			this.addEffect(addAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.Flying.class, "Dross Hopper gains flying until end of turn."));
		}
	}

	public DrossHopper(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Sacrifice a creature: Dross Hopper gains flying until end of turn.
		this.addAbility(new DrossHopperAbility0(state));
	}
}
