package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Terastodon")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEPHANT})
@ManaCost("6GG")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class Terastodon extends Card
{
	/**
	 * @eparam CAUSE: terastodon's trigger
	 * @eparam OBJECT: permanents targeted by terastodon's trigger
	 */
	public static final EventType TERASTODON_EVENT = new EventType("TERASTODON_EVENT")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			event.setResult(Empty.set);

			Set targets = parameters.get(Parameter.OBJECT);
			Event destroy = destroy(Identity.fromCollection(targets), "Destroy up to three target noncreature permanents").createEvent(game, event.getSource());
			destroy.perform(event, true);

			Set cause = parameters.get(Parameter.CAUSE);
			for(java.util.Map.Entry<Event.IndexedZone, java.util.Set<GameObject>> objectMoved: destroy.getObjectsMoved(game.actualState).entrySet())
			{
				Zone zone = game.actualState.get(objectMoved.getKey().zoneID);
				if(zone.isGraveyard())
					for(GameObject moved: objectMoved.getValue())
					{
						GameObject pastSelf = moved.getActual().getPastSelf();
						if(pastSelf.getZone().equals(game.actualState.battlefield()))
						{
							Player controller = pastSelf.getController(game.actualState);

							java.util.Map<Parameter, Set> tokenParameters = new java.util.HashMap<Parameter, Set>();
							tokenParameters.put(Parameter.CAUSE, cause);
							tokenParameters.put(Parameter.COLOR, new Set(Color.GREEN));
							tokenParameters.put(Parameter.CONTROLLER, new Set(controller));
							tokenParameters.put(Parameter.POWER, new Set(3));
							tokenParameters.put(Parameter.SUBTYPE, new Set((Object)java.util.Collections.singletonList(SubType.ELEPHANT)));
							tokenParameters.put(Parameter.TOUGHNESS, new Set(3));
							tokenParameters.put(Parameter.TYPE, new Set(Type.CREATURE));
							Event makeToken = createEvent(game, controller + " puts a 3/3 green Elephant creature token onto the battlefield", EventType.CREATE_TOKEN_ON_BATTLEFIELD, tokenParameters);
							makeToken.perform(event, true);
						}
					}
			}

			return true;
		}
	};

	public static final class ETBTurnThingsIntoElephants extends EventTriggeredAbility
	{
		public ETBTurnThingsIntoElephants(GameState state)
		{
			super(state, "When Terastodon enters the battlefield, you may destroy up to three target noncreature permanents. For each permanent put into a graveyard this way, its controller puts a 3/3 green Elephant creature token onto the battlefield.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator noncreaturePermanents = RelativeComplement.instance(Permanents.instance(), CreaturePermanents.instance());
			Target target = this.addTarget(noncreaturePermanents, "up to three target noncreature permanents");
			target.setNumber(0, 3);

			EventFactory effect = new EventFactory(TERASTODON_EVENT, "Destroy up to three target noncreature permanents. For each permanent put into a graveyard this way, its controller puts a 3/3 green Elephant creature token onto the battlefield.");
			effect.parameters.put(EventType.Parameter.CAUSE, This.instance());
			effect.parameters.put(EventType.Parameter.OBJECT, targetedBy(target));
			this.addEffect(youMay(effect, "You may destroy up to three target noncreature permanents. For each permanent put into a graveyard this way, its controller puts a 3/3 green Elephant creature token onto the battlefield."));
		}
	}

	public Terastodon(GameState state)
	{
		super(state);

		this.setPower(9);
		this.setToughness(9);

		// When Terastodon enters the battlefield, you may destroy up to three
		// target noncreature permanents. For each permanent put into a
		// graveyard this way, its controller puts a 3/3 green Elephant creature
		// token onto the battlefield.
		this.addAbility(new ETBTurnThingsIntoElephants(state));
	}
}
