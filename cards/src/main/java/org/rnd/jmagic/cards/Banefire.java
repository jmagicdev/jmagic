package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Banefire")
@Types({Type.SORCERY})
@ManaCost("XR")
@ColorIdentity({Color.RED})
public final class Banefire extends Card
{
	public static final class XIsBig extends StaticAbility
	{
		public XIsBig(GameState state)
		{
			super(state, "If X is 5 or more, Banefire can't be countered by spells or abilities and the damage can't be prevented.");

			this.canApply = THIS_IS_ON_THE_STACK;
			SetGenerator xIs5OrMore = Intersect.instance(ValueOfX.instance(This.instance()), Between.instance(5, null));
			this.canApply = Both.instance(this.canApply, xIs5OrMore);

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.COUNTER);
			pattern.put(EventType.Parameter.CAUSE, spellsAndAbilities());
			pattern.put(EventType.Parameter.OBJECT, This.instance());

			ContinuousEffect.Part noCountering = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			noCountering.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(pattern));
			this.addEffectPart(noCountering);

			ContinuousEffect.Part noPreventing = new ContinuousEffect.Part(ContinuousEffectType.DAMAGE_CANT_BE_PREVENTED);
			noPreventing.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			this.addEffectPart(noPreventing);
		}
	}

	public Banefire(GameState state)
	{
		super(state);

		// Banefire deals X damage to target creature or player.
		Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");

		this.addEffect(spellDealDamage(ValueOfX.instance(This.instance()), targetedBy(target), "Banefire deals X damage to target creature or player."));

		// If X is 5 or more, Banefire can't be countered by spells or abilities
		// and the damage can't be prevented.
		this.addAbility(new XIsBig(state));
	}
}
