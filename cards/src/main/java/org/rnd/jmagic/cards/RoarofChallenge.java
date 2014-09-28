package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Roar of Challenge")
@Types({Type.SORCERY})
@ManaCost("2G")
@ColorIdentity({Color.GREEN})
public final class RoarofChallenge extends Card
{
	public RoarofChallenge(GameState state)
	{
		super(state);

		// All creatures able to block target creature this turn do so.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

		ContinuousEffect.Part lure = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_REQUIREMENT_FOR_EACH_DEFENDING_CREATURE);
		lure.parameters.put(ContinuousEffectType.Parameter.ATTACKING, target);
		lure.parameters.put(ContinuousEffectType.Parameter.DEFENDING, HasType.instance(Type.CREATURE));
		this.addEffect(createFloatingEffect("All creatures able to block target creature this turn do so.", lure));

		// Ferocious \u2014 That creature gains indestructible until end of turn
		// if you control a creature with power 4 or greater.
		EventFactory indestructible = addAbilityUntilEndOfTurn(target, org.rnd.jmagic.abilities.keywords.Indestructible.class, "That creature gains indestructible until end of turn.");
		this.addEffect(ifThen(Ferocious.instance(), indestructible, "\n\nFerocious \u2014 That creature gains indestructible until end of turn if you control a creature with power 4 or greater."));
	}
}
