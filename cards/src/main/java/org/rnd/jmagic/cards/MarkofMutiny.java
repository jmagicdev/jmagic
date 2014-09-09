package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mark of Mutiny")
@Types({Type.SORCERY})
@ManaCost("2R")
@ColorIdentity({Color.RED})
public final class MarkofMutiny extends Card
{
	public MarkofMutiny(GameState state)
	{
		super(state);

		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
		// Gain control of target creature until end of turn.
		ContinuousEffect.Part controlPart = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
		controlPart.parameters.put(ContinuousEffectType.Parameter.OBJECT, targetedBy(target));
		controlPart.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());

		this.addEffect(createFloatingEffect("Gain control of target creature until end of turn.", controlPart));

		// Put a +1/+1 counter on it
		this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, targetedBy(target), "Put a +1/+1 counter on it"));

		// and untap it.
		this.addEffect(untap(targetedBy(target), "and untap it."));

		// That creature gains haste until end of turn.
		this.addEffect(addAbilityUntilEndOfTurn(targetedBy(target), org.rnd.jmagic.abilities.keywords.Haste.class, "That creature gains haste until end of turn."));
	}
}
