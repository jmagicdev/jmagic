package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Master Warcraft")
@Types({Type.INSTANT})
@ManaCost("2(R/W)(R/W)")
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.RARE), @Printings.Printed(ex = RavnicaCityOfGuilds.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE, Color.RED})
public final class MasterWarcraft extends Card
{
	public static final class MasterWarcraftAbility0 extends StaticAbility
	{
		public static final class AttackersDeclared extends SetGenerator
		{
			private static final class TrackAttackersDeclared extends Tracker<Boolean>
			{
				private boolean attackersDeclared = false;

				@Override
				protected Boolean getValueInternal()
				{
					return this.attackersDeclared;
				}

				@Override
				protected void reset()
				{
					this.attackersDeclared = false;
				}

				@Override
				protected boolean match(GameState state, Event event)
				{
					return (EventType.DECLARE_ATTACKERS == event.type);
				}

				@Override
				protected void update(GameState state, Event event)
				{
					this.attackersDeclared = true;
				}
			}

			private static AttackersDeclared INSTANCE = new AttackersDeclared();

			public static SetGenerator instance(GameState state)
			{
				state.ensureTracker(new TrackAttackersDeclared());
				return INSTANCE;
			}

			private AttackersDeclared()
			{
				// Declaring just for private modifier
			}

			@Override
			public Set evaluate(GameState state, Identified thisObject)
			{
				if(state.getTracker(TrackAttackersDeclared.class).getValue(state))
					return NonEmpty.set;
				return Empty.set;
			}
		}

		public MasterWarcraftAbility0(GameState state)
		{
			super(state, "Cast Master Warcraft only before attackers are declared.");

			SimpleEventPattern castSpell = new SimpleEventPattern(EventType.CAST_SPELL_OR_ACTIVATE_ABILITY);
			castSpell.put(EventType.Parameter.PLAYER, You.instance());
			castSpell.put(EventType.Parameter.OBJECT, This.instance());

			ContinuousEffect.Part prohibitEffect = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			prohibitEffect.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(castSpell));
			this.addEffectPart(prohibitEffect);

			this.canApply = AttackersDeclared.instance(state);
		}
	}

	public MasterWarcraft(GameState state)
	{
		super(state);

		// Cast Master Warcraft only before attackers are declared.
		this.addAbility(new MasterWarcraftAbility0(state));

		// You choose which creatures attack this turn.
		ContinuousEffect.Part attack = new ContinuousEffect.Part(ContinuousEffectType.OVERRIDE_CHOOSING_ATTACKERS);
		attack.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
		this.addEffect(createFloatingEffect("You choose which creatures attack this turn.", attack));

		// You choose which creatures block this turn and how those creatures
		// block.
		ContinuousEffect.Part block = new ContinuousEffect.Part(ContinuousEffectType.OVERRIDE_CHOOSING_BLOCKERS);
		block.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
		this.addEffect(createFloatingEffect("You choose which creatures block this turn and how those creatures block.", block));
	}
}
