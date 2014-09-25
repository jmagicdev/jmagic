package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Feat of Resistance")
@Types({Type.INSTANT})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class FeatofResistance extends Card
{
	public FeatofResistance(GameState state)
	{
		super(state);

		// Put a +1/+1 counter on target creature you control.
		SetGenerator target = targetedBy(this.addTarget(CREATURES_YOU_CONTROL, "target creature you control"));
		this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, target, "Put a +1/+1 counter on target creature you control."));

		// It gains protection from the color of your choice until end of turn.
		SetGenerator colors = Identity.fromCollection(Color.allColors());
		EventFactory chooseColor = playerChoose(You.instance(), 1, colors, PlayerInterface.ChoiceType.COLOR, PlayerInterface.ChooseReason.CHOOSE_COLOR, "It gains protection from the color of your choice");
		this.addEffect(chooseColor);

		this.addEffect(addProtectionUntilEndOfTurn(target, EffectResult.instance(chooseColor), "until end of turn."));
	}
}
