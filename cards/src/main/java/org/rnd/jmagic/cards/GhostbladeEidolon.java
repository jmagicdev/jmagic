package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ghostblade Eidolon")
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("2W")
@ColorIdentity({Color.WHITE})
public final class GhostbladeEidolon extends Card
{
	public static final class GhostbladeEidolonAbility2 extends StaticAbility
	{
		public GhostbladeEidolonAbility2(GameState state)
		{
			super(state, "Enchanted creature gets +1/+1 and has double strike.");
			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), +1, +1));
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), org.rnd.jmagic.abilities.keywords.DoubleStrike.class));
		}
	}

	public GhostbladeEidolon(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Bestow (5)(W) (If you cast this card for its bestow cost, it's an
		// Aura spell with enchant creature. It becomes a creature again if it's
		// not attached to a creature.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Bestow(state, "(5)(W)"));

		// Double strike (This creature deals both first-strike and regular
		// combat damage.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.DoubleStrike(state));

		// Enchanted creature gets +1/+1 and has double strike.
		this.addAbility(new GhostbladeEidolonAbility2(state));
	}
}
