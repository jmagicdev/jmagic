package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Everlasting Torment")
@Types({Type.ENCHANTMENT})
@ManaCost("2(B/R)")
@Printings({@Printings.Printed(ex = Expansion.SHADOWMOOR, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK, Color.RED})
public final class EverlastingTorment extends Card
{
	public static final class EverlastingTormentAbility0 extends StaticAbility
	{
		public EverlastingTormentAbility0(GameState state)
		{
			super(state, "Players can't gain life.");

			SimpleEventPattern gainPattern = new SimpleEventPattern(EventType.GAIN_LIFE_ONE_PLAYER);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(gainPattern));
			this.addEffectPart(part);
		}
	}

	public static final class EverlastingTormentAbility1 extends StaticAbility
	{
		public EverlastingTormentAbility1(GameState state)
		{
			super(state, "Damage can't be prevented.");
			this.addEffectPart(new ContinuousEffect.Part(ContinuousEffectType.DAMAGE_CANT_BE_PREVENTED));
		}
	}

	public static final class EverlastingTormentAbility2 extends StaticAbility
	{
		private static final class AllDamagePattern implements DamagePattern
		{
			@Override
			public java.util.Set<DamageAssignment.Batch> match(DamageAssignment.Batch damage, Identified thisObject, GameState state)
			{
				java.util.Set<DamageAssignment.Batch> ret = new java.util.HashSet<DamageAssignment.Batch>();
				ret.add(new DamageAssignment.Batch(damage));
				return ret;
			}
		}

		public EverlastingTormentAbility2(GameState state)
		{
			super(state, "All damage is dealt as though its source had wither.");

			DamagePattern allDamage = new AllDamagePattern();

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.DEAL_DAMAGE_AS_THOUGH_HAS_ABILITY);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Identity.instance(allDamage));
			part.parameters.put(ContinuousEffectType.Parameter.ABILITY, Identity.instance(org.rnd.jmagic.abilities.keywords.Wither.class));
			this.addEffectPart(part);
		}
	}

	public EverlastingTorment(GameState state)
	{
		super(state);

		// Players can't gain life.
		this.addAbility(new EverlastingTormentAbility0(state));

		// Damage can't be prevented.
		this.addAbility(new EverlastingTormentAbility1(state));

		// All damage is dealt as though its source had wither. (A source with
		// wither deals damage to creatures in the form of -1/-1 counters.)
		this.addAbility(new EverlastingTormentAbility2(state));
	}
}
