package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Awe Strike")
@Types({Type.INSTANT})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class AweStrike extends Card
{
	public static final class AweStrikeShield extends DamageReplacementEffect
	{
		private SetGenerator target;

		public AweStrikeShield(Game game, SetGenerator target)
		{
			super(game, "The next time target creature would deal damage this turn, prevent that damage.  You gain life equal to the damage prevented this way.");
			this.makePreventionEffect();
			this.target = target;
		}

		@Override
		public DamageAssignment.Batch match(Event context, DamageAssignment.Batch damageAssignments)
		{
			GameObject target = this.target.evaluate(context.game, this.getSourceObject(context.state)).getOne(GameObject.class);
			if(target == null)
				return new DamageAssignment.Batch();

			DamageAssignment.Batch ret = new DamageAssignment.Batch();
			for(DamageAssignment assignment: damageAssignments)
				if(assignment.sourceID == target.ID)
					ret.add(assignment);

			return ret;
		}

		@Override
		public java.util.List<EventFactory> prevent(DamageAssignment.Batch damageAssignments)
		{
			if(damageAssignments.isEmpty())
				return new java.util.LinkedList<EventFactory>();

			EventFactory gainLife = new EventFactory(EventType.GAIN_LIFE, "You gain life equal to the damage prevented this way");
			gainLife.parameters.put(EventType.Parameter.CAUSE, IdentifiedWithID.instance(damageAssignments.iterator().next().sourceID));
			gainLife.parameters.put(EventType.Parameter.PLAYER, You.instance());
			gainLife.parameters.put(EventType.Parameter.NUMBER, numberGenerator(damageAssignments.size()));

			damageAssignments.clear();
			return java.util.Collections.singletonList(gainLife);
		}
	}

	public AweStrike(GameState state)
	{
		super(state);

		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

		DamageReplacementEffect replacement = new AweStrikeShield(this.game, targetedBy(target));

		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.REPLACEMENT_EFFECT);
		part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Identity.instance(replacement));

		EventFactory factory = new EventFactory(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, "The next time target creature would deal damage this turn, prevent that damage.  You gain life equal to the damage prevented this way.");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.EFFECT, Identity.instance(part));
		factory.parameters.put(EventType.Parameter.USES, numberGenerator(1));
		this.addEffect(factory);
	}
}
