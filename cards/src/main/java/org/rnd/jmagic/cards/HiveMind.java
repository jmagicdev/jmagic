package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Hive Mind")
@Types({Type.ENCHANTMENT})
@ManaCost("5U")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class HiveMind extends Card
{
	/**
	 * @eparam CAUSE: Hive Mind's trigger
	 * @eparam OBJECT: The thing being copied
	 * @eparam PLAYER: Who is copying it
	 * @eparam RESULT: empty
	 */
	public static EventType HIVE_MIND_COPIES = new EventType("HIVE_MIND_COPIES")
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
			Set toCopy = parameters.get(Parameter.OBJECT);
			for(Player player: event.state.apnapOrder(parameters.get(Parameter.PLAYER)))
			{
				java.util.Map<Parameter, Set> newParameters = new java.util.HashMap<Parameter, Set>();
				newParameters.put(Parameter.CAUSE, cause);
				newParameters.put(Parameter.OBJECT, toCopy);
				newParameters.put(Parameter.PLAYER, new Set(player));
				createEvent(game, player + " copies " + toCopy + " and may choose new targets for it.", EventType.COPY_SPELL_OR_ABILITY, newParameters).perform(event, true);

			}

			event.setResult(Empty.set);
			return true;
		}
	};

	public static final class CopyStuff extends EventTriggeredAbility
	{
		public CopyStuff(GameState state)
		{
			super(state, "Whenever a player casts an instant or sorcery spell, each other player copies that spell. Each of those players may choose new targets for his or her copy.");

			// Whenever a player casts an instant or sorcery spell,
			SimpleEventPattern triggerPattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			triggerPattern.put(EventType.Parameter.OBJECT, HasType.instance(Type.INSTANT, Type.SORCERY));
			this.addPattern(triggerPattern);

			// each other player copies that spell. Each of those players may
			// choose new targets for his or her copy.
			SetGenerator triggerEvent = TriggerEvent.instance(This.instance());
			SetGenerator spell = EventParameter.instance(triggerEvent, EventType.Parameter.OBJECT);
			SetGenerator caster = ControllerOf.instance(spell);
			SetGenerator eachOtherPlayer = RelativeComplement.instance(Players.instance(), caster);

			EventType.ParameterMap copyParameters = new EventType.ParameterMap();
			copyParameters.put(EventType.Parameter.CAUSE, This.instance());
			copyParameters.put(EventType.Parameter.OBJECT, spell);
			copyParameters.put(EventType.Parameter.PLAYER, eachOtherPlayer);
			this.addEffect(new EventFactory(HIVE_MIND_COPIES, copyParameters, "Each other player copies that spell. Each of those players may choose new targets for his or her copy."));

		}
	}

	public HiveMind(GameState state)
	{
		super(state);

		this.addAbility(new CopyStuff(state));
	}
}
