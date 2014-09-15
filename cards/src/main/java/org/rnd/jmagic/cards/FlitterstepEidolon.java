package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Flitterstep Eidolon")
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class FlitterstepEidolon extends Card
{
	public static final class FlitterstepEidolonAbility1 extends StaticAbility
	{
		public FlitterstepEidolonAbility1(GameState state)
		{
			super(state, "Flitterstep Eidolon can't be blocked.");
			this.addEffectPart(unblockable(This.instance()));
		}
	}

	public static final class FlitterstepEidolonAbility2 extends StaticAbility
	{
		public FlitterstepEidolonAbility2(GameState state)
		{
			super(state, "Enchanted creature gets +1/+1 and can't be blocked.");
			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), +1, +1));
			this.addEffectPart(unblockable(EnchantedBy.instance(This.instance())));
		}
	}

	public FlitterstepEidolon(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Bestow (5)(U) (If you cast this card for its bestow cost, it's an
		// Aura spell with enchant creature. It becomes a creature again if it's
		// not attached to a creature.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Bestow(state, "(5)(U)"));

		// Flitterstep Eidolon can't be blocked.
		this.addAbility(new FlitterstepEidolonAbility1(state));

		// Enchanted creature gets +1/+1 and can't be blocked.
		this.addAbility(new FlitterstepEidolonAbility2(state));
	}
}
