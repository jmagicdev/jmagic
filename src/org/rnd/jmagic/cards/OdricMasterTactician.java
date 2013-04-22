package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Odric, Master Tactician")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.HUMAN})
@ManaCost("2WW")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class OdricMasterTactician extends Card
{
	public static final class OdricMasterTacticianAbility1 extends EventTriggeredAbility
	{
		public static final class AbilitySourceAndAtLeastNOthersAttack implements SetPattern
		{
			private int minimum;

			public AbilitySourceAndAtLeastNOthersAttack(int minimumOthers)
			{
				this.minimum = minimumOthers + 1;
			}

			@Override
			public boolean match(GameState state, Identified thisObject, Set set)
			{
				if(!set.contains(((NonStaticAbility)thisObject).getSource(state)))
					return false;

				if(set.size() < this.minimum)
					return false;

				return true;
			}

			@Override
			public void freeze(GameState state, Identified thisObject)
			{
				// Nothing to freeze
			}
		}

		public OdricMasterTacticianAbility1(GameState state)
		{
			super(state, "Whenever Odric, Master Tactician and at least three other creatures attack, you choose which creatures block this combat and how those creatures block.");
			SimpleEventPattern pattern = new SimpleEventPattern(EventType.DECLARE_ATTACKERS);
			pattern.withResult(new AbilitySourceAndAtLeastNOthersAttack(3));
			this.addPattern(pattern);

			EventFactory note = new EventFactory(NOTE, "");
			note.parameters.put(EventType.Parameter.OBJECT, CurrentPhase.instance());
			this.addEffect(note);

			SetGenerator expires = RelativeComplement.instance(CurrentPhase.instance(), EffectResult.instance(note));
			ContinuousEffect.Part block = new ContinuousEffect.Part(ContinuousEffectType.OVERRIDE_CHOOSING_BLOCKERS);
			block.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
			this.addEffect(createFloatingEffect(expires, "You choose which creatures block this combat and how those creatures block.", block));
		}
	}

	public OdricMasterTactician(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(4);

		// First strike (This creature deals combat damage before creatures
		// without first strike.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));

		// Whenever Odric, Master Tactician and at least three other creatures
		// attack, you choose which creatures block this combat and how those
		// creatures block.
		this.addAbility(new OdricMasterTacticianAbility1(state));
	}
}
