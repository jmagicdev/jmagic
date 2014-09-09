package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Autumn's Veil")
@Types({Type.INSTANT})
@ManaCost("G")
@ColorIdentity({Color.GREEN})
public final class AutumnsVeil extends Card
{
	public AutumnsVeil(GameState state)
	{
		super(state);

		// Spells you control can't be countered by blue or black spells this
		// turn,
		SetGenerator spellsYouControl = Intersect.instance(Spells.instance(), ControlledBy.instance(You.instance(), Stack.instance()));
		SetGenerator blueAndBlackSpells = Intersect.instance(HasColor.instance(Color.BLUE, Color.BLACK), Spells.instance());

		SimpleEventPattern countering = new SimpleEventPattern(EventType.COUNTER_ONE);
		countering.put(EventType.Parameter.OBJECT, spellsYouControl);
		countering.put(EventType.Parameter.CAUSE, blueAndBlackSpells);

		ContinuousEffect.Part noCounter = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
		noCounter.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(countering));

		// and creatures you control can't be the targets of blue or black
		// spells this turn.
		ContinuousEffect.Part noTarget = new ContinuousEffect.Part(ContinuousEffectType.CANT_BE_THE_TARGET_OF);
		noTarget.parameters.put(ContinuousEffectType.Parameter.OBJECT, CREATURES_YOU_CONTROL);
		noTarget.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(new SimpleSetPattern(blueAndBlackSpells)));

		this.addEffect(createFloatingEffect("Spells you control can't be countered by blue or black spells this turn, and creatures you control can't be the targets of blue or black spells this turn.", noCounter, noTarget));
	}
}
