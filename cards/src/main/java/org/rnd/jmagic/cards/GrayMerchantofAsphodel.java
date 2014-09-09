package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Gray Merchant of Asphodel")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("3BB")
@ColorIdentity({Color.BLACK})
public final class GrayMerchantofAsphodel extends Card
{
	public static final class GrayMerchantofAsphodelAbility0 extends EventTriggeredAbility
	{
		public GrayMerchantofAsphodelAbility0(GameState state)
		{
			super(state, "When Gray Merchant of Asphodel enters the battlefield, each opponent loses X life, where X is your devotion to black. You gain life equal to the life lost this way.");
			this.addPattern(whenThisEntersTheBattlefield());

			EventFactory loseLife = loseLife(OpponentsOf.instance(You.instance()), DevotionTo.instance(Color.BLACK), "Each opponent loses X life, where X is your devotion to black.");
			this.addEffect(loseLife);

			SetGenerator lifeLost = Sum.instance(EffectResult.instance(loseLife));
			this.addEffect(gainLife(You.instance(), lifeLost, "You gain life equal to the life lost this way."));
		}
	}

	public GrayMerchantofAsphodel(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(4);

		// When Gray Merchant of Asphodel enters the battlefield, each opponent
		// loses X life, where X is your devotion to black. You gain life equal
		// to the life lost this way. (Each {B} in the mana costs of permanents
		// you control counts toward your devotion to black.)
		this.addAbility(new GrayMerchantofAsphodelAbility0(state));
	}
}
