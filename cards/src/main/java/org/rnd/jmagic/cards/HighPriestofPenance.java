package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("High Priest of Penance")
@Types({Type.CREATURE})
@SubTypes({SubType.CLERIC, SubType.HUMAN})
@ManaCost("WB")
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class HighPriestofPenance extends Card
{
	public static final class HighPriestofPenanceAbility0 extends EventTriggeredAbility
	{
		public HighPriestofPenanceAbility0(GameState state)
		{
			super(state, "Whenever High Priest of Penance is dealt damage, you may destroy target nonland permanent.");
			this.addPattern(whenThisIsDealtDamage());

			SetGenerator target = targetedBy(this.addTarget(RelativeComplement.instance(Permanents.instance(), HasType.instance(Type.LAND)), "target nonland permanent"));
			this.addEffect(youMay(destroy(target, "Destroy target nonland permanent.")));
		}
	}

	public HighPriestofPenance(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Whenever High Priest of Penance is dealt damage, you may destroy
		// target nonland permanent.
		this.addAbility(new HighPriestofPenanceAbility0(state));
	}
}
