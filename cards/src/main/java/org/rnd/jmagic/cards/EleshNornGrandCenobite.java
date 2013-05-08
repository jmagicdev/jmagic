package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Elesh Norn, Grand Cenobite")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.PRAETOR})
@ManaCost("5WW")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.MYTHIC)})
@ColorIdentity({Color.WHITE})
public final class EleshNornGrandCenobite extends Card
{
	public static final class EleshNornGrandCenobiteAbility1 extends StaticAbility
	{
		public EleshNornGrandCenobiteAbility1(GameState state)
		{
			super(state, "Other creatures you control get +2/+2.");
			this.addEffectPart(modifyPowerAndToughness(RelativeComplement.instance(CREATURES_YOU_CONTROL, This.instance()), +2, +2));
		}
	}

	public static final class EleshNornGrandCenobiteAbility2 extends StaticAbility
	{
		public EleshNornGrandCenobiteAbility2(GameState state)
		{
			super(state, "Creatures your opponents control get -2/-2.");
			this.addEffectPart(modifyPowerAndToughness(Intersect.instance(ControlledBy.instance(OpponentsOf.instance(You.instance())), CreaturePermanents.instance()), -2, -2));
		}
	}

	public EleshNornGrandCenobite(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(7);

		// Vigilance
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));

		// Other creatures you control get +2/+2.
		this.addAbility(new EleshNornGrandCenobiteAbility1(state));

		// Creatures your opponents control get -2/-2.
		this.addAbility(new EleshNornGrandCenobiteAbility2(state));
	}
}
