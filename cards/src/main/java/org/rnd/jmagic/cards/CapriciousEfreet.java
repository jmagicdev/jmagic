package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Capricious Efreet")
@Types({Type.CREATURE})
@SubTypes({SubType.EFREET})
@ManaCost("4RR")
@Printings({@Printings.Printed(ex = Magic2010.class, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class CapriciousEfreet extends Card
{

	/**
	 * @eparam CAUSE: Capricious Efreet's ability
	 * @eparam PERMANENT: objects to randomly choose from
	 * @eparam RESULT: the zone change
	 */
	public static EventType DESTROY_AT_RANDOM = new EventType("DESTROY_AT_RANDOM")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			java.util.List<GameObject> objects = new java.util.LinkedList<GameObject>(parameters.get(Parameter.PERMANENT).getAll(GameObject.class));
			if(objects.isEmpty())
				return false;

			java.util.Collections.shuffle(objects);
			GameObject destroyThis = objects.get(0);

			java.util.Map<Parameter, Set> destroyParameters = new java.util.HashMap<Parameter, Set>();
			destroyParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			destroyParameters.put(Parameter.PERMANENT, new Set(destroyThis));
			Event destroy = createEvent(game, "Destroy one of them at random.", DESTROY_PERMANENTS, destroyParameters);
			boolean ret = destroy.perform(event, false);

			event.setResult(destroy.getResultGenerator());
			return ret;
		}
	};

	public static final class RandomDestroy extends EventTriggeredAbility
	{
		public RandomDestroy(GameState state)
		{
			super(state, "At the beginning of your upkeep, choose target nonland permanent you control and up to two target nonland permanents you don't control. Destroy one of them at random.");

			// At the beginning of your upkeep,
			this.addPattern(atTheBeginningOfYourUpkeep());

			// choose target nonland permanent you control and up to two target
			// nonland permanents you don't control.
			SetGenerator nonlandPermanents = RelativeComplement.instance(Permanents.instance(), HasType.instance(Type.LAND));
			SetGenerator youControl = ControlledBy.instance(You.instance());
			Target target = this.addTarget(Intersect.instance(nonlandPermanents, youControl), "target nonland permanent you control");

			Target otherTarget = this.addTarget(RelativeComplement.instance(nonlandPermanents, youControl), "up to two target nonland permanents you don't control");
			otherTarget.setNumber(0, 2);

			// Destroy one of them at random.
			EventType.ParameterMap parameters = new EventType.ParameterMap();
			parameters.put(EventType.Parameter.CAUSE, This.instance());
			parameters.put(EventType.Parameter.PERMANENT, targetedBy(target, otherTarget));
			this.addEffect(new EventFactory(DESTROY_AT_RANDOM, parameters, "Destroy one of them at random."));
		}
	}

	public CapriciousEfreet(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(4);

		this.addAbility(new RandomDestroy(state));
	}
}
