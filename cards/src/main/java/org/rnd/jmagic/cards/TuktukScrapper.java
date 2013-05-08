package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Tuktuk Scrapper")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN, SubType.ARTIFICER, SubType.ALLY})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class TuktukScrapper extends Card
{
	/**
	 * @eparam SOURCE: tuktuk scrapper
	 * @eparam CAUSE: tuktuk's ability
	 * @eparam TARGET: permanent that is the target of CAUSE
	 * @eparam NUMBER: number of allies controlled by controller of CAUSE
	 * @eparam CONTROLLER: controller of TARGET
	 */
	public static final EventType TUKTUK_SCRAPPER_EVENT = new EventType("TUKTUK_SCRAPPER_EVENT")
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
				damageParameters.put(Parameter.NUMBER, parameters.get(Parameter.NUMBER));
				damageParameters.put(Parameter.TAKER, parameters.get(Parameter.CONTROLLER));
				Event damage = createEvent(game, "Tuktuk Scrapper deals damage to that artifact's controller equal to the number of Allies you control.", EventType.DEAL_DAMAGE_EVENLY, damageParameters);
				damage.perform(event, true);
			}

			return true;
		}
	};

	public static final class AllyKillArtifact extends EventTriggeredAbility
	{
		public AllyKillArtifact(GameState state)
		{
			super(state, "Whenever Tuktuk Scrapper or another Ally enters the battlefield under your control, you may destroy target artifact. If that artifact is put into a graveyard this way, Tuktuk Scrapper deals damage to that artifact's controller equal to the number of Allies you control.");
			this.addPattern(allyTrigger());

			SetGenerator target = targetedBy(this.addTarget(ArtifactPermanents.instance(), "target artifact"));

			EventFactory effect = new EventFactory(TUKTUK_SCRAPPER_EVENT, "Destroy target artifact. If that artifact is put into a graveyard this way, Tuktuk Scrapper deals damage to that artifact's controller equal to the number of Allies you control.");
			effect.parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			effect.parameters.put(EventType.Parameter.CAUSE, This.instance());
			effect.parameters.put(EventType.Parameter.TARGET, target);
			effect.parameters.put(EventType.Parameter.NUMBER, Count.instance(ALLIES_YOU_CONTROL));
			effect.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			this.addEffect(youMay(effect, "You may destroy target artifact. If that artifact is put into a graveyard this way, Tuktuk Scrapper deals damage to that artifact's controller equal to the number of Allies you control."));
		}
	}

	public TuktukScrapper(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Whenever Tuktuk Scrapper or another Ally enters the battlefield under
		// your control, you may destroy target artifact. If that artifact is
		// put into a graveyard this way, Tuktuk Scrapper deals damage to that
		// artifact's controller equal to the number of Allies you control.
		this.addAbility(new AllyKillArtifact(state));
	}
}
