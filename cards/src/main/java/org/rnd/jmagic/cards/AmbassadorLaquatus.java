package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ambassador Laquatus")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.MERFOLK, SubType.WIZARD})
@ManaCost("1UU")
@ColorIdentity({Color.BLUE})
public final class AmbassadorLaquatus extends Card
{
	public static final class ThreeMillThree extends ActivatedAbility
	{
		public ThreeMillThree(GameState state)
		{
			super(state, "(3): Target player puts the top three cards of his or her library into his or her graveyard.");
			this.setManaCost(new ManaPool("3"));
			Target target = this.addTarget(Players.instance(), "target player");

			this.addEffect(millCards(targetedBy(target), 3, "Target player puts the top three cards of his or her library into his or her graveyard."));
		}
	}

	public AmbassadorLaquatus(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		this.addAbility(new ThreeMillThree(state));
	}
}
