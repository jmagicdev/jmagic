package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Hellfire")
@Types({Type.SORCERY})
@ManaCost("2BBB")
@Printings({@Printings.Printed(ex = Legends.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class Hellfire extends Card
{
	/**
	 * @eparam CAUSE: The cause for the destruction / source of damage
	 * @eparam RESULT: Result of the destroy event
	 */
	public static final EventType HELLFIRE_EVENT = new EventType("HELLFIRE_EVENT")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			SetGenerator nonBlackCreatures = RelativeComplement.instance(HasType.instance(Type.CREATURE), HasColor.instance(Color.BLACK));
			SetGenerator nonBlackCreaturePermanents = Intersect.instance(nonBlackCreatures, InZone.instance(Battlefield.instance()));

			java.util.Map<Parameter, Set> destroyParameters = new java.util.HashMap<Parameter, Set>();
			destroyParameters.put(EventType.Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			destroyParameters.put(EventType.Parameter.PERMANENT, nonBlackCreaturePermanents.evaluate(game, null));
			Event destroyEvent = createEvent(game, "Destroy all nonblack creatures.", EventType.DESTROY_PERMANENTS, destroyParameters);
			boolean destroy = destroyEvent.perform(event, true);

			int damage = 3;
			for(java.util.Map.Entry<Event.IndexedZone, java.util.Set<GameObject>> objectMoved: destroyEvent.getObjectsMoved(game.actualState).entrySet())
			{
				Zone zone = game.actualState.get(objectMoved.getKey().zoneID);
				if(zone.isGraveyard())
					for(GameObject moved: objectMoved.getValue())
					{
						GameObject pastSelf = moved.getPastSelf();
						if(pastSelf.getTypes().contains(Type.CREATURE) && pastSelf.getZone().equals(game.actualState.battlefield()))
							damage++;
					}
			}

			java.util.Map<Parameter, Set> damageParameters = new java.util.HashMap<Parameter, Set>();
			damageParameters.put(EventType.Parameter.SOURCE, parameters.get(Parameter.CAUSE));
			damageParameters.put(EventType.Parameter.NUMBER, new Set(damage));
			damageParameters.put(EventType.Parameter.TAKER, new Set(event.getSource().getController(event.state)));
			Event damageEvent = createEvent(game, "Hellfire deals X plus 3 damage to you, where X is the number of creatures put into all graveyards this way.", EventType.DEAL_DAMAGE_EVENLY, damageParameters);
			boolean damageDealt = damageEvent.perform(event, true);

			event.setResult(destroyEvent.getResultGenerator());

			return destroy && damageDealt;
		}

		@Override
		public String toString()
		{
			return "HELLFIRE_EVENT";
		}
	};

	public Hellfire(GameState state)
	{
		super(state);

		EventType.ParameterMap destroyParameters = new EventType.ParameterMap();
		destroyParameters.put(EventType.Parameter.CAUSE, This.instance());
		this.addEffect(new EventFactory(HELLFIRE_EVENT, destroyParameters, "Destroy all nonblack creatures. Hellfire deals X plus 3 damage to you, where X is the number of creatures put into all graveyards this way."));
	}
}
