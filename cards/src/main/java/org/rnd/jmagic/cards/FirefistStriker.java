package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Firefist Striker")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SOLDIER})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class FirefistStriker extends Card
{
	public static final class FirefistStrikerAbility0 extends EventTriggeredAbility
	{
		public FirefistStrikerAbility0(GameState state)
		{
			super(state, "Whenever Firefist Striker and at least two other creatures attack, target creature can't block this turn.");
			this.addPattern(battalion());

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(target, Blocking.instance())));
			this.addEffect(createFloatingEffect("Target creature can't block this turn", part));
		}
	}

	public FirefistStriker(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Battalion \u2014 Whenever Firefist Striker and at least two other
		// creatures attack, target creature can't block this turn.
		this.addAbility(new FirefistStrikerAbility0(state));
	}
}
