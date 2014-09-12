package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Sliver Hivelord")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.SLIVER})
@ManaCost("WUBRG")
@ColorIdentity({Color.RED, Color.WHITE, Color.BLUE, Color.BLACK, Color.GREEN})
public final class SliverHivelord extends Card
{
	public static final class SliverHivelordAbility0 extends StaticAbility
	{
		public SliverHivelordAbility0(GameState state)
		{
			super(state, "Sliver creatures you control have indestructible.");
			this.addEffectPart(addAbilityToObject(SLIVER_CREATURES_YOU_CONTROL, org.rnd.jmagic.abilities.keywords.Indestructible.class));
		}
	}

	public SliverHivelord(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Sliver creatures you control have indestructible. (Damage and effects
		// that say "destroy" don't destroy them.)
		this.addAbility(new SliverHivelordAbility0(state));
	}
}
