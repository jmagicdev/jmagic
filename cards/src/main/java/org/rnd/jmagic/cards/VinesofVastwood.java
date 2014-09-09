package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Vines of Vastwood")
@Types({Type.INSTANT})
@ManaCost("G")
@ColorIdentity({Color.GREEN})
public final class VinesofVastwood extends Card
{
	public VinesofVastwood(GameState state)
	{
		super(state);

		org.rnd.jmagic.abilities.keywords.Kicker ability = new org.rnd.jmagic.abilities.keywords.Kicker(state, "(G)");
		this.addAbility(ability);

		CostCollection kicker = ability.costCollections[0];

		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

		SetGenerator stuffOpponentsControl = ControlledBy.instance(OpponentsOf.instance(You.instance()), Stack.instance());

		ContinuousEffect.Part shroudPart = new ContinuousEffect.Part(ContinuousEffectType.CANT_BE_THE_TARGET_OF);
		shroudPart.parameters.put(ContinuousEffectType.Parameter.OBJECT, targetedBy(target));
		shroudPart.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(new SimpleSetPattern(stuffOpponentsControl)));

		this.addEffect(createFloatingEffect("Target creature can't be the target of spells or abilities your opponents control this turn.", shroudPart));

		EventFactory ifFactory = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "If Vines of Vastwood was kicked, that creature gets +4/+4 until end of turn.");
		ifFactory.parameters.put(EventType.Parameter.IF, ThisSpellWasKicked.instance(kicker));
		ifFactory.parameters.put(EventType.Parameter.THEN, Identity.instance(createFloatingEffect("That creature gets +4/+4 until end of turn.", modifyPowerAndToughness(targetedBy(target), +4, +4))));
		this.addEffect(ifFactory);
	}
}
