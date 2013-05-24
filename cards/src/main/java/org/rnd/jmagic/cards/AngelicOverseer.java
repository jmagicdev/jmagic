package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Angelic Overseer")
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("3WW")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.MYTHIC)})
@ColorIdentity({Color.WHITE})
public final class AngelicOverseer extends Card
{
	public static final class AngelicOverseerAbility1 extends StaticAbility
	{
		public AngelicOverseerAbility1(GameState state)
		{
			super(state, "As long as you control a Human, Angelic Overseer has hexproof and is indestructible.");
			SetGenerator youControl = ControlledBy.instance(You.instance());
			SetGenerator youControlAHuman = Intersect.instance(youControl, HasSubType.instance(SubType.HUMAN));
			this.canApply = Both.instance(this.canApply, youControlAHuman);

			this.addEffectPart(addAbilityToObject(This.instance(), org.rnd.jmagic.abilities.keywords.Hexproof.class), indestructible(This.instance()));
		}
	}

	public AngelicOverseer(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// As long as you control a Human, Angelic Overseer has hexproof and is
		// indestructible.
		this.addAbility(new AngelicOverseerAbility1(state));
	}
}
