package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Prince of Thralls")
@Types({Type.CREATURE})
@SubTypes({SubType.DEMON})
@ManaCost("4UBBR")
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLUE, Color.BLACK, Color.RED})
public final class PrinceofThralls extends Card
{
	public static final class Enthrall extends EventTriggeredAbility
	{
		public Enthrall(GameState state)
		{
			super(state, "Whenever a permanent an opponent controls is put into a graveyard, put that card onto the battlefield under your control unless that opponent pays 3 life.");

			this.addPattern(new SimpleZoneChangePattern(Battlefield.instance(), GraveyardOf.instance(Players.instance()), ControlledBy.instance(OpponentsOf.instance(You.instance())), true));

			SetGenerator triggerEvent = TriggerZoneChange.instance(This.instance());
			SetGenerator thatCardOld = OldObjectOf.instance(triggerEvent);
			SetGenerator thatCardNew = NewObjectOf.instance(triggerEvent);

			EventFactory moveFactory = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, "Put that card onto the battlefield under your control.");
			moveFactory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			moveFactory.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			moveFactory.parameters.put(EventType.Parameter.OBJECT, thatCardNew);

			EventFactory lifeFactory = payLife(ControllerOf.instance(thatCardOld), 3, "That opponent pays 3 life.");

			this.addEffect(unless(ControllerOf.instance(thatCardOld), moveFactory, lifeFactory, "Put that card onto the battlefield under your control unless that opponent pays 3 life."));
		}
	}

	public PrinceofThralls(GameState state)
	{
		super(state);

		this.setPower(7);
		this.setToughness(7);

		this.addAbility(new Enthrall(state));
	}
}
