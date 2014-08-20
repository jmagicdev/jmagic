package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Rise from the Grave")
@Types({Type.SORCERY})
@ManaCost("4B")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class RisefromtheGrave extends Card
{
	/**
	 * @eparam CAUSE: Rise from the Grave
	 * @eparam PLAYER: CAUSE's controller
	 * @eparam OBJECT: CAUSE's target
	 * @eparam RESULT: empty
	 */
	public static final EventType RISE_FROM_THE_GRAVE_EVENT = new EventType("RISE_FROM_THE_GRAVE_EVENT")
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
			Set you = parameters.get(Parameter.PLAYER);
			GameObject target = parameters.get(Parameter.OBJECT).getOne(GameObject.class);

			// Put target creature card in a graveyard onto the battlefield
			// under your control.
			java.util.Map<Parameter, Set> ontoFieldParameters = new java.util.HashMap<Parameter, Set>();
			ontoFieldParameters.put(Parameter.CAUSE, cause);
			ontoFieldParameters.put(Parameter.CONTROLLER, you);
			ontoFieldParameters.put(Parameter.OBJECT, new Set(target));
			Event ontoField = createEvent(game, "Put target creature card from a graveyard onto the battlefield under your control.", PUT_ONTO_BATTLEFIELD, ontoFieldParameters);

			// Not top level -- the creature needs to be a black Zombie the
			// entire time it's on the battlefield.
			ontoField.perform(event, false);
			SetGenerator thatCreature = NewObjectOf.instance(ontoField.getResultGenerator());

			// That creature is a black Zombie in addition to its other colors
			// and types.
			ContinuousEffect.Part blackPart = new ContinuousEffect.Part(ContinuousEffectType.ADD_COLOR);
			blackPart.parameters.put(ContinuousEffectType.Parameter.OBJECT, thatCreature);
			blackPart.parameters.put(ContinuousEffectType.Parameter.COLOR, Identity.instance(Color.BLACK));

			ContinuousEffect.Part zombiePart = new ContinuousEffect.Part(ContinuousEffectType.ADD_TYPES);
			zombiePart.parameters.put(ContinuousEffectType.Parameter.OBJECT, thatCreature);
			zombiePart.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(SubType.ZOMBIE));

			EventFactory factory = new EventFactory(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, "That creature is a black Zombie in addition to its other colors and types.");
			factory.parameters.put(Parameter.CAUSE, Identity.fromCollection(cause));
			factory.parameters.put(Parameter.EFFECT, Identity.instance(blackPart, zombiePart));
			factory.parameters.put(Parameter.EXPIRES, Identity.instance(Empty.instance()));
			ontoField.getResult().getOne(ZoneChange.class).events.add(factory);

			event.setResult(Empty.set);
			return true;
		}
	};

	public RisefromtheGrave(GameState state)
	{
		super(state);

		SetGenerator inAGraveyard = InZone.instance(GraveyardOf.instance(Players.instance()));
		SetGenerator targetable = Intersect.instance(HasType.instance(Type.CREATURE), inAGraveyard);
		Target target = this.addTarget(targetable, "target creature card from a graveyard");

		EventType.ParameterMap parameters = new EventType.ParameterMap();
		parameters.put(EventType.Parameter.CAUSE, This.instance());
		parameters.put(EventType.Parameter.PLAYER, You.instance());
		parameters.put(EventType.Parameter.OBJECT, targetedBy(target));
		this.addEffect(new EventFactory(RISE_FROM_THE_GRAVE_EVENT, parameters, "Put target creature card from a graveyard onto the battlefield under your control. That creature is a black Zombie in addition to its other colors and types."));
	}
}
