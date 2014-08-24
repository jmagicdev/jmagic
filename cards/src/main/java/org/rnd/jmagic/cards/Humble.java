package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Humble")
@Types({Type.INSTANT})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.URZAS_SAGA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class Humble extends Card
{
	public Humble(GameState state)
	{
		super(state);

		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

		// lose all abilities
		ContinuousEffect.Part loseAbilities = new ContinuousEffect.Part(ContinuousEffectType.REMOVE_ABILITY_FROM_OBJECT);
		loseAbilities.parameters.put(ContinuousEffectType.Parameter.OBJECT, targetedBy(target));

		// becomes a 0/1
		ContinuousEffect.Part setPT = setPowerAndToughness(targetedBy(target), 0, 1);

		this.addEffect(createFloatingEffect("Target creature loses all abilities and has base power and toughness 0/1 until end of turn", loseAbilities, setPT));
	}
}
