package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Turn to Frog")
@Types({Type.INSTANT})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class TurntoFrog extends Card
{
	public TurntoFrog(GameState state)
	{
		super(state);

		// Target creature loses all abilities and becomes a 1/1 blue Frog until
		// end of turn.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

		ContinuousEffect.Part loseAbilities = new ContinuousEffect.Part(ContinuousEffectType.REMOVE_ABILITY_FROM_OBJECT);
		loseAbilities.parameters.put(ContinuousEffectType.Parameter.OBJECT, target);

		ContinuousEffect.Part setPT = setPowerAndToughness(target, 1, 1);

		ContinuousEffect.Part setColor = new ContinuousEffect.Part(ContinuousEffectType.SET_COLOR);
		setColor.parameters.put(ContinuousEffectType.Parameter.OBJECT, target);
		setColor.parameters.put(ContinuousEffectType.Parameter.COLOR, Identity.instance(Color.BLUE));

		ContinuousEffect.Part setFrog = new ContinuousEffect.Part(ContinuousEffectType.SET_TYPES);
		setFrog.parameters.put(ContinuousEffectType.Parameter.OBJECT, target);
		setFrog.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(SubType.FROG));

		this.addEffect(createFloatingEffect("Target creature loses all abilities and becomes a 1/1 blue Frog until end of turn.", loseAbilities, setPT, setColor, setFrog));
	}
}
