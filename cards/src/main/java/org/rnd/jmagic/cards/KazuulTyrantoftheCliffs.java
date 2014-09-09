package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Kazuul, Tyrant of the Cliffs")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.OGRE})
@ManaCost("3RR")
@ColorIdentity({Color.RED})
public final class KazuulTyrantoftheCliffs extends Card
{
	public static final class KazuulTyrantoftheCliffsAbility0 extends EventTriggeredAbility
	{
		public KazuulTyrantoftheCliffsAbility0(GameState state)
		{
			super(state, "Whenever a creature an opponent controls attacks, if you're the defending player, put a 3/3 red Ogre creature token onto the battlefield unless that creature's controller pays (3).");
			SetGenerator opponentsCreatures = Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(OpponentsOf.instance(You.instance())));
			SimpleEventPattern pattern = new SimpleEventPattern(EventType.DECLARE_ONE_ATTACKER);
			pattern.put(EventType.Parameter.OBJECT, opponentsCreatures);
			this.addPattern(pattern);

			SetGenerator triggerEvent = TriggerEvent.instance(This.instance());
			SetGenerator thatCreature = EventParameter.instance(triggerEvent, EventType.Parameter.OBJECT);
			SetGenerator defendingPlayer = DefendingPlayer.instance(thatCreature);
			this.interveningIf = Intersect.instance(You.instance(), defendingPlayer);

			CreateTokensFactory token = new CreateTokensFactory(1, 3, 3, "Put a 3/3 red Ogre creature token onto the battlefield");
			token.setColors(Color.RED);
			token.setSubTypes(SubType.OGRE);

			SetGenerator thatCreaturesController = ControllerOf.instance(thatCreature);

			EventFactory theyPay3 = new EventFactory(EventType.PAY_MANA, "That creature's controller may pay (3)");
			theyPay3.parameters.put(EventType.Parameter.CAUSE, This.instance());
			theyPay3.parameters.put(EventType.Parameter.COST, Identity.fromCollection(new ManaPool("3")));
			theyPay3.parameters.put(EventType.Parameter.PLAYER, thatCreaturesController);

			this.addEffect(unless(thatCreaturesController, token.getEventFactory(), theyPay3, "Put a 3/3 red Ogre creature token onto the battlefield unless that creature's controller pays (3)."));
		}
	}

	public KazuulTyrantoftheCliffs(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(4);

		// Whenever a creature an opponent controls attacks, if you're the
		// defending player, put a 3/3 red Ogre creature token onto the
		// battlefield unless that creature's controller pays (3).
		this.addAbility(new KazuulTyrantoftheCliffsAbility0(state));
	}
}
