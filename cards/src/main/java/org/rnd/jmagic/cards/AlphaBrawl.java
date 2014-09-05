package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Alpha Brawl")
@Types({Type.SORCERY})
@ManaCost("6RR")
@Printings({@Printings.Printed(ex = DarkAscension.class, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class AlphaBrawl extends Card
{
	public AlphaBrawl(GameState state)
	{
		super(state);

		// Target creature an opponent controls deals damage equal to its power
		// to each other creature that player controls,
		SetGenerator legal = Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(OpponentsOf.instance(You.instance())));
		SetGenerator target = targetedBy(this.addTarget(legal, "target creature an opponents controls"));

		SetGenerator others = RelativeComplement.instance(legal, target);

		EventFactory getMad = new EventFactory(EventType.DEAL_DAMAGE_EVENLY, "Target creature an opponent controls deals damage equal to its power to each other creature that player controls,");
		getMad.parameters.put(EventType.Parameter.SOURCE, target);
		getMad.parameters.put(EventType.Parameter.NUMBER, PowerOf.instance(target));
		getMad.parameters.put(EventType.Parameter.TAKER, others);
		this.addEffect(getMad);

		// then each of those creatures deals damage equal to its power to that
		// creature.
		DynamicEvaluation eachOfThose = DynamicEvaluation.instance();

		EventFactory getEven = new EventFactory(EventType.DEAL_DAMAGE_EVENLY, "one of those creatures deals damage equal to its power to that creature");
		getEven.parameters.put(EventType.Parameter.SOURCE, eachOfThose);
		getEven.parameters.put(EventType.Parameter.NUMBER, PowerOf.instance(eachOfThose));
		getEven.parameters.put(EventType.Parameter.TAKER, target);

		EventFactory gangBang = new EventFactory(FOR_EACH, "then each of those creatures deals damage equal to its power to that creature.");
		gangBang.parameters.put(EventType.Parameter.OBJECT, others);
		gangBang.parameters.put(EventType.Parameter.TARGET, Identity.instance(eachOfThose));
		gangBang.parameters.put(EventType.Parameter.EFFECT, Identity.instance(getEven));
		this.addEffect(gangBang);
	}
}
