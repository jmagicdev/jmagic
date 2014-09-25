package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Efreet Weaponmaster")
@Types({Type.CREATURE})
@SubTypes({SubType.EFREET, SubType.MONK})
@ManaCost("3URW")
@ColorIdentity({Color.RED, Color.WHITE, Color.BLUE})
public final class EfreetWeaponmaster extends Card
{
	public static final class EfreetWeaponmasterAbility1 extends EventTriggeredAbility
	{
		public EfreetWeaponmasterAbility1(GameState state)
		{
			super(state, "When Efreet Weaponmaster enters the battlefield or is turned face up, another target creature you control gets +3/+0 until end of turn.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addPattern(whenThisIsTurnedFaceUp());

			SetGenerator otherCreatures = RelativeComplement.instance(CREATURES_YOU_CONTROL, ABILITY_SOURCE_OF_THIS);
			SetGenerator target = targetedBy(this.addTarget(otherCreatures, "another target creature you control"));
			this.addEffect(ptChangeUntilEndOfTurn(target, +3, +0, "Another target creature you control gets +3/+0 until end of turn."));
		}
	}

	public EfreetWeaponmaster(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(3);

		// First strike
		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));

		// When Efreet Weaponmaster enters the battlefield or is turned face up,
		// another target creature you control gets +3/+0 until end of turn.
		this.addAbility(new EfreetWeaponmasterAbility1(state));

		// Morph (2)(U)(R)(W) (You may cast this card face down as a 2/2
		// creature for (3). Turn it face up any time for its morph cost.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Morph(state, "(2)(U)(R)(W)"));
	}
}
