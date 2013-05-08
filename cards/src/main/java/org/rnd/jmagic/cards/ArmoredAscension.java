package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Armored Ascension")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("3W")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.SHADOWMOOR, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class ArmoredAscension extends Card
{
	public static final class Boost extends StaticAbility
	{
		public Boost(GameState state)
		{
			super(state, "Enchanted creature gets +1/+1 for each Plains you control and has flying.");

			// Enchanted creature
			SetGenerator enchantedCreature = EnchantedBy.instance(This.instance());

			SetGenerator plains = Intersect.instance(LandPermanents.instance(), HasSubType.instance(SubType.PLAINS));
			SetGenerator youControl = ControlledBy.instance(ControllerOf.instance(This.instance()));
			SetGenerator plainsYouControl = Intersect.instance(plains, youControl);
			SetGenerator boostAmount = Count.instance(plainsYouControl);

			// gets +1/+1 for each Plains you control
			this.addEffectPart(modifyPowerAndToughness(enchantedCreature, boostAmount, boostAmount));
			// and has flying.
			this.addEffectPart(addAbilityToObject(enchantedCreature, org.rnd.jmagic.abilities.keywords.Flying.class));
		}
	}

	public ArmoredAscension(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		this.addAbility(new Boost(state));
	}
}
