package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Guile")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL, SubType.INCARNATION})
@ManaCost("3UUU")
@Printings({@Printings.Printed(ex = Expansion.LORWYN, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class Guile extends Card
{
	public static final class HesBig extends StaticAbility
	{
		public HesBig(GameState state)
		{
			super(state, "Guile can't be blocked except by three or more creatures.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(Count.instance(Blocking.instance(This.instance())), Between.instance(1, 2))));
			this.addEffectPart(part);
		}
	}

	public static final class CounterFun extends StaticAbility
	{
		/**
		 * @eparam CAUSE: the ability
		 * @eparam OBJECT: the spell that would be countered
		 * @eparam PLAYER: the player that may play the spell
		 * @eparam RESULT: empty
		 */
		public static final EventType GUILE_COUNTER = new EventType("GUILE_COUNTER")
		{

			@Override
			public Parameter affects()
			{
				return Parameter.OBJECT;
			}

			@Override
			public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
			{
				GameObject spell = parameters.get(Parameter.OBJECT).getOne(GameObject.class);

				java.util.Map<Parameter, Set> exileParameters = new java.util.HashMap<Parameter, Set>();
				exileParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
				exileParameters.put(Parameter.TO, ExileZone.instance().evaluate(game, null));
				exileParameters.put(Parameter.OBJECT, new Set(spell));
				Event exileEvent = createEvent(game, "Exile " + spell + ".", EventType.MOVE_OBJECTS, exileParameters);

				if(exileEvent.perform(event, true))
				{
					spell = game.actualState.get(exileEvent.getResult().getOne(ZoneChange.class).newObjectID);
					Player player = parameters.get(Parameter.CAUSE).getOne(GameObject.class).getController(game.actualState);

					java.util.Map<Parameter, Set> recastParameters = new java.util.HashMap<Parameter, Set>();
					recastParameters.put(EventType.Parameter.CAUSE, parameters.get(Parameter.CAUSE));
					recastParameters.put(EventType.Parameter.PLAYER, new Set(player));
					recastParameters.put(EventType.Parameter.OBJECT, new Set(spell));
					Event recast = createEvent(game, "Cast " + spell + " without paying its mana cost.", PLAY_WITHOUT_PAYING_MANA_COSTS, recastParameters);
					recast.perform(event, true);
				}

				event.setResult(Empty.set);
				return true;
			}
		};

		public CounterFun(GameState state)
		{
			super(state, "If a spell or ability you control would counter a spell, instead exile that spell and you may play that card without paying its mana cost.");

			SimpleEventPattern replace = new SimpleEventPattern(EventType.COUNTER_ONE);
			replace.put(EventType.Parameter.CAUSE, ControlledBy.instance(You.instance(), Stack.instance()));
			replace.put(EventType.Parameter.OBJECT, Spells.instance());

			EventReplacementEffect replacement = new EventReplacementEffect(state.game, "If a spell or ability you control would counter a spell, instead exile that spell and you may play that card without paying its mana cost.", replace);

			EventFactory factory = new EventFactory(GUILE_COUNTER, "Exile that spell and you may play that card without paying its mana cost.");
			factory.parameters.put(EventType.Parameter.OBJECT, EventParameter.instance(ReplacedBy.instance(Identity.instance(replacement)), EventType.Parameter.OBJECT));
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			replacement.addEffect(factory);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.REPLACEMENT_EFFECT);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Identity.instance(replacement));
			this.addEffectPart(part);
		}
	}

	public Guile(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		// Guile can't be blocked except by three or more creatures.
		this.addAbility(new HesBig(state));

		// If a spell or ability you control would counter a spell, instead
		// exile that spell and you may play that card without paying its mana
		// cost.
		this.addAbility(new CounterFun(state));

		// When Guile is put into a graveyard from anywhere, shuffle it into its
		// owner's library.
		this.addAbility(new org.rnd.jmagic.abilities.LorwynIncarnationTrigger(state, "Guile"));
	}
}
