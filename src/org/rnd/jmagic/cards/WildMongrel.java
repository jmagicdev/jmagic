package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Wild Mongrel")
@Types({Type.CREATURE})
@SubTypes({SubType.HOUND})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.ODYSSEY, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class WildMongrel extends Card
{
	/**
	 * @eparam CAUSE: the ability
	 * @eparam OBJECT: the mongrel
	 * @eparam PLAYER: the player choosing
	 * @eparam RESULT: empty
	 */
	public static final EventType WILD_MONGREL_EVENT = new EventType("WILD_MONGREL_EVENT")
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

			Color color = parameters.get(Parameter.PLAYER).getOne(Player.class).chooseColor(parameters.get(Parameter.OBJECT).getOne(GameObject.class).ID);
			if(color == null)
				return false;

			Set object = parameters.get(Parameter.OBJECT);

			ContinuousEffect.Part colorPart = new ContinuousEffect.Part(ContinuousEffectType.SET_COLOR);
			colorPart.parameters.put(ContinuousEffectType.Parameter.OBJECT, Identity.instance(object));
			colorPart.parameters.put(ContinuousEffectType.Parameter.COLOR, Identity.instance(color));
			Set effects = new Set(colorPart, modifyPowerAndToughness(Identity.instance(object), +1, +1));

			java.util.Map<Parameter, Set> fceParameters = new java.util.HashMap<Parameter, Set>();
			fceParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			fceParameters.put(Parameter.EFFECT, effects);
			Event fceEvent = createEvent(game, object + " gets +1/+1 and becomes " + color.toString() + " until end of turn.", EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, fceParameters);
			return fceEvent.perform(event, false);
		}
	};

	public static final class WildMongrelAbility0 extends ActivatedAbility
	{
		public WildMongrelAbility0(GameState state)
		{
			super(state, "Discard a card: Wild Mongrel gets +1/+1 and becomes the color of your choice until end of turn.");

			this.addCost(discardCards(You.instance(), 1, "Discard a card"));

			EventFactory factory = new EventFactory(WILD_MONGREL_EVENT, "Wild Mongrel gets +1/+1 and becomes the color of your choice until end of turn.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.OBJECT, AbilitySource.instance(This.instance()));
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(factory);
		}
	}

	public WildMongrel(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Discard a card: Wild Mongrel gets +1/+1 and becomes the color of your
		// choice until end of turn.
		this.addAbility(new WildMongrelAbility0(state));
	}
}
