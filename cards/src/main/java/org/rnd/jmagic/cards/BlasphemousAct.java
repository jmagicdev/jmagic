package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Blasphemous Act")
@Types({Type.SORCERY})
@ManaCost("8R")
@ColorIdentity({Color.RED})
public final class BlasphemousAct extends Card
{
	public static final class BlasphemousActAbility0 extends StaticAbility
	{
		public BlasphemousActAbility0(GameState state)
		{
			super(state, "Blasphemous Act costs (1) less to cast for each creature on the battlefield.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.MANA_COST_REDUCTION);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			part.parameters.put(ContinuousEffectType.Parameter.NUMBER, Count.instance(CreaturePermanents.instance()));
			part.parameters.put(ContinuousEffectType.Parameter.COST, Identity.fromCollection(new ManaPool("(1)")));
			this.addEffectPart(part);

			// doesn't union with this.canApply since it completely changes when
			// it applies
			this.canApply = NonEmpty.instance();
		}
	}

	public BlasphemousAct(GameState state)
	{
		super(state);

		// Blasphemous Act costs (1) less to cast for each creature on the
		// battlefield.
		this.addAbility(new BlasphemousActAbility0(state));

		// Blasphemous Act deals 13 damage to each creature.
		this.addEffect(spellDealDamage(13, CreaturePermanents.instance(), "Blasphemous Act deals 13 damage to each creature."));
	}
}
