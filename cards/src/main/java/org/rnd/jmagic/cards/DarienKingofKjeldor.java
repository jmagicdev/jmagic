package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Darien, King of Kjeldor")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.HUMAN})
@ManaCost("4WW")
@ColorIdentity({Color.WHITE})
public final class DarienKingofKjeldor extends Card
{
	public static final class PainRecruits extends EventTriggeredAbility
	{
		public PainRecruits(GameState state)
		{
			super(state, "Whenever you're dealt damage, you may put that many 1/1 white Soldier creature tokens onto the battlefield.");

			this.addPattern(whenIsDealtDamage(You.instance()));

			SetGenerator thatMany = Count.instance(TriggerDamage.instance(This.instance()));
			String effectName = "Put that many 1/1 white Soldier creature tokens onto the battlefield.";
			CreateTokensFactory tokens = new CreateTokensFactory(thatMany, numberGenerator(1), numberGenerator(1), effectName);
			tokens.setColors(Color.WHITE);
			tokens.setSubTypes(SubType.SOLDIER);
			this.addEffect(youMay(tokens.getEventFactory(), "You may put that many 1/1 white Soldier creature tokens onto the battlefield."));
		}
	}

	public DarienKingofKjeldor(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Whenever you're dealt damage, you may put that many 1/1 white Soldier
		// creature tokens onto the battlefield.
		this.addAbility(new PainRecruits(state));
	}
}
