package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Sunblast Angel")
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("4WW")
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class SunblastAngel extends Card
{
	public static final class SunblastAngelAbility1 extends EventTriggeredAbility
	{
		public SunblastAngelAbility1(GameState state)
		{
			super(state, "When Sunblast Angel enters the battlefield, destroy all tapped creatures.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(destroy(Intersect.instance(Tapped.instance(), CreaturePermanents.instance()), "Destroy all tapped creatures."));
		}
	}

	public SunblastAngel(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(5);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Sunblast Angel enters the battlefield, destroy all tapped
		// creatures.
		this.addAbility(new SunblastAngelAbility1(state));
	}
}
