package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Acolyte's Reward")
@Types({Type.INSTANT})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class AcolytesReward extends Card
{
	public static final class PreventAndDealEffect extends DamageReplacementEffect
	{
		public PreventAndDealEffect(Game game, String name)
		{
			super(game, name);
			this.makePreventionEffect();
		}

		@Override
		public DamageAssignment.Batch match(Event context, DamageAssignment.Batch damageAssignments)
		{
			GameObject spell = (GameObject)(this.getSourceObject(context.game.actualState));
			Target target = spell.getMode(1).targets.get(0);
			int targetID = spell.getChosenTargets()[0].get(target).get(0).targetID;

			return damageAssignments.stream() //
			.filter(dmg -> dmg.takerID == targetID) //
			.collect(java.util.stream.Collectors.toCollection(DamageAssignment.Batch::new));
		}

		@Override
		public java.util.List<EventFactory> prevent(DamageAssignment.Batch damageAssignments)
		{
			if(damageAssignments.isEmpty())
				return java.util.Collections.emptyList();

			FloatingContinuousEffect fce = this.getFloatingContinuousEffect(this.game.physicalState);
			if(fce.damage == 0)
				return java.util.Collections.emptyList();

			GameObject spell = (GameObject)(this.getSourceObject(this.game.actualState));
			Target target = spell.getMode(1).targets.get(1);

			// This will never get more damage than it can prevent
			int prevented = damageAssignments.size();
			fce.damage -= prevented;
			damageAssignments.clear();
			return java.util.Collections.singletonList(spellDealDamage(prevented, targetedBy(target), "Acolyte's Reward deals that much damage to target creature or player."));
		}

		@Override
		public java.util.Collection<GameObject> refersTo(GameState state)
		{
			return java.util.Collections.singleton((GameObject)this.getSourceObject(state));
		}
	}

	public AcolytesReward(GameState state)
	{
		super(state);

		// Prevent the next X damage that would be dealt to target creature this
		// turn, where X is your devotion to white. If damage is prevented this
		// way, Acolyte's Reward deals that much damage to target creature or
		// player.

		this.addTarget(CreaturePermanents.instance(), "target creature");
		this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");

		ReplacementEffect prevent = new PreventAndDealEffect(state.game, "Prevent the next X damage that would be dealt to target creature this turn, where X is your devotion to white. If damage is prevented this way, Acolyte's Reward deals that much damage to target creature or player.");

		EventFactory fce = createFloatingEffect("Prevent the next X damage that would be dealt to target creature this turn, where X is your devotion to white. If damage is prevented this way, Acolyte's Reward deals that much damage to target creature or player.",//
				replacementEffectPart(prevent));
		fce.parameters.put(EventType.Parameter.DAMAGE, DevotionTo.instance(Color.WHITE));
		this.addEffect(fce);
	}
}
