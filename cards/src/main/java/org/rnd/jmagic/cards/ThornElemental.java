package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Thorn Elemental")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("5GG")
@Printings({@Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.SEVENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.STARTER, r = Rarity.RARE), @Printings.Printed(ex = Expansion.URZAS_DESTINY, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class ThornElemental extends Card
{
	public static final class ThornElementalAbility0 extends StaticAbility
	{
		public ThornElementalAbility0(GameState state)
		{
			super(state, "You may have Thorn Elemental assign its combat damage as though it weren't blocked.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.DEAL_DAMAGE_AS_THOUGH_UNBLOCKED);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			this.addEffectPart(part);
		}
	}

	public ThornElemental(GameState state)
	{
		super(state);

		this.setPower(7);
		this.setToughness(7);

		// You may have Thorn Elemental assign its combat damage as though it
		// weren't blocked.
		this.addAbility(new ThornElementalAbility0(state));
	}
}
