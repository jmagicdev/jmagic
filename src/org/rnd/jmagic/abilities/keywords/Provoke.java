package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

/**
 * 702.36. Provoke
 * 
 * 702.36a Provoke is a triggered ability. "Provoke" means "Whenever this
 * creature attacks, you may choose to have target creature defending player
 * controls block this creature this combat if able. If you do, untap that
 * creature."
 * 
 * 702.36b If a creature has multiple instances of provoke, each triggers
 * separately.
 */
@Name("Provoke")
public final class Provoke extends Keyword
{
	public Provoke(GameState state)
	{
		super(state, "Provoke");
	}

	@Override
	public java.util.List<NonStaticAbility> createNonStaticAbilities()
	{
		java.util.List<NonStaticAbility> ret = new java.util.LinkedList<NonStaticAbility>();
		ret.add(new ProvokeAbility(this.state));
		return ret;
	}

	public static final class ProvokeAbility extends EventTriggeredAbility
	{
		public ProvokeAbility(GameState state)
		{
			super(state, "When this attacks, you may have target creature defending player controls untap and block it if able.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.DECLARE_ONE_ATTACKER);
			pattern.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			this.addPattern(pattern);

			SetGenerator defendingPlayer = DefendingPlayer.instance(ABILITY_SOURCE_OF_THIS);
			Target target = this.addTarget(Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(defendingPlayer)), "target creature defending player controls");
			SetGenerator targetCreature = targetedBy(target);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_REQUIREMENT);
			part.parameters.put(ContinuousEffectType.Parameter.ATTACKING, ABILITY_SOURCE_OF_THIS);
			part.parameters.put(ContinuousEffectType.Parameter.DEFENDING, targetCreature);

			EventFactory effectFactory = createFloatingEffect(Intersect.instance(CurrentStep.instance(), EndOfCombatStepOf.instance(Players.instance())), "Target creature defending player controls must block this creature this combat if able.", part);
			EventFactory mayFactory = youMay(effectFactory, "You may choose to have target creature defending player controls block this creature this combat if able.");
			EventFactory untapFactory = untap(targetCreature, "Untap that creature.");

			EventFactory ifFactory = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "Target creature defending player controls untap and block it if able.");
			ifFactory.parameters.put(EventType.Parameter.IF, Identity.instance(mayFactory));
			ifFactory.parameters.put(EventType.Parameter.THEN, Identity.instance(untapFactory));
			this.addEffect(ifFactory);
		}
	}
}
