package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Death's Approach")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class DeathsApproach extends Card
{
	public static final class DeathsApproachAbility1 extends StaticAbility
	{
		public DeathsApproachAbility1(GameState state)
		{
			super(state, "Enchanted creature gets -X/-X, where X is the number of creature cards in its controller's graveyard.");

			SetGenerator enchanted = EnchantedBy.instance(This.instance());
			SetGenerator X = Count.instance(Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(ControllerOf.instance(enchanted)))));
			SetGenerator minusX = Subtract.instance(numberGenerator(0), X);
			this.addEffectPart(modifyPowerAndToughness(enchanted, minusX, minusX));
		}
	}

	public DeathsApproach(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets -X/-X, where X is the number of creature
		// cards in its controller's graveyard.
		this.addAbility(new DeathsApproachAbility1(state));
	}
}
