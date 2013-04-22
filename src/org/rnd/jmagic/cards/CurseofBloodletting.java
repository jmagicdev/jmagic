package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Curse of Bloodletting")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.CURSE, SubType.AURA})
@ManaCost("3RR")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class CurseofBloodletting extends Card
{
	public static final class DoubleDamageEffect extends DamageReplacementEffect
	{
		public DoubleDamageEffect(Game game)
		{
			super(game, "If a source would deal damage to enchanted player, it deals double that damage to that player instead.");
		}

		@Override
		public DamageAssignment.Batch match(Event context, DamageAssignment.Batch damageAssignments)
		{
			DamageAssignment.Batch batch = new DamageAssignment.Batch();

			GameObject source = (GameObject)this.getStaticSourceObject(context.game.actualState);
			int enchantedPlayer = source.getAttachedTo();

			for(DamageAssignment assignment: damageAssignments)
				if(context.state.<Identified>get(assignment.takerID).ID == enchantedPlayer)
					batch.add(assignment);

			return batch;
		}

		@Override
		public java.util.List<EventFactory> replace(DamageAssignment.Batch damageAssignments)
		{
			java.util.Collection<DamageAssignment> duplicates = new java.util.LinkedList<DamageAssignment>();
			for(DamageAssignment assignment: damageAssignments)
				duplicates.add(new DamageAssignment(assignment));
			damageAssignments.addAll(duplicates);

			return new java.util.LinkedList<EventFactory>();
		}
	}

	public static final class CurseofBloodlettingAbility1 extends StaticAbility
	{
		public CurseofBloodlettingAbility1(GameState state)
		{
			super(state, "If a source would deal damage to enchanted player, it deals double that damage to that player instead.");
			this.addEffectPart(replacementEffectPart(new DoubleDamageEffect(this.game)));
		}
	}

	public CurseofBloodletting(GameState state)
	{
		super(state);

		// Enchant player
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Player(state));

		// If a source would deal damage to enchanted player, it deals double
		// that damage to that player instead.
		this.addAbility(new CurseofBloodlettingAbility1(state));
	}
}
