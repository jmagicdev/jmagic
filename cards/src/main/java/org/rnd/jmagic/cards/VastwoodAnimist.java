package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Vastwood Animist")
@Types({Type.CREATURE})
@SubTypes({SubType.ELF, SubType.ALLY, SubType.SHAMAN})
@ManaCost("2G")
@ColorIdentity({Color.GREEN})
public final class VastwoodAnimist extends Card
{
	public static final class VastwoodAnimistAbility0 extends ActivatedAbility
	{
		public VastwoodAnimistAbility0(GameState state)
		{
			super(state, "(T): Target land you control becomes an X/X Elemental creature until end of turn, where X is the number of Allies you control. It's still a land.");
			this.costsTap = true;

			SetGenerator landsYouControl = Intersect.instance(LandPermanents.instance(), ControlledBy.instance(You.instance()));
			SetGenerator target = targetedBy(this.addTarget(landsYouControl, "target land you control"));
			SetGenerator X = Count.instance(ALLIES_YOU_CONTROL);

			Animator animator = new Animator(target, X, X);
			animator.addSubType(SubType.ELEMENTAL);
			this.addEffect(createFloatingEffect("Target land you control becomes an X/X Elemental creature until end of turn, where X is the number of Allies you control. It's still a land.", animator.getParts()));
		}
	}

	public VastwoodAnimist(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (T): Target land you control becomes an X/X Elemental creature until
		// end of turn, where X is the number of Allies you control. It's still
		// a land.
		this.addAbility(new VastwoodAnimistAbility0(state));
	}
}
