package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.gameTypes.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Glimmervoid Basin")
@Types({Type.PLANE})
@SubTypes({SubType.MIRRODIN})
@Printings({@Printings.Printed(ex = Expansion.PLANECHASE, r = Rarity.COMMON)})
@ColorIdentity({})
public final class GlimmervoidBasin extends Card
{
	/**
	 * @eparam CAUSE: the ability
	 * @eparam PLAYER: the players that will be getting the creature tokens
	 * @eparam OBJECT: the creature to copy
	 * @eparam RESULT: empty
	 */
	public static final EventType GLIMMERVOID_BASIN_CREATURE_COPY = new EventType("GLIMMERVOID_BASIN_CREATURE_COPY")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			GameObject creature = parameters.get(Parameter.OBJECT).getOne(GameObject.class);

			for(Player player: game.actualState.apnapOrder(parameters.get(Parameter.PLAYER)))
			{
				java.util.Map<Parameter, Set> copyParameters = new java.util.HashMap<Parameter, Set>();
				copyParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
				copyParameters.put(Parameter.CONTROLLER, new Set(player));
				copyParameters.put(Parameter.OBJECT, new Set(creature));
				Event copyEvent = createEvent(game, player + " gets a copy of " + creature + ".", EventType.CREATE_TOKEN_COPY, copyParameters);
				copyEvent.perform(event, false);
			}

			event.setResult(Empty.set);

			return true;
		}
	};

	public static final class SpellReflection extends EventTriggeredAbility
	{
		public SpellReflection(GameState state)
		{
			super(state, "Whenever a player casts an instant or sorcery spell with a single target, he or she copies that spell for each other spell, permanent, card not on the battlefield, and/or player the spell could target. Each copy targets a different one of them.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.withResult(Intersect.instance(HasType.instance(Type.INSTANT, Type.SORCERY), HasOneTarget.instance()));
			this.addPattern(pattern);

			EventFactory factory = new EventFactory(EventType.COPY_SPELL_FOR_EACH_TARGET, "He or she copies that spell for each other spell, permanent, card not on the battlefield, and/or player the spell could target. Each copy targets a different one of them.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.OBJECT, EventResult.instance(TriggerEvent.instance(This.instance())));
			factory.parameters.put(EventType.Parameter.TARGET, Union.instance(Spells.instance(), Permanents.instance(), Cards.instance(), Players.instance()));
			this.addEffect(factory);

			this.canTrigger = Planechase.triggeredAbilityCanTrigger;
		}
	}

	public static final class CreatureReflection extends EventTriggeredAbility
	{
		public CreatureReflection(GameState state)
		{
			super(state, "Whenever you roll (C), choose target creature. Each player except that creature's controller puts a token that's a copy of that creature onto the battlefield.");

			this.addPattern(Planechase.wheneverYouRollChaos());

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			EventFactory factory = new EventFactory(GLIMMERVOID_BASIN_CREATURE_COPY, "Choose target creature. Each player except that creature's controller puts a token that's a copy of that creature onto the battlefield.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.PLAYER, RelativeComplement.instance(Players.instance(), ControllerOf.instance(targetedBy(target))));
			factory.parameters.put(EventType.Parameter.OBJECT, targetedBy(target));
			this.addEffect(factory);

			this.canTrigger = Planechase.triggeredAbilityCanTrigger;
		}
	}

	public GlimmervoidBasin(GameState state)
	{
		super(state);

		this.addAbility(new SpellReflection(state));

		this.addAbility(new CreatureReflection(state));
	}
}
