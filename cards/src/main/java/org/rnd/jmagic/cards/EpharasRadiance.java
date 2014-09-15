package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ephara's Radiance")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("W")
@ColorIdentity({Color.WHITE})
public final class EpharasRadiance extends Card
{
	public static final class GainLife extends ActivatedAbility
	{
		public GainLife(GameState state)
		{
			super(state, "(1)(W), (T): You gain 3 life.");
			this.setManaCost(new ManaPool("(1)(W)"));
			this.costsTap = true;
			this.addEffect(gainLife(You.instance(), 3, "You gain 3 life."));
		}
	}

	public static final class EpharasRadianceAbility1 extends StaticAbility
	{
		public EpharasRadianceAbility1(GameState state)
		{
			super(state, "Enchanted creature has \"(1)(W), (T): You gain 3 life.\"");
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), GainLife.class));
		}
	}

	public EpharasRadiance(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature has "(1)(W), (T): You gain 3 life."
		this.addAbility(new EpharasRadianceAbility1(state));
	}
}
