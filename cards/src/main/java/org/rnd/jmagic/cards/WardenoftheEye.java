package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Warden of the Eye")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.DJINN})
@ManaCost("2URW")
@ColorIdentity({Color.RED, Color.WHITE, Color.BLUE})
public final class WardenoftheEye extends Card
{
	public static final class WardenoftheEyeAbility0 extends EventTriggeredAbility
	{
		public WardenoftheEyeAbility0(GameState state)
		{
			super(state, "When Warden of the Eye enters the battlefield, return target noncreature, nonland card from your graveyard to your hand.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator deadThings = RelativeComplement.instance(InZone.instance(GraveyardOf.instance(You.instance())), HasType.instance(Type.CREATURE, Type.LAND));
			SetGenerator target = targetedBy(this.addTarget(deadThings, "target noncreature, nonland card from your graveyard"));
			this.addEffect(putIntoHand(target, You.instance(), "Return target noncreature, nonland card from your graveyard to your hand."));
		}
	}

	public WardenoftheEye(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// When Warden of the Eye enters the battlefield, return target
		// noncreature, nonland card from your graveyard to your hand.
		this.addAbility(new WardenoftheEyeAbility0(state));
	}
}
