package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Dawnglare Invoker")
@Types({Type.CREATURE})
@SubTypes({SubType.KOR, SubType.WIZARD})
@ManaCost("2W")
@ColorIdentity({Color.WHITE})
public final class DawnglareInvoker extends Card
{
	public static final class DawnglareInvokerAbility1 extends ActivatedAbility
	{
		public DawnglareInvokerAbility1(GameState state)
		{
			super(state, "(8): Tap all creatures target player controls.");

			this.setManaCost(new ManaPool("(8)"));

			Target target = this.addTarget(Players.instance(), "target player");

			this.addEffect(tap(Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(targetedBy(target))), "Tap all creatures target player controls."));
		}
	}

	public DawnglareInvoker(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// (8): Tap all creatures target player controls.
		this.addAbility(new DawnglareInvokerAbility1(state));
	}
}
