package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Kalitas, Bloodchief of Ghet")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE, SubType.WARRIOR})
@ManaCost("5BB")
@ColorIdentity({Color.BLACK})
public final class KalitasBloodchiefofGhet extends Card
{
	/**
	 * @eparam CAUSE: kalitas' trigger
	 * @eparam PLAYER: controller of CAUSE
	 * @eparam OBJECT: what to destroy
	 */
	public static final EventType KALITAS_EVENT = new EventType("KALITAS_EVENT")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set cause = parameters.get(Parameter.CAUSE);
			Set target = parameters.get(Parameter.OBJECT);

			GameObject targetObject = target.getOne(GameObject.class);
			int targetID = targetObject.ID;
			int power = targetObject.getPower();
			int toughness = targetObject.getToughness();

			java.util.Map<Parameter, Set> destroyParameters = new java.util.HashMap<Parameter, Set>();
			destroyParameters.put(Parameter.CAUSE, cause);
			destroyParameters.put(Parameter.PERMANENT, target);
			Event destroy = createEvent(game, "Destroy target creature.", EventType.DESTROY_PERMANENTS, destroyParameters);
			destroy.perform(event, true);

			Set you = parameters.get(Parameter.PLAYER);
			for(java.util.Map.Entry<Event.IndexedZone, java.util.Set<GameObject>> objectMoved: destroy.getObjectsMoved(game.actualState).entrySet())
			{
				Zone zone = game.actualState.get(objectMoved.getKey().zoneID);
				if(zone.isGraveyard())
					for(GameObject moved: objectMoved.getValue())
						if(moved.pastSelf == targetID)
						{
							java.util.Map<Parameter, Set> tokenParameters = new java.util.HashMap<Parameter, Set>();
							tokenParameters.put(Parameter.CAUSE, cause);
							tokenParameters.put(Parameter.COLOR, new Set(Color.BLACK));
							tokenParameters.put(Parameter.CONTROLLER, you);
							tokenParameters.put(Parameter.NUMBER, ONE);
							tokenParameters.put(Parameter.POWER, ZERO);
							tokenParameters.put(Parameter.SUBTYPE, new Set((Object)(java.util.Arrays.asList(SubType.VAMPIRE))));
							tokenParameters.put(Parameter.TYPE, new Set(Type.CREATURE));
							tokenParameters.put(Parameter.TOUGHNESS, ZERO);
							Event makeToken = createEvent(game, "Put a black Vampire creature token onto the battlefield. Its power is equal to that creature's power and its toughness is equal to that creature's toughness.", EventType.CREATE_TOKEN_ON_BATTLEFIELD, tokenParameters);

							// This event is not top level, because things that
							// trigger when a creature with a certain power
							// comes into play should see this creature's
							// "actual" power.
							makeToken.perform(event, false);
							Set token = makeToken.getResult();

							ContinuousEffect.Part part = setPowerAndToughness(Identity.fromCollection(token), power, toughness);

							java.util.Map<Parameter, Set> setPTParameters = new java.util.HashMap<Parameter, Set>();
							setPTParameters.put(Parameter.CAUSE, cause);
							setPTParameters.put(Parameter.EFFECT, new Set(part));
							setPTParameters.put(Parameter.EXPIRES, new Set(Empty.instance()));
							Event setPT = createEvent(game, "Its power is equal to that creature's power and its toughness is equal to that creature's toughness.", EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, setPTParameters);
							setPT.perform(event, false);

							// optimization; we should be done once we find the
							// thing that was destroyed
							break;
						}
			}

			event.setResult(Empty.set);
			return true;
		}
	};

	public static final class IsABitch extends ActivatedAbility
	{
		public IsABitch(GameState state)
		{
			super(state, "(B)(B)(B), (T): Destroy target creature. If that creature is put into a graveyard this way, put a black Vampire creature token onto the battlefield. Its power is equal to that creature's power and its toughness is equal to that creature's toughness.");
			this.setManaCost(new ManaPool("BBB"));
			this.costsTap = true;

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			EventFactory effect = new EventFactory(KALITAS_EVENT, "Destroy target creature. If that creature is put into a graveyard this way, put a black Vampire creature token onto the battlefield. Its power is equal to that creature's power and its toughness is equal to that creature's toughness.");
			effect.parameters.put(EventType.Parameter.CAUSE, This.instance());
			effect.parameters.put(EventType.Parameter.PLAYER, You.instance());
			effect.parameters.put(EventType.Parameter.OBJECT, targetedBy(target));
			this.addEffect(effect);
		}
	}

	public KalitasBloodchiefofGhet(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// (B)(B)(B), (T): Destroy target creature. If that creature is put into
		// a graveyard this way, put a black Vampire creature token onto the
		// battlefield. Its power is equal to that creature's power and its
		// toughness is equal to that creature's toughness.
		this.addAbility(new IsABitch(state));
	}
}
