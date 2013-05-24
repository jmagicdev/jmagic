package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Entangling Vines")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class EntanglingVines extends Card
{
	public static final class Entangle extends StaticAbility
	{
		public Entangle(GameState state)
		{
			super(state, "Enchanted creature doesn't untap during its controller's untap step.");

			// Enchanted creature doesn't untap during its controller's untap
			// step.
			EventPattern prohibitPattern = new UntapDuringControllersUntapStep(EnchantedBy.instance(This.instance()));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(prohibitPattern));
			this.addEffectPart(part);
		}
	}

	public EntanglingVines(GameState state)
	{
		super(state);

		// Enchant tapped creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.TappedCreature(state));

		this.addAbility(new Entangle(state));
	}
}
