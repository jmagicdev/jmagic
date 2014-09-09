package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Myr Servitor")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.MYR})
@ManaCost("1")
@ColorIdentity({})
public final class MyrServitor extends Card
{
	public static final class Serve extends EventTriggeredAbility
	{
		public Serve(GameState state)
		{
			super(state, "At the beginning of your upkeep, if Myr Servitor is on the battlefield, each player returns all cards named Myr Servitor from his or her graveyard to the battlefield.");
			this.addPattern(atTheBeginningOfYourUpkeep());
			this.interveningIf = Exists.instance(ABILITY_SOURCE_OF_THIS);

			SetGenerator servitorsInYards = Intersect.instance(HasName.instance("Myr Servitor"), InZone.instance(GraveyardOf.instance(Players.instance())));

			EventFactory serveMore = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_UNDER_OWNER_CONTROL, "Each player returns all cards named Myr Servitor from his or her graveyard to the battlefield.");
			serveMore.parameters.put(EventType.Parameter.CAUSE, This.instance());
			serveMore.parameters.put(EventType.Parameter.OBJECT, servitorsInYards);
			this.addEffect(serveMore);
		}
	}

	public MyrServitor(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// At the beginning of your upkeep, if Myr Servitor is on the
		// battlefield, each player returns all cards named Myr Servitor from
		// his or her graveyard to the battlefield.
		this.addAbility(new Serve(state));
	}
}
