package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Electropotence")
@Types({Type.ENCHANTMENT})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class Electropotence extends Card
{
	public static final class Pandemonium extends EventTriggeredAbility
	{
		public Pandemonium(GameState state)
		{
			super(state, "Whenever a creature enters the battlefield under your control, you may pay (2)(R). If you do, that creature deals damage equal to its power to target creature or player.");

			// TODO: should this use the controller of the zone change, or do
			// this intersection? This probably applies to lots of other stuff
			// too.
			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), Intersect.instance(HasType.instance(Type.CREATURE), ControlledBy.instance(You.instance())), false));

			Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");

			SetGenerator thatCreature = NewObjectOf.instance(TriggerZoneChange.instance(This.instance()));

			EventFactory damageFactory = new EventFactory(EventType.DEAL_DAMAGE_EVENLY, "That creature deals damage equal to its power to target creature or player.");
			damageFactory.parameters.put(EventType.Parameter.SOURCE, thatCreature);
			damageFactory.parameters.put(EventType.Parameter.NUMBER, PowerOf.instance(thatCreature));
			damageFactory.parameters.put(EventType.Parameter.TAKER, targetedBy(target));

			EventFactory mayPay = new EventFactory(EventType.PLAYER_MAY_PAY_MANA, "You may pay (2)(R).");
			mayPay.parameters.put(EventType.Parameter.CAUSE, This.instance());
			mayPay.parameters.put(EventType.Parameter.COST, Identity.instance(new ManaPool("(2)(R)")));
			mayPay.parameters.put(EventType.Parameter.PLAYER, You.instance());

			EventFactory ifFactory = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may pay (2)(R). If you do, that creature deals damage equal to its power to target creature or player.");
			ifFactory.parameters.put(EventType.Parameter.IF, Identity.instance(mayPay));
			ifFactory.parameters.put(EventType.Parameter.THEN, Identity.instance(damageFactory));
			this.addEffect(ifFactory);
		}
	}

	public Electropotence(GameState state)
	{
		super(state);

		// Whenever a creature enters the battlefield under your control, you
		// may pay (2)(R). If you do, that creature deals damage equal to its
		// power to target creature or player.
		this.addAbility(new Pandemonium(state));
	}
}
