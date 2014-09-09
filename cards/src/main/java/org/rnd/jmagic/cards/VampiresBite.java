package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Vampire's Bite")
@Types({Type.INSTANT})
@ManaCost("B")
@ColorIdentity({Color.BLACK})
public final class VampiresBite extends Card
{
	public VampiresBite(GameState state)
	{
		super(state);

		org.rnd.jmagic.abilities.keywords.Kicker ability = new org.rnd.jmagic.abilities.keywords.Kicker(state, "2B");
		this.addAbility(ability);

		// Kicker (2)(B) (You may pay an additional (2)(B) as you cast this
		// spell.)
		CostCollection kickerCost = ability.costCollections[0];

		// Target creature gets +3/+0 until end of turn. If Vampire's Bite was
		// kicked, that creature gains lifelink until end of turn. (Damage dealt
		// by the creature also causes its controller to gain that much life.)
		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

		ContinuousEffect.Part ptPart = modifyPowerAndToughness(targetedBy(target), +3, +0);
		ContinuousEffect.Part lifelinkPart = addAbilityToObject(targetedBy(target), org.rnd.jmagic.abilities.keywords.Lifelink.class);

		EventFactory ctsEffect = new EventFactory(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, "Target creature gets +3/+0 until end of turn. If Vampire's Bite was kicked, that creature gains lifelink until end of turn.");
		ctsEffect.parameters.put(EventType.Parameter.CAUSE, This.instance());
		ctsEffect.parameters.put(EventType.Parameter.EFFECT, IfThenElse.instance(ThisSpellWasKicked.instance(kickerCost), Identity.instance(ptPart, lifelinkPart), Identity.instance(ptPart)));
	}
}
