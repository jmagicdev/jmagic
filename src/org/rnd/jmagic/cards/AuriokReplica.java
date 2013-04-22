package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Auriok Replica")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.CLERIC})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class AuriokReplica extends Card
{
	public static final PlayerInterface.ChooseReason AURIOK_REPLICA_CHOOSEREASON = new PlayerInterface.ChooseReason("AuriokReplica", "Choose a source of damage.", true);

	public static final class AuriokReplicaAbility0 extends ActivatedAbility
	{
		public static final class AuriokPrevention extends DamageReplacementEffect
		{
			private final SetGenerator chosenSource;

			public AuriokPrevention(Game game, String name, SetGenerator chosenSource)
			{
				super(game, name);
				this.chosenSource = chosenSource;
				this.makePreventionEffect();
			}

			@Override
			public DamageAssignment.Batch match(Event context, DamageAssignment.Batch damageAssignments)
			{
				GameObject sourceObject = (GameObject)this.getSourceObject(context.game.actualState);
				Player you = sourceObject.getController(context.game.actualState);

				java.util.Set<Integer> ids = new java.util.HashSet<Integer>();

				for(Identified i: this.chosenSource.evaluate(context.game, sourceObject).getAll(Identified.class))
					ids.add(i.ID);

				DamageAssignment.Batch ret = new DamageAssignment.Batch();
				for(DamageAssignment assignment: damageAssignments)
					if(ids.contains(assignment.sourceID) && assignment.takerID == you.ID)
						ret.add(assignment);
				return ret;
			}

			@Override
			public java.util.List<EventFactory> prevent(DamageAssignment.Batch damageAssignments)
			{
				damageAssignments.clear();
				return new java.util.LinkedList<EventFactory>();
			}
		}

		public AuriokReplicaAbility0(GameState state)
		{
			super(state, "(W), Sacrifice Auriok Replica: Prevent all damage a source of your choice would deal to you this turn.");
			this.setManaCost(new ManaPool("(W)"));
			this.addCost(sacrificeThis("Auriok Replica"));

			EventFactory choose = new EventFactory(EventType.PLAYER_CHOOSE, "Choose a source of damage.");
			choose.parameters.put(EventType.Parameter.PLAYER, You.instance());
			choose.parameters.put(EventType.Parameter.CHOICE, AllSourcesOfDamage.instance());
			choose.parameters.put(EventType.Parameter.TYPE, Identity.instance(PlayerInterface.ChoiceType.DAMAGE_SOURCE, AURIOK_REPLICA_CHOOSEREASON));
			this.addEffect(choose);

			ContinuousEffect.Part part = replacementEffectPart(new AuriokPrevention(state.game, "Prevent all damage a source of your choice would deal to you this turn.", EffectResult.instance(choose)));
			this.addEffect(createFloatingEffect("Prevent all damage a source of your choice would deal to you this turn.", part));
		}
	}

	public AuriokReplica(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (W), Sacrifice Auriok Replica: Prevent all damage a source of your
		// choice would deal to you this turn.
		this.addAbility(new AuriokReplicaAbility0(state));
	}
}
