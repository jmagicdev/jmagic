package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Phyrexian Unlife")
@Types({Type.ENCHANTMENT})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class PhyrexianUnlife extends Card
{
	public static final class PhyrexianUnlifeAbility0 extends StaticAbility
	{
		public PhyrexianUnlifeAbility0(GameState state)
		{
			super(state, "You don't lose the game for having 0 or less life.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.LOSE_GAME);
			pattern.put(EventType.Parameter.CAUSE, Identity.instance(Game.LESS_THAN_ZERO_LIFE));
			pattern.put(EventType.Parameter.PLAYER, You.instance());

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(pattern));
			this.addEffectPart(part);
		}
	}

	public static final class PhyrexianUnlifeAbility1 extends StaticAbility
	{
		private static final class DamageToYouPattern implements DamagePattern
		{
			@Override
			public java.util.Set<DamageAssignment.Batch> match(DamageAssignment.Batch damage, Identified thisObject, GameState state)
			{
				java.util.Set<DamageAssignment.Batch> ret = new java.util.HashSet<DamageAssignment.Batch>();
				DamageAssignment.Batch batch = new DamageAssignment.Batch();
				ret.add(new DamageAssignment.Batch(damage));
				if(thisObject instanceof Controllable)
				{
					int you = ((Controllable)thisObject).getController(state).ID;
					for(DamageAssignment assignment: damage)
						if(assignment.takerID == you)
							batch.add(assignment);
				}
				return ret;
			}
		}

		public PhyrexianUnlifeAbility1(GameState state)
		{
			super(state, "As long as you have 0 or less life, all damage is dealt to you as though its source had infect.");

			DamagePattern allDamage = new DamageToYouPattern();

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.DEAL_DAMAGE_AS_THOUGH_HAS_ABILITY);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Identity.instance(allDamage));
			part.parameters.put(ContinuousEffectType.Parameter.ABILITY, Identity.instance(org.rnd.jmagic.abilities.keywords.Infect.class));
			this.addEffectPart(part);

			this.canApply = Both.instance(this.canApply, Intersect.instance(LifeTotalOf.instance(You.instance()), Between.instance(Empty.instance(), numberGenerator(0))));
		}
	}

	public PhyrexianUnlife(GameState state)
	{
		super(state);

		// You don't lose the game for having 0 or less life.
		this.addAbility(new PhyrexianUnlifeAbility0(state));

		// As long as you have 0 or less life, all damage is dealt to you as
		// though its source had infect. (Damage is dealt to you in the form of
		// poison counters.)
		this.addAbility(new PhyrexianUnlifeAbility1(state));
	}
}
