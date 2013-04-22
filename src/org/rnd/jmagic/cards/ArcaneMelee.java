package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Arcane Melee")
@Types({Type.ENCHANTMENT})
@ManaCost("4U")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class ArcaneMelee extends Card
{
	public static final class ArcaneMeleeAbility0 extends StaticAbility
	{
		public ArcaneMeleeAbility0(GameState state)
		{
			super(state, "Instant and sorcery spells cost (2) less to cast.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.MANA_COST_REDUCTION);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, HasType.instance(Type.INSTANT, Type.SORCERY));
			part.parameters.put(ContinuousEffectType.Parameter.COST, Identity.instance(new ManaPool("(2)")));
			this.addEffectPart(part);
		}
	}

	public ArcaneMelee(GameState state)
	{
		super(state);

		// Instant and sorcery spells cost (2) less to cast.
		this.addAbility(new ArcaneMeleeAbility0(state));
	}
}
