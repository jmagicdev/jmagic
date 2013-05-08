package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Werewolf Ransacker")
@Types({Type.CREATURE})
@SubTypes({SubType.WEREWOLF})
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class WerewolfRansacker extends AlternateCard
{
	/**
	 * @eparam SOURCE: werewolf ransacker
	 * @eparam CAUSE: werewolf ransacker's ability
	 * @eparam TARGET: permanent that is the target of CAUSE
	 * @eparam CONTROLLER: controller of TARGET
	 */
	public static final EventType WEREWOLF_RANSACKER_EVENT = new EventType("WEREWOLF_RANSACKER_EVENT")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set target = parameters.get(Parameter.TARGET);

			java.util.Map<Parameter, Set> destroyParameters = new java.util.HashMap<Parameter, Set>();
			destroyParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			destroyParameters.put(Parameter.PERMANENT, target);
			Event destroy = createEvent(game, "Destroy target artifact", EventType.DESTROY_PERMANENTS, destroyParameters);
			destroy.perform(event, true);

			GameObject deadArtifact = game.actualState.get(target.getOne(GameObject.class).getActual().futureSelf);
			if(deadArtifact.getZone().isGraveyard())
			{
				java.util.Map<Parameter, Set> damageParameters = new java.util.HashMap<Parameter, Set>();
				damageParameters.put(Parameter.CAUSE, parameters.get(Parameter.SOURCE));
				damageParameters.put(Parameter.NUMBER, new Set(3));
				damageParameters.put(Parameter.TAKER, parameters.get(Parameter.CONTROLLER));
				Event damage = createEvent(game, "Werewolf Ransacker deals 3 damage to that artifact's controller.", EventType.DEAL_DAMAGE_EVENLY, damageParameters);
				damage.perform(event, true);
			}

			return true;
		}
	};

	public static final class WerewolfRansackerAbility0 extends EventTriggeredAbility
	{
		public WerewolfRansackerAbility0(GameState state)
		{
			super(state, "Whenever this creature transforms into Werewolf Ransacker, you may destroy target artifact. If that artifact is put into a graveyard this way, Werewolf Ransacker deals 3 damage to that artifact's controller.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.TRANSFORM_ONE_PERMANENT);
			pattern.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			this.addPattern(pattern);

			SetGenerator target = targetedBy(this.addTarget(ArtifactPermanents.instance(), "target artifact"));

			EventFactory factory = new EventFactory(WEREWOLF_RANSACKER_EVENT, "Destroy target artifact. If that artifact is put into a graveyard this way, Werewolf Ransacker deals 3 damage to that artifact's controller.");
			factory.parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.TARGET, target);
			factory.parameters.put(EventType.Parameter.CONTROLLER, ControllerOf.instance(target));
			this.addEffect(youMay(factory));
		}
	}

	public WerewolfRansacker(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(4);

		this.setColorIndicator(Color.RED);

		// Whenever this creature transforms into Werewolf Ransacker, you may
		// destroy target artifact. If that artifact is put into a graveyard
		// this way, Werewolf Ransacker deals 3 damage to that artifact's
		// controller.
		this.addAbility(new WerewolfRansackerAbility0(state));

		// At the beginning of each upkeep, if a player cast two or more spells
		// last turn, transform Werewolf Ransacker.
		this.addAbility(new org.rnd.jmagic.abilities.Werewolves.BecomeHuman(state, this.getName()));
	}
}
