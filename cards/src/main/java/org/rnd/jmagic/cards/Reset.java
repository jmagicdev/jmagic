package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Reset")
@Types({Type.INSTANT})
@ManaCost("UU")
@Printings({@Printings.Printed(ex = Legends.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class Reset extends Card
{
	public static final class ResetStatic extends StaticAbility
	{
		private static final class NotAfterOpponentsUpkeepStep extends SetGenerator
		{
			private static NotAfterOpponentsUpkeepStep _instance = null;

			public static NotAfterOpponentsUpkeepStep instance()
			{
				if(_instance == null)
					_instance = new NotAfterOpponentsUpkeepStep();
				return _instance;
			}

			@Override
			public Set evaluate(GameState state, Identified thisObject)
			{
				Turn currentTurn = state.currentTurn();

				boolean turnBelongsToOpponent = false;
				if(currentTurn != null)
					for(Player player: OpponentsOf.get(state, ((GameObject)thisObject).getController(state)).getAll(Player.class))
						if(player.ID == currentTurn.ownerID)
						{
							turnBelongsToOpponent = true;
							break;
						}

				if(!turnBelongsToOpponent)
					return NonEmpty.set;

				if(state.getTracker(AfterUpkeep.class).getValue(state))
					return Empty.set;

				return NonEmpty.set;
			}
		}

		public static final class AfterUpkeep extends Tracker<Boolean>
		{
			private boolean afterUpkeep = false;

			@Override
			protected Boolean getValueInternal()
			{
				return this.afterUpkeep;
			}

			@Override
			protected void reset()
			{
				this.afterUpkeep = false;
			}

			@Override
			protected boolean match(GameState state, Event event)
			{
				if(event.type != EventType.BEGIN_STEP)
					return false;
				Step ran = event.parametersNow.get(EventType.Parameter.STEP).evaluate(state, null).getOne(Step.class);
				return ran.type == Step.StepType.UPKEEP;
			}

			@Override
			protected void update(GameState state, Event event)
			{
				this.afterUpkeep = true;
			}
		}

		public ResetStatic(GameState state)
		{
			super(state, "Cast Reset only during an opponent's turn after his or her upkeep step.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.CAST_SPELL_OR_ACTIVATE_ABILITY);
			pattern.put(EventType.Parameter.OBJECT, This.instance());

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(pattern));
			this.addEffectPart(part);

			state.ensureTracker(new AfterUpkeep());
			this.canApply = NotAfterOpponentsUpkeepStep.instance();
		}
	}

	public Reset(GameState state)
	{
		super(state);

		this.addAbility(new ResetStatic(state));

		SetGenerator landsYouControl = Intersect.instance(LandPermanents.instance(), ControlledBy.instance(You.instance()));
		this.addEffect(untap(landsYouControl, "Untap all lands you control."));
	}
}
