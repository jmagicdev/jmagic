package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Nameless Inversion")
@Types({Type.TRIBAL, Type.INSTANT})
@SubTypes({SubType.SHAPESHIFTER})
@ManaCost("1B")
@ColorIdentity({Color.BLACK})
public final class NamelessInversion extends Card
{
	public NamelessInversion(GameState state)
	{
		super(state);

		// Changeling (This card is every creature type at all times.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Changeling(state));

		// Target creature gets +3/-3 and loses all creature types until end of
		// turn.
		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

		ContinuousEffect.Part pt = modifyPowerAndToughness(targetedBy(target), +3, -3);
		ContinuousEffect.Part types = new ContinuousEffect.Part(ContinuousEffectType.REMOVE_TYPES);
		types.parameters.put(ContinuousEffectType.Parameter.OBJECT, targetedBy(target));
		types.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.fromCollection(SubType.getAllCreatureTypes()));

		this.addEffect(createFloatingEffect("Target creature gets +3/-3 and loses all creature types until end of turn.", pt, types));
	}
}
