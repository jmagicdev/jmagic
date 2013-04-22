package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mangara of Corondor")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("1WW")
@Printings({@Printings.Printed(ex = Expansion.TIME_SPIRAL, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class MangaraofCorondor extends Card
{
	public static final class MangaraofCorondorAbility0 extends ActivatedAbility
	{
		public MangaraofCorondorAbility0(GameState state)
		{
			super(state, "(T): Exile Mangara of Corondor and target permanent.");
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(Permanents.instance(), "target permanent"));

			this.addEffect(exile(Union.instance(ABILITY_SOURCE_OF_THIS, target), "Exile Mangara of Corondor and target permanent."));
		}
	}

	public MangaraofCorondor(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (T): Exile Mangara of Corondor and target permanent.
		this.addAbility(new MangaraofCorondorAbility0(state));
	}
}
