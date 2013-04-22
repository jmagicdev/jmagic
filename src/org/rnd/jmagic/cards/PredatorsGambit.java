package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Predator's Gambit")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class PredatorsGambit extends Card
{
	public static final class PredatorsGambitAbility2 extends StaticAbility
	{
		public PredatorsGambitAbility2(GameState state)
		{
			super(state, "Enchanted creature has intimidate as long as its controller controls no other creatures.");

			SetGenerator enchanted = EnchantedBy.instance(This.instance());
			this.addEffectPart(addAbilityToObject(enchanted, org.rnd.jmagic.abilities.keywords.Intimidate.class));

			this.canApply = Both.instance(this.canApply, Intersect.instance(numberGenerator(0), Count.instance(RelativeComplement.instance(Intersect.instance(ControlledBy.instance(ControllerOf.instance(enchanted)), HasType.instance(Type.CREATURE)), enchanted))));
		}
	}

	public PredatorsGambit(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets +2/+1.
		this.addAbility(new org.rnd.jmagic.abilities.StaticPTChange(state, EnchantedBy.instance(This.instance()), "Enchanted creature", +2, +1, false));

		// Enchanted creature has intimidate as long as its controller controls
		// no other creatures. (It can't be blocked except by artifact creatures
		// and/or creatures that share a color with it.)
		this.addAbility(new PredatorsGambitAbility2(state));
	}
}
