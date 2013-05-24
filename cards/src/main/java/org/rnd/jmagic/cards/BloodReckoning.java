package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Blood Reckoning")
@Types({Type.ENCHANTMENT})
@ManaCost("3B")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class BloodReckoning extends Card
{
	public static final class BloodReckoningAbility0 extends EventTriggeredAbility
	{
		public BloodReckoningAbility0(GameState state)
		{
			super(state, "Whenever a creature attacks you or a planeswalker you control, that creature's controller loses 1 life.");

			SetGenerator planeswalkersYouControl = Intersect.instance(ControlledBy.instance(You.instance()), HasType.instance(Type.PLANESWALKER));

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.DECLARE_ONE_ATTACKER);
			pattern.put(EventType.Parameter.OBJECT, CreaturePermanents.instance());
			pattern.put(EventType.Parameter.DEFENDER, Union.instance(You.instance(), planeswalkersYouControl));
			this.addPattern(pattern);

			SetGenerator taker = ControllerOf.instance(EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.OBJECT));
			this.addEffect(permanentDealDamage(1, taker, "That creature's controller loses 1 life."));
		}
	}

	public BloodReckoning(GameState state)
	{
		super(state);

		// Whenever a creature attacks you or a planeswalker you control, that
		// creature's controller loses 1 life.
		this.addAbility(new BloodReckoningAbility0(state));
	}
}
