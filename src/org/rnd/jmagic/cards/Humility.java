package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Humility")
@Types({Type.ENCHANTMENT})
@ManaCost("2WW")
@Printings({@Printings.Printed(ex = Expansion.TEMPEST, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class Humility extends Card
{
	public static final class HumilityEffect extends StaticAbility
	{
		public HumilityEffect(GameState state)
		{
			super(state, "All creatures lose all abilities and are 1/1");

			SetGenerator creatures = CreaturePermanents.instance();

			// lose all abilities
			ContinuousEffect.Part loseAbilities = new ContinuousEffect.Part(ContinuousEffectType.REMOVE_ABILITY_FROM_OBJECT);
			loseAbilities.parameters.put(ContinuousEffectType.Parameter.OBJECT, creatures);
			this.addEffectPart(loseAbilities);

			// and are 1/1
			this.addEffectPart(setPowerAndToughness(creatures, 1, 1));
		}
	}

	public Humility(GameState state)
	{
		super(state);

		this.addAbility(new HumilityEffect(state));
	}
}
