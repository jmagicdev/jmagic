package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Smoke Teller")
@Types({Type.CREATURE})
@SubTypes({SubType.SHAMAN, SubType.HUMAN})
@ManaCost("1G")
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class SmokeTeller extends Card
{
	public static final class SmokeTellerAbility0 extends ActivatedAbility
	{
		public SmokeTellerAbility0(GameState state)
		{
			super(state, "(1)(U): Look at target face-down creature.");
			this.setManaCost(new ManaPool("(1)(U)"));
			SetGenerator faceDownCreatures = Intersect.instance(FaceDown.instance(), CreaturePermanents.instance());
			SetGenerator target = targetedBy(this.addTarget(faceDownCreatures, "target face-down creature"));
			this.addEffect(look(You.instance(), target, "Look at target face-down creature."));
		}
	}

	public SmokeTeller(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (1)(U): Look at target face-down creature.
		this.addAbility(new SmokeTellerAbility0(state));
	}
}
