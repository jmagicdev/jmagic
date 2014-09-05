package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Dead Reckoning")
@Types({Type.SORCERY})
@ManaCost("1BB")
@Printings({@Printings.Printed(ex = Worldwake.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class DeadReckoning extends Card
{
	public DeadReckoning(GameState state)
	{
		super(state);

		// You may put target creature card from your graveyard on top of your
		// library. If you do, Dead Reckoning deals damage equal to that card's
		// power to target creature.
		SetGenerator inYourYard = InZone.instance(GraveyardOf.instance(You.instance()));
		SetGenerator yourDeadCreatures = Intersect.instance(HasType.instance(Type.CREATURE), inYourYard);
		SetGenerator deadTarget = targetedBy(this.addTarget(yourDeadCreatures, "target creature card from your graveyard"));

		SetGenerator soonToBeDeadTarget = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

		EventFactory move = new EventFactory(EventType.PUT_INTO_LIBRARY, "Put target creature card from your graveyard on top of your library");
		move.parameters.put(EventType.Parameter.CAUSE, This.instance());
		move.parameters.put(EventType.Parameter.INDEX, numberGenerator(1));
		move.parameters.put(EventType.Parameter.OBJECT, deadTarget);
		EventFactory youMayMove = youMay(move, "You may put target creature card from your graveyard on top of your library");

		EventFactory damage = spellDealDamage(PowerOf.instance(deadTarget), soonToBeDeadTarget, "Dead Reckoning deals damage equal to that card's power to target creature");

		EventFactory effect = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may put target creature card from your graveyard on top of your library. If you do, Dead Reckoning deals damage equal to that card's power to target creature.");
		effect.parameters.put(EventType.Parameter.IF, Identity.instance(youMayMove));
		effect.parameters.put(EventType.Parameter.THEN, Identity.instance(damage));
		this.addEffect(effect);
	}
}
