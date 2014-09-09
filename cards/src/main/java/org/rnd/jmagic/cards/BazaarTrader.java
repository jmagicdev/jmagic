package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Bazaar Trader")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class BazaarTrader extends Card
{
	public static final class BazaarTraderAbility0 extends ActivatedAbility
	{
		public BazaarTraderAbility0(GameState state)
		{
			super(state, "(T): Target player gains control of target artifact, creature, or land you control.");
			this.costsTap = true;

			Target targetPlayer = this.addTarget(Players.instance(), "target player");

			SetGenerator stuff = Union.instance(ArtifactPermanents.instance(), CreaturePermanents.instance(), LandPermanents.instance());
			SetGenerator yourStuff = Intersect.instance(stuff, ControlledBy.instance(You.instance()));
			Target targetThing = this.addTarget(yourStuff, "target artifact, creature, or land you control");

			ContinuousEffect.Part changeControl = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
			changeControl.parameters.put(ContinuousEffectType.Parameter.OBJECT, targetedBy(targetThing));
			changeControl.parameters.put(ContinuousEffectType.Parameter.PLAYER, targetedBy(targetPlayer));
			EventFactory effect = createFloatingEffect("Target player gains control of target artifact, creature, or land you control.", changeControl);
			effect.parameters.put(EventType.Parameter.EXPIRES, Identity.instance(Empty.instance()));
			this.addEffect(effect);
		}
	}

	public BazaarTrader(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (T): Target player gains control of target artifact, creature, or
		// land you control.
		this.addAbility(new BazaarTraderAbility0(state));
	}
}
