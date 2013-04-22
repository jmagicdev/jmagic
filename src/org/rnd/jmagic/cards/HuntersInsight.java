package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.patterns.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hunter's Insight")
@Types({Type.INSTANT})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class HuntersInsight extends Card
{
	public HuntersInsight(GameState state)
	{
		super(state);

		// Choose target creature you control.
		SetGenerator target = delayedTriggerContext(targetedBy(this.addTarget(CREATURES_YOU_CONTROL, "target creature you control")));

		// Whenever that creature deals combat damage to a player or
		// planeswalker this turn, draw that many cards.
		SetGenerator playerOrWalker = Union.instance(Players.instance(), Intersect.instance(Permanents.instance(), HasType.instance(Type.PLANESWALKER)));
		SimpleDamagePattern combatDamage = new SimpleDamagePattern(target, playerOrWalker, true);

		SetGenerator thatMany = Count.instance(TriggerDamage.instance(This.instance()));
		EventFactory draw = drawCards(You.instance(), thatMany, "Draw that many cards.");

		EventFactory drawLater = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "Choose target creature you control. Whenever that creature deals combat damage to a player or planeswalker this turn, draw that many cards.");
		drawLater.parameters.put(EventType.Parameter.CAUSE, This.instance());
		drawLater.parameters.put(EventType.Parameter.DAMAGE, Identity.instance(combatDamage));
		drawLater.parameters.put(EventType.Parameter.EXPIRES, CleanupStepOf.instance(Players.instance()));
		drawLater.parameters.put(EventType.Parameter.EFFECT, Identity.instance(draw));
		this.addEffect(drawLater);
	}
}
