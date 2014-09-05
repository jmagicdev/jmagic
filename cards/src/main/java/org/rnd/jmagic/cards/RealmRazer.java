package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Realm Razer")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("3RGW")
@Printings({@Printings.Printed(ex = ShardsOfAlara.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE, Color.GREEN, Color.RED})
public final class RealmRazer extends Card
{
	public static final class RealmRazerAbility0 extends EventTriggeredAbility
	{
		public RealmRazerAbility0(GameState state)
		{
			super(state, "When Realm Razer enters the battlefield, exile all lands.");
			this.addPattern(whenThisEntersTheBattlefield());

			EventFactory exile = exile(LandPermanents.instance(), "Exile all lands.");
			exile.setLink(this);
			this.addEffect(exile);

			this.getLinkManager().addLinkClass(RealmRazerAbility1.class);
		}
	}

	/**
	 * @eparam OBJECT: the objects to return (the lands exiled by the ability
	 * linked to this one)
	 */
	public static final EventType REALM_RAZER_RETURN = new EventType("REALM_RAZER_RETURN")
	{

		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set cause = new Set(event.getSource());

			java.util.Map<Parameter, Set> moveParameters = new java.util.HashMap<Parameter, Set>();
			moveParameters.put(EventType.Parameter.CAUSE, cause);
			moveParameters.put(EventType.Parameter.OBJECT, parameters.get(Parameter.OBJECT));
			Event putOntoBattlefield = createEvent(game, "Return the exiled cards to the battlefield under their owners' control", EventType.PUT_ONTO_BATTLEFIELD_UNDER_OWNER_CONTROL, moveParameters);
			putOntoBattlefield.perform(event, false);

			for(ZoneChange change: putOntoBattlefield.getResult().getAll(ZoneChange.class))
			{
				EventFactory tap = new EventFactory(TAP_PERMANENTS, event.getName());
				tap.parameters.put(EventType.Parameter.CAUSE, Identity.fromCollection(cause));
				tap.parameters.put(EventType.Parameter.OBJECT, NewObjectOf.instance(Identity.instance(change)));
				change.events.add(tap);
			}

			event.setResult(new Set());
			return true;
		}

	};

	public static final class RealmRazerAbility1 extends EventTriggeredAbility
	{
		public RealmRazerAbility1(GameState state)
		{
			super(state, "When Realm Razer leaves the battlefield, return the exiled cards to the battlefield tapped under their owners' control.");
			this.addPattern(whenThisLeavesTheBattlefield());

			EventFactory returnCards = new EventFactory(REALM_RAZER_RETURN, "Return the exiled cards to the battlefield tapped under their owners' control.");
			returnCards.parameters.put(EventType.Parameter.OBJECT, ChosenFor.instance(LinkedTo.instance(This.instance())));
			this.addEffect(returnCards);

			this.getLinkManager().addLinkClass(RealmRazerAbility0.class);
		}
	}

	public RealmRazer(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(2);

		// When Realm Razer enters the battlefield, exile all lands.
		this.addAbility(new RealmRazerAbility0(state));

		// When Realm Razer leaves the battlefield, return the exiled cards to
		// the battlefield tapped under their owners' control.
		this.addAbility(new RealmRazerAbility1(state));
	}
}
