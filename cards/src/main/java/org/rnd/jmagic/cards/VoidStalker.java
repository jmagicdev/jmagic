package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Void Stalker")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class VoidStalker extends Card
{
	public static final class VoidStalkerAbility0 extends ActivatedAbility
	{
		public VoidStalkerAbility0(GameState state)
		{
			super(state, "(2)(U), (T): Put Void Stalker and target creature on top of their owners' libraries, then those players shuffle their libraries.");
			this.setManaCost(new ManaPool("(2)(U)"));
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			SetGenerator thisAndThat = Union.instance(ABILITY_SOURCE_OF_THIS, target);
			this.addEffect(putOnTopOfLibrary(thisAndThat, "Put Void Stalker and target creature on top of their owners' libraries,"));

			this.addEffect(shuffleLibrary(OwnerOf.instance(thisAndThat), "then those players shuffle their libraries."));
		}
	}

	public VoidStalker(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// (2)(U), (T): Put Void Stalker and target creature on top of their
		// owners' libraries, then those players shuffle their libraries.
		this.addAbility(new VoidStalkerAbility0(state));
	}
}
