package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Transguild Courier")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.GOLEM})
@ManaCost("4")
@Printings({@Printings.Printed(ex = Dissension.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE, Color.BLUE, Color.BLACK, Color.GREEN, Color.RED})
public final class TransguildCourier extends Card
{
	public TransguildCourier(GameState state)
	{
		super(state);
		this.setColorIndicator(Color.allColors());
		this.setPower(3);
		this.setToughness(3);
	}
}
