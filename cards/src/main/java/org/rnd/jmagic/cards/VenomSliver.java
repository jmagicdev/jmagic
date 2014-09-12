package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Venom Sliver")
@Types({Type.CREATURE})
@SubTypes({SubType.SLIVER})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class VenomSliver extends Card
{
	public static final class VenomSliverAbility0 extends StaticAbility
	{
		public VenomSliverAbility0(GameState state)
		{
			super(state, "Sliver creatures you control have deathtouch.");
			this.addEffectPart(addAbilityToObject(SLIVER_CREATURES_YOU_CONTROL, org.rnd.jmagic.abilities.keywords.Deathtouch.class));
		}
	}

	public VenomSliver(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Sliver creatures you control have deathtouch. (Any amount of damage a
		// creature with deathtouch deals to a creature is enough to destroy
		// it.)
		this.addAbility(new VenomSliverAbility0(state));
	}
}
