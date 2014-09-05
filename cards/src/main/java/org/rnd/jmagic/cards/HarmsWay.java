package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Harm's Way")
@Types({Type.INSTANT})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Magic2010.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class HarmsWay extends Card
{
	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("HarmsWay", "Choose a source of damage.", true);

	private static final class HarmsWayEffect extends DamageReplacementEffect
	{
		private final int chosenSourceID;
		private final int targetID;

		private HarmsWayEffect(Game game, String name, int chosenSourceID, int targetID)
		{
			super(game, name);
			this.chosenSourceID = chosenSourceID;
			this.targetID = targetID;
			this.makeRedirectionEffect();
		}

		private boolean filter(Player you, DamageAssignment assignment)
		{
			Identified taker = this.game.actualState.get(assignment.takerID);

			// source of your choice
			if(assignment.sourceID != this.chosenSourceID)
				return false;

			// would deal to you
			boolean dealtToYou = assignment.takerID == you.ID;

			// and/or permanents you control
			boolean dealtToPermanent = taker.isGameObject() && ((GameObject)taker).isPermanent();
			boolean dealtToPermanentYouControl = dealtToPermanent && ((GameObject)taker).controllerID == you.ID;

			return dealtToYou || dealtToPermanentYouControl;
		}

		@Override
		public DamageAssignment.Batch match(Event context, DamageAssignment.Batch damageAssignments)
		{
			Player you = ((GameObject)this.getSourceObject(context.game.actualState)).getController(context.game.actualState);

			DamageAssignment.Batch ret = new DamageAssignment.Batch();
			for(DamageAssignment assignment: damageAssignments)
				if(filter(you, assignment))
					ret.add(assignment);
			return ret;
		}

		@Override
		public java.util.List<EventFactory> redirect(java.util.Map<DamageAssignment, DamageAssignment> damageAssignments)
		{
			// This method won't ever receive more damage than it can
			// redirect.
			this.getFloatingContinuousEffect(this.game.physicalState).damage -= damageAssignments.size();

			for(java.util.Map.Entry<DamageAssignment, DamageAssignment> redirect: damageAssignments.entrySet())
			{
				GameObject source = this.game.actualState.<GameObject>get(redirect.getKey().sourceID);
				Identified target = this.game.actualState.get(this.targetID);
				redirect.setValue(new DamageAssignment(source, target));
			}

			return new java.util.LinkedList<EventFactory>();
		}
	}

	/**
	 * @eparam CAUSE: the spell creating this shield
	 * @eparam PLAYER: the player choosing the source
	 * @eparam TARGET: who to redirect damage to
	 * @eparam RESULT: empty
	 */
	public static final EventType HARMS_WAY_EVENT = new EventType("HARMS_WAY_EVENT")
	{
		@Override
		public EventType.Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<EventType.Parameter, Set> parameters)
		{
			Player you = parameters.get(EventType.Parameter.PLAYER).getOne(Player.class);
			final int targetID = parameters.get(EventType.Parameter.TARGET).getOne(Identified.class).ID;

			Set damageSources = AllSourcesOfDamage.instance().evaluate(game.actualState, null);
			final int chosenSourceID = you.sanitizeAndChoose(game.actualState, 1, damageSources.getAll(GameObject.class), PlayerInterface.ChoiceType.DAMAGE_SOURCE, REASON).iterator().next().ID;

			DamageReplacementEffect replacement = new HarmsWayEffect(game, "The next 2 damage that a source of your choice would deal to you and/or permanents you control this turn is dealt to target creature or player instead.", chosenSourceID, targetID);

			ContinuousEffect.Part part = replacementEffectPart(replacement);

			java.util.Map<EventType.Parameter, Set> floaterParameters = new java.util.HashMap<EventType.Parameter, Set>();
			floaterParameters.put(EventType.Parameter.CAUSE, parameters.get(EventType.Parameter.CAUSE));
			floaterParameters.put(EventType.Parameter.EFFECT, new Set(part));
			floaterParameters.put(EventType.Parameter.DAMAGE, new Set(2));
			Event floaterEvent = createEvent(game, "The next 2 damage that a source of your choice would deal to you and/or permanents you control this turn is dealt to target creature or player instead.", EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, floaterParameters);
			boolean ret = floaterEvent.perform(event, true);

			event.setResult(Empty.set);

			return ret;
		}
	};

	public HarmsWay(GameState state)
	{
		super(state);

		Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");

		EventType.ParameterMap parameters = new EventType.ParameterMap();
		parameters.put(EventType.Parameter.CAUSE, This.instance());
		parameters.put(EventType.Parameter.PLAYER, You.instance());
		parameters.put(EventType.Parameter.TARGET, targetedBy(target));
		this.addEffect(new EventFactory(HARMS_WAY_EVENT, parameters, "The next 2 damage that a source of your choice would deal to you and/or permanents you control this turn is dealt to target creature or player instead."));
	}
}
