package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Paragon of Open Graves")
@Types({Type.CREATURE})
@SubTypes({SubType.SKELETON, SubType.WARRIOR})
@ManaCost("3B")
@ColorIdentity({Color.BLACK})
public final class ParagonofOpenGraves extends Card
{
	public static final class ParagonofOpenGravesAbility0 extends StaticAbility
	{
		public ParagonofOpenGravesAbility0(GameState state)
		{
			super(state, "Other black creatures you control get +1/+1.");
			SetGenerator guys = Intersect.instance(HasColor.instance(Color.BLACK), CREATURES_YOU_CONTROL);
			SetGenerator otherGuys = RelativeComplement.instance(guys, This.instance());
			this.addEffectPart(modifyPowerAndToughness(otherGuys, +1, +1));
		}
	}

	public static final class ParagonofOpenGravesAbility1 extends ActivatedAbility
	{
		public ParagonofOpenGravesAbility1(GameState state)
		{
			super(state, "(2)(B), (T): Another target black creature you control gains deathtouch until end of turn.");
			this.setManaCost(new ManaPool("(2)(B)"));
			this.costsTap = true;

			SetGenerator guys = Intersect.instance(HasColor.instance(Color.BLACK), CREATURES_YOU_CONTROL);
			SetGenerator otherGuys = RelativeComplement.instance(guys, This.instance());
			SetGenerator target = targetedBy(this.addTarget(otherGuys, "another target black creature you control"));
			this.addEffect(addAbilityUntilEndOfTurn(target, org.rnd.jmagic.abilities.keywords.Deathtouch.class, "Another target black creature you control gains deathtouch until end of turn."));
		}
	}

	public ParagonofOpenGraves(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Other black creatures you control get +1/+1.
		this.addAbility(new ParagonofOpenGravesAbility0(state));

		// (2)(B), (T): Another target black creature you control gains
		// deathtouch until end of turn. (Any amount of damage it deals to a
		// creature is enough to destroy it.)
		this.addAbility(new ParagonofOpenGravesAbility1(state));
	}
}
