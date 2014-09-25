package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Deflecting Palm")
@Types({Type.INSTANT})
@ManaCost("RW")
@ColorIdentity({Color.RED, Color.WHITE})
public final class DeflectingPalm extends Card
{
	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("DeflectingPalm", "Choose a source of damage.", true);

	public static final class PreventAndDealEffect extends DamageReplacementEffect
	{
		private SetGenerator chosenSource;

		public PreventAndDealEffect(Game game, SetGenerator chosenSource)
		{
			super(game, "The next time a source of your choice would deal damage to you this turn, prevent that damage. If damage is prevented this way, Deflecting Palm deals that much damage to that source's controller.");
			this.makePreventionEffect();
			this.chosenSource = chosenSource;
		}

		@Override
		public DamageAssignment.Batch match(Event context, DamageAssignment.Batch damageAssignments)
		{
			Identified deflectingPalm = this.getSourceObject(context.game.actualState);
			int you = You.instance().evaluate(context.game, deflectingPalm).getOne(Player.class).ID;
			int source = chosenSource.evaluate(context.game, deflectingPalm).getOne(Identified.class).ID;

			return damageAssignments.stream() //
			.filter(dmg -> dmg.takerID == you && dmg.sourceID == source) //
			.collect(java.util.stream.Collectors.toCollection(DamageAssignment.Batch::new));
		}

		@Override
		public java.util.List<EventFactory> prevent(DamageAssignment.Batch damageAssignments)
		{
			if(damageAssignments.isEmpty())
				return java.util.Collections.emptyList();

			int prevented = damageAssignments.size();
			int sourceID = damageAssignments.iterator().next().sourceID;
			SetGenerator controller = ControllerOf.instance(IdentifiedWithID.instance(sourceID));

			damageAssignments.clear();
			return java.util.Collections.singletonList(spellDealDamage(prevented, controller, "Deflecting Palm deals that much damage to that source's controller."));
		}

		@Override
		public java.util.Collection<GameObject> refersTo(GameState state)
		{
			return java.util.Collections.singleton((GameObject)this.getSourceObject(state));
		}
	}

	public DeflectingPalm(GameState state)
	{
		super(state);

		// The next time a source of your choice would deal damage to you this
		// turn, prevent that damage. If damage is prevented this way,
		// Deflecting Palm deals that much damage to that source's controller.

		EventFactory choose = playerChoose(You.instance(), 1, AllSourcesOfDamage.instance(), PlayerInterface.ChoiceType.DAMAGE_SOURCE, REASON, "The next time a source of your choice");
		this.addEffect(choose);

		DamageReplacementEffect e = new PreventAndDealEffect(state.game, EffectResult.instance(choose));
		this.addEffect(createFloatingReplacement(e, "would deal damage to you this turn, prevent that damage. If damage is prevented this way, Deflecting Palm deals that much damage to that source's controller."));
	}
}
