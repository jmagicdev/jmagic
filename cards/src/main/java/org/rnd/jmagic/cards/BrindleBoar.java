package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Brindle Boar")
@Types({Type.CREATURE})
@SubTypes({SubType.BOAR})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class BrindleBoar extends Card
{
	public static final class BrindleBoarAbility0 extends ActivatedAbility
	{
		public BrindleBoarAbility0(GameState state)
		{
			super(state, "Sacrifice Brindle Boar: You gain 4 life.");
			this.addCost(sacrificeThis("Brindle Boar"));
			this.addEffect(gainLife(You.instance(), 4, "You gain 4 life."));
		}
	}

	public BrindleBoar(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Sacrifice Brindle Boar: You gain 4 life.
		this.addAbility(new BrindleBoarAbility0(state));
	}
}
