package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Deviant Glee")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK, Color.RED})
public final class DeviantGlee extends Card
{
	public static final class GainTrample extends ActivatedAbility
	{
		public GainTrample(GameState state)
		{
			super(state, "(R): This creature gains trample until end of turn.");
			this.setManaCost(new ManaPool("R"));
			this.addEffect(addAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.Trample.class, "This creature gains trample until end of turn."));
		}
	}

	public static final class DeviantGleeAbility1 extends StaticAbility
	{
		public DeviantGleeAbility1(GameState state)
		{
			super(state, "Enchanted creature gets +2/+1 and has \"(R): This creature gains trample until end of turn.\"");

			SetGenerator enchantedCreature = EnchantedBy.instance(This.instance());
			this.addEffectPart(modifyPowerAndToughness(enchantedCreature, +2, +1));
			this.addEffectPart(addAbilityToObject(enchantedCreature, GainTrample.class));
		}
	}

	public DeviantGlee(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets +2/+1 and has
		// "(R): This creature gains trample until end of turn."
		this.addAbility(new DeviantGleeAbility1(state));
	}
}
