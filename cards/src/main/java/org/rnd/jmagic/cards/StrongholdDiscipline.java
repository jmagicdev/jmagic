package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Stronghold Discipline")
@Types({Type.SORCERY})
@ManaCost("2BB")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.NEMESIS, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class StrongholdDiscipline extends Card
{
	/**
	 * @eparam CAUSE: The cause for the destruction / source of damage
	 * @eparam RESULT: Empty
	 */
	public static final EventType STRONGHOLD_DISCIPLINE_EVENT = new EventType("STRONGHOLD_DISCIPLINE_EVENT")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			boolean ret = true;

			for(Player player: game.actualState.players)
			{
				java.util.Map<Parameter, Set> lifeLossParameters = new java.util.HashMap<Parameter, Set>();
				lifeLossParameters.put(EventType.Parameter.CAUSE, parameters.get(Parameter.CAUSE));
				lifeLossParameters.put(EventType.Parameter.PLAYER, new Set(player));
				lifeLossParameters.put(EventType.Parameter.NUMBER, Intersect.instance(ControlledBy.instance(Identity.instance(player)), CreaturePermanents.instance()).evaluate(game, null));
				Event loseLifeEvent = createEvent(game, player + " loses 1 life for each creature he or she controls.", EventType.LOSE_LIFE, lifeLossParameters);
				ret = loseLifeEvent.perform(event, true) && ret;
			}

			event.setResult(Empty.set);

			return ret;
		}

		@Override
		public String toString()
		{
			return "STRONGHOLD_DISCIPLINE_EVENT";
		}
	};

	public StrongholdDiscipline(GameState state)
	{
		super(state);

		EventType.ParameterMap parameters = new EventType.ParameterMap();
		parameters.put(EventType.Parameter.CAUSE, This.instance());
		this.addEffect(new EventFactory(STRONGHOLD_DISCIPLINE_EVENT, parameters, "Each player loses 1 life for each creature he or she controls."));
	}
}
