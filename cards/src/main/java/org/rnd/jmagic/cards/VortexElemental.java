package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Vortex Elemental")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("U")
@ColorIdentity({Color.BLUE})
public final class VortexElemental extends Card
{
	public static final class VortexElementalAbility0 extends ActivatedAbility
	{
		public VortexElementalAbility0(GameState state)
		{
			super(state, "(U): Put Vortex Elemental and each creature blocking or blocked by it on top of their owners' libraries, then those players shuffle their libraries.");
			this.setManaCost(new ManaPool("(U)"));

			SetGenerator blockingThis = Blocking.instance(ABILITY_SOURCE_OF_THIS);
			SetGenerator blockedByThis = BlockedBy.instance(ABILITY_SOURCE_OF_THIS);
			SetGenerator stuff = Union.instance(blockingThis, blockedByThis);
			this.addEffect(putOnTopOfLibrary(stuff, "Put Vortex Elemental and each creature blocking or blocked by it on top of their owners' libraries,"));

			SetGenerator thosePlayers = OwnerOf.instance(Union.instance(ABILITY_SOURCE_OF_THIS, stuff));
			this.addEffect(shuffleLibrary(thosePlayers, "then those players shuffle their libraries."));
		}
	}

	public static final class VortexElementalAbility1 extends ActivatedAbility
	{
		public VortexElementalAbility1(GameState state)
		{
			super(state, "(3)(U)(U): Target creature blocks Vortex Elemental this turn if able.");
			this.setManaCost(new ManaPool("(3)(U)(U)"));
		}
	}

	public VortexElemental(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(1);

		// (U): Put Vortex Elemental and each creature blocking or blocked by it
		// on top of their owners' libraries, then those players shuffle their
		// libraries.
		this.addAbility(new VortexElementalAbility0(state));

		// (3)(U)(U): Target creature blocks Vortex Elemental this turn if able.
		this.addAbility(new VortexElementalAbility1(state));
	}
}
