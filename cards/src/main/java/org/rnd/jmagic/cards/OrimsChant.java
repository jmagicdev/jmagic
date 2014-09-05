package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.patterns.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Orim's Chant")
@Types({Type.INSTANT})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Planeshift.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class OrimsChant extends Card
{
	public OrimsChant(GameState state)
	{
		super(state);

		// Kicker (W) (You may pay an additional (W) as you cast this spell.)
		org.rnd.jmagic.abilities.keywords.Kicker ability = new org.rnd.jmagic.abilities.keywords.Kicker(state, "(W)");
		this.addAbility(ability);

		// Target player can't cast spells this turn.
		SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));

		SimpleEventPattern targetCasts = new SimpleEventPattern(EventType.CAST_SPELL_OR_ACTIVATE_ABILITY);
		targetCasts.put(EventType.Parameter.PLAYER, target);
		targetCasts.put(EventType.Parameter.OBJECT, SetPattern.CASTABLE);

		ContinuousEffect.Part noCasting = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
		noCasting.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(targetCasts));
		this.addEffect(createFloatingEffect("Target player can't cast spells this turn.", noCasting));

		// If Orim's Chant was kicked, creatures can't attack this turn.
		ContinuousEffect.Part noAttacking = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_RESTRICTION);
		noAttacking.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Attacking.instance()));

		EventFactory conditional = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "If Orim's Chant was kicked, creatures can't attack this turn.");
		conditional.parameters.put(EventType.Parameter.IF, ThisSpellWasKicked.instance(ability.costCollections[0]));
		conditional.parameters.put(EventType.Parameter.THEN, Identity.instance(createFloatingEffect("Creatures can't attack this turn", noAttacking)));
		this.addEffect(conditional);
	}
}
