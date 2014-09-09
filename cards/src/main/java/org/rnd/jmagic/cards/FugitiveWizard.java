package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Fugitive Wizard")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("U")
@ColorIdentity({Color.BLUE})
public final class FugitiveWizard extends Card
{
	public FugitiveWizard(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);
	}
}
