package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Circle of Flame")
@Types({Type.ENCHANTMENT})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class CircleofFlame extends Card
{
	public static final class CircleofFlameAbility0 extends EventTriggeredAbility
	{
		public CircleofFlameAbility0(GameState state)
		{
			super(state, "Whenever a creature without flying attacks you or a planeswalker you control, Circle of Flame deals 1 damage to that creature.");

			SetGenerator withoutFlying = RelativeComplement.instance(CreaturePermanents.instance(), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class));
			SetGenerator youAndYours = Union.instance(You.instance(), Intersect.instance(HasType.instance(Type.PLANESWALKER), ControlledBy.instance(You.instance())));

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.DECLARE_ONE_ATTACKER);
			pattern.put(EventType.Parameter.OBJECT, withoutFlying);
			pattern.put(EventType.Parameter.DEFENDER, youAndYours);
			this.addPattern(pattern);

			SetGenerator thatCreature = EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.OBJECT);
			this.addEffect(permanentDealDamage(1, thatCreature, "Circle of Flame deals 1 damage to that creature."));
		}
	}

	public CircleofFlame(GameState state)
	{
		super(state);

		// Whenever a creature without flying attacks you or a planeswalker you
		// control, Circle of Flame deals 1 damage to that creature.
		this.addAbility(new CircleofFlameAbility0(state));
	}
}
