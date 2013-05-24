package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Claws of Valakut")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1RR")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class ClawsofValakut extends Card
{
	public static final class Pump extends StaticAbility
	{
		public Pump(GameState state)
		{
			super(state, "Enchanted creature gets +1/+0 for each Mountain you control and has first strike.");

			SetGenerator enchantedCreature = EnchantedBy.instance(This.instance());
			SetGenerator mountains = HasSubType.instance(SubType.MOUNTAIN);
			SetGenerator youControl = ControlledBy.instance(You.instance());
			SetGenerator amount = Count.instance(Intersect.instance(mountains, youControl));

			this.addEffectPart(modifyPowerAndToughness(enchantedCreature, amount, numberGenerator(0)));
			this.addEffectPart(addAbilityToObject(enchantedCreature, org.rnd.jmagic.abilities.keywords.FirstStrike.class));
		}
	}

	public ClawsofValakut(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets +1/+0 for each Mountain you control and has
		// first strike.
		this.addAbility(new Pump(state));
	}
}
